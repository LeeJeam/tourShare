package com.xmyy.pay.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.codingapi.tx.annotation.TxTransaction;
import com.xmyy.common.EnumConstants;
import com.xmyy.common.util.BigDecimalUtil;
import com.xmyy.common.util.BizSequenceUtils;
import com.xmyy.common.util.PasswordUtil;
import com.xmyy.common.vo.MemberInfo;
import com.xmyy.member.model.UcPayPassword;
import com.xmyy.member.service.UcPayPasswordService;
import com.xmyy.member.service.UcSellerService;
import com.xmyy.order.model.DgOrder;
import com.xmyy.order.service.DgOrderService;
import com.xmyy.pay.allinpay.ServerFactory;
import com.xmyy.pay.allinpay.YunResponse;
import com.xmyy.pay.allinpay.constants.AllinPayEnum;
import com.xmyy.pay.allinpay.constants.PayModeEnum;
import com.xmyy.pay.allinpay.constants.Server;
import com.xmyy.pay.allinpay.constants.YunConfig;
import com.xmyy.pay.allinpay.request.*;
import com.xmyy.pay.allinpay.response.*;
import com.xmyy.pay.core.YunClient;
import com.xmyy.pay.core.util.RSAUtil;
import com.xmyy.pay.mapper.DgPayLogMapper;
import com.xmyy.pay.model.DgPayLog;
import com.xmyy.pay.service.AllinPayMemberService;
import com.xmyy.pay.service.AllinPayOrderService;
import com.xmyy.pay.service.DgPayLogService;
import com.xmyy.pay.vo.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * 通联支付接口  服务实现类
 *
 * @author AnCheng
 */
@Service(interfaceClass = AllinPayOrderService.class)
//@CacheConfig(cacheNames = "allinPayOrderService")
public class AllinPayOrderServiceImpl implements AllinPayOrderService {
    private static Logger logger = LoggerFactory.getLogger(AllinPayOrderServiceImpl.class);
    private static YunClient yunClient = YunClient.getInstance();

    @Resource
    private UcSellerService sellerService;
    @Resource
    private DgPayLogService payLogService;
    @Resource
    private DgPayLogMapper payLogMapper;
    @Resource
    private DgOrderService orderService;
    @Resource
    private AllinPayMemberService payMemberService;
    @Resource
    private UcPayPasswordService payPasswordService;

    @Override
    @Transactional
    @TxTransaction(isStart = true)
    public Object agentCollectApply(AgentCollectApplyParam p) {
        MemberInfo memberInfo = sellerService.getMemberInfo(p.getMemberId(), p.getMemberType());
        if (memberInfo == null) {
            return "获取用户信息失败";
        }

        //快捷支付，需要验证买家的支付密码
        if (p.getPayMethod() == EnumConstants.XBBPayMethod.QUICKPAY.getValue().intValue()) {
            UcPayPassword payPassword = payPasswordService.queryById(memberInfo.getPayPasswordId());
            if (payPassword == null || payPassword.getPassword() == null) {
                return "请先设置支付密码";
            }
            if (!PasswordUtil.verifyPassword(p.getPayPwd(), payPassword.getPassword())) {
                return "支付密码错误，请重新输入";
            }
        }

        List<DgOrder> orderList = orderService.listByOrderIds(p.getOrderIdList());
        /*
         * 如果是单独支付，查询是否有未完成的支付记录
         * 1.如果有“进行中”的支付记录，直接返回提示，避免重复支付
         * 2.如果有“未支付”的支付记录，说明没有成功调用通联接口，继续申请支付
         */
        DgPayLog payLog = null;
        if (p.getOrderIdList().size() == 1) {
            DgOrder order = orderList.get(0);
            if (order.getApplyPaylogId() != null) {
                //有托管代收支付记录，状态未“进行中”或“交易完成”，直接返回，避免重复支付
                EntityWrapper<DgPayLog> ew = new EntityWrapper<>();
                ew.eq("id_", order.getApplyPaylogId());
                //sql一定要加括号
                ew.and("(status = {0} OR status = {1})", EnumConstants.PayStatus.PAY_ONGOING.getValue(), EnumConstants.PayStatus.PAY_SUCCESS.getValue());
                if (payLogMapper.selectCount(ew) > 0) {
                    return "请勿重复支付";
                }

                /*
                 * 快捷支付不验证，申请支付要么成功要么失败，不会有未支付记录
                 * 如果有“未支付”记录，支付宝支付返回payInfo，重复调用通联接口会报“不支持多次申请支付”
                 * TODO 微信支付？？？
                 */
                ew = new EntityWrapper<>();
                ew.eq("id_", order.getApplyPaylogId());
                ew.eq("status", EnumConstants.PayStatus.WAITTING_PAY.getValue());
                ew.orderBy("id_", false);
                if (payLogMapper.selectCount(ew) > 0) {
                    payLog = payLogMapper.selectList(ew).get(0);
                    if (p.getPayMethod() == EnumConstants.XBBPayMethod.ALIPAY_APP.getValue().intValue()) {
                        AgentCollectApplyResult result = new AgentCollectApplyResult();
                        result.setBizUserId(payLog.getBizUserId());
                        result.setBizOrderNo(payLog.getBizOrderNo());
                        result.setPayInfo(payLog.getPayInfo());
                        return result;
                    } else if (p.getPayMethod() == EnumConstants.XBBPayMethod.WECHATPAY_APP.getValue().intValue()) {
                        //TODO 有微信支付后，看这里还要不要返回微信支付数据
                    }
                }
            }
        }

        /*
         * 情况1：如果是多订单批量支付，若批量支付不成功，此次支付直接作废，之后都是单个订单独立支付，所以批量支付记录肯定是新生成的
         * 情况2：没有找到未支付的支付记录，生成新的支付记录
         */
        if (payLog == null || p.getOrderIdList().size() > 1) {
            payLog = new DgPayLog();
            payLog.setOrderIds(StringUtils.join(p.getOrderIdList(), ","));
            payLog.setBizOrderNo(BizSequenceUtils.generateBizNo(EnumConstants.BizCode.PayLogNo));
            payLog.setBizUserId(memberInfo.getUuid());
            payLog.setServiceType(EnumConstants.ServiceType.PAY_ORDER.getValue());
            payLog.setStatus(EnumConstants.PayStatus.WAITTING_PAY.getValue());
            payLog.setAmount(p.getAmount());
            payLog.setFee(p.getFee());
            payLog.setPayMethod(p.getPayMethod());
            payLog.setRecieverList(p.getRecieverList().toJSONString());
            payLog = payLogService.update(payLog);

            //更新订单关联的支付记录ID，如果是多订单统一支付，多张订单指向同一支付记录
            for (DgOrder order : orderList) {
                order.setApplyPaylogId(payLog.getId());
            }
            orderService.updateBatch(orderList);
        }

        //TODO 分账记录

        //请求通联托管代收接口
        ServerHandler handler = ServerFactory.orderHandler(Server.Order.AGENTCOLLECTAPPLY);
        AgentCollectApplyParams params = new AgentCollectApplyParams();
        params.setBizOrderNo(payLog.getBizOrderNo());
        params.setPayerId(memberInfo.getUuid());
        params.setRecieverList(p.getRecieverList());
        params.setTradeCode(AllinPayEnum.TradeCode.AGENT_COLLECT.getValue());
        params.setAmount(p.getAmount());
        params.setFee(p.getFee());
        params.setBackUrl(YunConfig.BACK_URL);

        if (p.getPayMethod().equals(EnumConstants.XBBPayMethod.ALIPAY_APP.getValue())) {
            //支付宝支付（扫码正扫）
            JSONObject param = new JSONObject();
            param.put("payType", "A01");
            param.put("amount", p.getAmount());
            JSONObject json = new JSONObject();
            json.put(PayModeEnum.SCAN_ALIPAY.toString(), param);
            params.setPayMethod(json);
        } else if (p.getPayMethod().equals(EnumConstants.XBBPayMethod.WECHATPAY_APP.getValue())) {
            //微信APP支付
            JSONObject param = new JSONObject();
            param.put("limitPay", "no_credit"); //TODO 参数
            param.put("amount", p.getAmount());
            param.put("apptype", EnumConstants.DeviceType.valueOf(memberInfo.getLoginSource()));
            param.put("appname", YunConfig.APPNAME);
            param.put("apppackage", YunConfig.APPPACKAGE);
            param.put("cusip", p.getIp());
            JSONObject json = new JSONObject();
            json.put(PayModeEnum.WeChatPAYAPP_VSP.toString(), param);
            params.setPayMethod(json);
        } else if (p.getPayMethod().equals(EnumConstants.XBBPayMethod.QUICKPAY.getValue())) {
            //收银宝快捷支付
            JSONObject param = new JSONObject();
            if (StringUtils.isBlank(p.getBankCardNo())) {
                return "请填写银行卡号";
            } else {
                try {
                    param.put("bankCardNo", RSAUtil.encrypt(p.getBankCardNo()));
                } catch (Exception e) {
                    e.printStackTrace();
                    return "银行卡号加密失败";
                }
            }
            param.put("amount", p.getAmount());
            JSONObject json = new JSONObject();
            json.put(PayModeEnum.QUICKPAY_VSP.toString(), param);
            params.setPayMethod(json);
            params.setValidateType(0L);
        }

        params.setIndustryCode(AllinPayEnum.IndustryCode.OTHER.getCode());
        params.setIndustryName(AllinPayEnum.IndustryCode.OTHER.getName());
        params.setSource(AllinPayEnum.Source.MOBILE.getValue());

        YunResponse response = yunClient.doRequest(handler, params, AgentCollectApplyResult.class);
        if (response.isOK()) {
            AgentCollectApplyResult result = (AgentCollectApplyResult) response.getResult();
            result.setBizUserId(memberInfo.getUuid());

            //保存返回的数据，若需要重新支付，直接返回数据，避免重复调用通联接口
            payLog.setTradeNo(result.getTradeNo());
            payLog.setPayInfo(result.getPayInfo());
            //TODO 有微信支付后，看这里还要不要保存微信支付数据
            payLogService.update(payLog);

            return result;
        } else {
            //若支付失败，更新交易记录为“交易失败”
            payLog.setStatus(EnumConstants.PayStatus.PAY_FAIL.getValue());
            payLogService.update(payLog);

            logger.info("托管代付失败，交易号：{}，失败原因：{}", payLog.getBizOrderNo(), response.getMessage());
            return response.getMessage();
        }

    }


    @Override
    @Transactional
    @TxTransaction
    public Object signalAgentPay(SignalAgentPayParam p) {
        DgOrder order = orderService.queryById(p.getOrderId());
        DgPayLog payLog = null;

        //如果订单有代付交易记录，状态为“进行中”或“交易成功”，直接返回，避免重复支付
        if (order.getPayPaylogId() != null) {
            EntityWrapper<DgPayLog> ew = new EntityWrapper<>();
            ew.eq("id_", order.getPayPaylogId());
            ew.and("(status = {0} OR status = {1})", EnumConstants.PayStatus.PAY_ONGOING.getValue(), EnumConstants.PayStatus.PAY_SUCCESS.getValue());
            if (payLogMapper.selectCount(ew) > 0) {
                return "请勿重复支付";
            }

            //如果有“未支付”记录，即继续此次代付
            ew = new EntityWrapper<>();
            ew.eq("id_", order.getPayPaylogId());
            ew.eq("status", EnumConstants.PayStatus.WAITTING_PAY.getValue());
            ew.orderBy("id_", false);
            if (payLogMapper.selectCount(ew) > 0) {
                payLog = payLogMapper.selectList(ew).get(0);
            }
        }

        //生成新的托管代付的支付记录
        if (payLog == null) {
            payLog = new DgPayLog();
            payLog.setOrderIds(order.getId().toString());
            payLog.setBizOrderNo(BizSequenceUtils.generateBizNo(EnumConstants.BizCode.PayLogNo));
            payLog.setBizUserId(p.getBizUserId());
            payLog.setServiceType(EnumConstants.ServiceType.RECIEVE.getValue());
            payLog.setStatus(EnumConstants.PayStatus.WAITTING_PAY.getValue());
            payLog.setAmount(p.getAmount());
            payLog.setFee(p.getFee());
            MemberInfo memberInfo = sellerService.getMemberInfo(order.getBuyerId(), EnumConstants.MemberType.buyer.getValue());
            payLog.setRemark(memberInfo.getNickName()); //代付记录交易明细，显示买家的昵称
            payLog = payLogService.update(payLog);

            //更新订单的托管代付支付ID
            order.setPayPaylogId(payLog.getId());
            orderService.update(order);
        }

        //TODO 分账记录

        //调用单笔代付接口
        ServerHandler handler = ServerFactory.orderHandler(Server.Order.SIGNALAGENTPAY);
        SignalAgentPayParams params = new SignalAgentPayParams();
        params.setBizOrderNo(payLog.getBizOrderNo());

        JSONArray collectPayList = new JSONArray();
        JSONObject collectPayMap = new JSONObject();
        DgPayLog applyPayLog = payLogService.queryById(order.getApplyPaylogId());
        collectPayMap.put("bizOrderNo", applyPayLog.getBizOrderNo()); //代收时的支付单号
        collectPayMap.put("amount", applyPayLog.getAmount());
        collectPayList.add(collectPayMap);
        params.setCollectPayList(collectPayList);

        params.setBizUserId(p.getBizUserId());
        params.setAccountSetNo(AllinPayEnum.AccountSetNo.XBB.getValue());
        params.setBackUrl(YunConfig.BACK_URL);
        params.setAmount(p.getAmount());
        params.setFee(p.getFee());
        params.setTradeCode(AllinPayEnum.TradeCode.AGENT_PAY.getValue());

        YunResponse response = yunClient.doRequest(handler, params, SignalAgentPayResult.class);
        if (response.isOK()) {
            return response.getResult();
        } else {
            //若支付失败，更新交易记录为“交易失败”
            payLog.setStatus(EnumConstants.PayStatus.PAY_FAIL.getValue());
            payLogService.update(payLog);

            logger.info("托管代收失败，交易号：{}，失败原因：{}", payLog.getBizOrderNo(), response.getMessage());
            return response.getMessage();
        }
    }


    @Override
    @Transactional
    @TxTransaction(isStart = true)
    public Object refund(RefundParam p) {
        DgOrder order = orderService.queryById(p.getOrderId());
        DgPayLog payLog = null;

        //如果订单有退款交易记录，交易记录状态为 “进行中”、“交易成功”、“退款成功”、“退款受理中”，直接返回，避免重复申请退款
        if (order.getRefundPaylogId() != null) {
            EntityWrapper<DgPayLog> ew = new EntityWrapper<>();
            ew.eq("id_", order.getRefundPaylogId());
            Integer[] status = {EnumConstants.PayStatus.PAY_ONGOING.getValue(), EnumConstants.PayStatus.PAY_SUCCESS.getValue(),
                    EnumConstants.PayStatus.REFUND_SUCCESS.getValue(), EnumConstants.PayStatus.FOR_REFUND.getValue()};
            ew.in("status", status);
            if (payLogMapper.selectCount(ew) > 0) {
                return "请勿重复退款";
            }

            //如果有“未支付”记录，说明没有成功调用通联申请退款，即继续此次退款
            ew = new EntityWrapper<>();
            ew.eq("id_", order.getRefundPaylogId());
            ew.eq("status", EnumConstants.PayStatus.WAITTING_PAY.getValue());
            ew.orderBy("id_", false);
            if (payLogMapper.selectCount(ew) > 0) {
                payLog = payLogMapper.selectList(ew).get(0);
            }
        }

        //生成新的退款申请的支付记录
        if (payLog == null) {
            payLog = new DgPayLog();
            payLog.setOrderIds(order.getId().toString());
            payLog.setBizOrderNo(BizSequenceUtils.generateBizNo(EnumConstants.BizCode.PayLogNo));
            payLog.setBizUserId(p.getBizUserId());
            payLog.setServiceType(EnumConstants.ServiceType.REFUND.getValue());
            payLog.setStatus(EnumConstants.PayStatus.WAITTING_PAY.getValue());
            payLog.setAmount(p.getAmount());
            payLog.setFee(p.getFee());
            payLog = payLogService.update(payLog);

            //更新订单的托管代付支付ID
            order.setRefundPaylogId(payLog.getId());
            orderService.update(order);
        }

        //TODO 分账记录

        //调用通联退款申请接口
        ServerHandler handler = ServerFactory.orderHandler(Server.Order.REFUND);
        RefundParams params = new RefundParams();
        params.setBizOrderNo(payLog.getBizOrderNo());
        DgPayLog oriPayLog = payLogService.queryById(order.getApplyPaylogId());
        params.setOriBizOrderNo(oriPayLog.getBizOrderNo());
        params.setBizUserId(p.getBizUserId());
        params.setRefundType("D0"); //TODO 退款方式，与产品确认用哪种
        params.setRefundList(JSONArray.parseArray(oriPayLog.getRecieverList()));
        params.setBackUrl(YunConfig.BACK_URL);
        params.setAmount(p.getAmount());
//        params.setCouponAmount(0L);
        params.setFeeAmount(0L);
        YunResponse response = yunClient.doRequest(handler, params, RefundResult.class);
        if (response.isOK()) {
            payLog.setStatus(EnumConstants.PayStatus.PAY_ONGOING.getValue());
            payLogService.update(payLog);

            return response.getResult();
        } else {
            //若支付失败，更新交易记录为“交易失败”
            payLog.setStatus(EnumConstants.PayStatus.PAY_FAIL.getValue());
            payLogService.update(payLog);

            logger.info("申请退款失败，交易号：{}，失败原因：{}", payLog.getBizOrderNo(), response.getMessage());
            return response.getMessage();
        }
    }


    @Override
    @Transactional
    @TxTransaction(isStart = true)
    public Object getOrderDetail(String bizOrderNo) {
        if (bizOrderNo == null) {
            return "支付单号不能为空";
        }

        DgPayLog pp = new DgPayLog();
        pp.setBizOrderNo(bizOrderNo);
        DgPayLog payLog = payLogMapper.selectOne(pp);

        //调用通联查询支付状态
        ServerHandler handler = ServerFactory.orderHandler(Server.Order.GETORDERDETAIL);
        GetOrderDetailParams params = new GetOrderDetailParams();
        params.setBizUserId(payLog.getBizUserId());
        params.setBizOrderNo(bizOrderNo);
        YunResponse response = yunClient.doRequest(handler, params, GetOrderDetailResult.class);
        if (response.isOK()) {
            GetOrderDetailResult result = (GetOrderDetailResult) response.getResult();

            //查询支付状态时，检查支付状态是否变化
            checkPayStatusAndFinishServiceOpts(payLog, result.getOrderStatus());

            //封装查询结果
            OrderDetailResult cusResult = new OrderDetailResult(); //TODO，需求支付做完后再考虑这里怎么返回
            cusResult.setBizOrderNo(result.getBizOrderNo());
            cusResult.setOrderStatus(result.getOrderStatus().intValue());
            if (payLog.getServiceType() == EnumConstants.ServiceType.WITH_DRAW.getValue().intValue()) {
                cusResult.setType(3);
                cusResult.setId(payLog.getId());
            } else {
                DgOrder order = orderService.queryById(Long.parseLong(payLog.getOrderIds()));
                if (order.getOrderType() == EnumConstants.OrderType.PRE_SALE.getValue().intValue()) {
                    cusResult.setType(1);
                } else if (order.getOrderType() == EnumConstants.OrderType.DEMAND_SALE.getValue().intValue()) {
                    cusResult.setType(2);
                }
                cusResult.setId(Long.parseLong(payLog.getOrderIds()));
            }

            return cusResult;
        } else {
            logger.info("查询支付状态失败，交易号：{}，失败原因：{}", payLog.getBizOrderNo(), response.getMessage());
            return response.getMessage();
        }
    }


    @Override
    @Transactional(readOnly = true)
    public Object queryInExpDetail(Long memberId, Integer memberType, Integer current, Integer size) {
        MemberInfo memberInfo = sellerService.getMemberInfo(memberId, memberType);
        if (StringUtils.isEmpty(memberInfo.getUuid())) {
            return "获取用户信息失败";
        }
        if (current == null || current < 1) {
            current = 1;
        }
        if (size == null || size < 1) {
            size = 5;
        }

        ServerHandler handler = ServerFactory.orderHandler(Server.Order.QUERYINEXPDETAIL);
        QueryInExpDetailParams params = new QueryInExpDetailParams();
        params.setBizUserId(memberInfo.getUuid());
        params.setStartPosition((long) ((current - 1) * size + 1));
        params.setQueryNum(size.longValue());
        YunResponse response = yunClient.doRequest(handler, params, QueryInExpDetailResult.class);
        if (response.isOK()) {
            return response.getResult();
        } else {
            logger.info(response.getMessage());
            return response.getMessage();
        }
    }


    @Override
    @Transactional(readOnly = true)
    public Object wallet(Long memberId, Integer memberType, Integer current, Integer size) {
        if (current == null || current < 1) {
            current = 1;
        }
        if (size == null || size < 1) {
            size = 5;
        }

        MemberInfo memberInfo = sellerService.getMemberInfo(memberId, memberType);
        if (StringUtils.isEmpty(memberInfo.getUuid())) {
            return "获取用户信息失败";
        }

        Object o = queryBalance(memberId, memberType);
        if (o instanceof String) {
            return o.toString();
        }

        MyWalletResult result = new MyWalletResult();
        result.setAmount(BigDecimalUtil.changeF2Y(o.toString()));
        result.setIsSetPayPwd(memberInfo.getPayPasswordId() == null ? 0 : 1);

        //查询并封装交易明细
        ServerHandler handler = ServerFactory.orderHandler(Server.Order.QUERYINEXPDETAIL);
        QueryInExpDetailParams params = new QueryInExpDetailParams();
        params.setBizUserId(memberInfo.getUuid());
        params.setStartPosition((long) ((current - 1) * size + 1));
        params.setQueryNum(size.longValue());
        YunResponse response = yunClient.doRequest(handler, params, QueryInExpDetailResult.class);

        if (response.isOK()) {
            QueryInExpDetailResult detailResult = (QueryInExpDetailResult) response.getResult();
            List<InExpDetailDto> inExpDetailList = JSONObject.parseArray(detailResult.getInExpDetail(), InExpDetailDto.class);

            List<MyWalletResult.PayDetail> payDetailList = new ArrayList<>();
            for (InExpDetailDto inExpDetailDto : inExpDetailList) {
                MyWalletResult.PayDetail payDetail = new MyWalletResult.PayDetail();
                DgPayLog p = new DgPayLog();
                p.setBizOrderNo(inExpDetailDto.getBizOrderNo());
                DgPayLog payLog = payLogMapper.selectOne(p);

                payDetail.setRemark(payLog.getRemark());
                payDetail.setPayTimeStr(inExpDetailDto.getChangeTime());
                String symbol = inExpDetailDto.getChgAmount() > 0 ? "+ " : "- ";
                payDetail.setAmountStr(symbol + BigDecimalUtil.changeF2Y(Math.abs(inExpDetailDto.getChgAmount())));
                payDetail.setInOut(inExpDetailDto.getChgAmount() > 0 ? 1 : -1);

                if (payLog.getServiceType() == EnumConstants.ServiceType.WITH_DRAW.getValue().intValue()) {
                    payDetail.setDrawId(payLog.getId());
                } else {
                    payDetail.setOrderId(Long.parseLong(payLog.getOrderIds())); //买手的交易明细不会有代收，所以交易记录的orderId肯定是单个的
                }

                payDetailList.add(payDetail);
            }

            result.setPayDetailList(payDetailList);
        } else {
            logger.info("查询收支明细失败，bizUserId：{}，失败原因：{}", memberInfo.getUuid(), response.getMessage());
            return "查询收支明细失败";
        }

        return result;
    }


    @Override
    @Transactional(readOnly = true)
    public Object prepareToDraw(Long memberId, Integer memberType) {
        PrepareToDrawResult result = new PrepareToDrawResult();

        //查询余额
        Object o1 = queryBalance(memberId, memberType);
        if (o1 instanceof String) {
            return o1.toString();
        } else {
            result.setAmount(BigDecimalUtil.changeF2Y(o1.toString()));
        }

        //查询绑定的银行卡
        Object o2 = payMemberService.queryBankCardList(memberId, memberType, null);
        if (o2 instanceof String) {
            return o2.toString();
        } else {
            result.setBankCardList((List<BankCardResult>) o2);
        }

        return result;
    }


    @Override
    @Transactional
    public Object withDrawApply(Long memberId, DrawApplyParam p) {
        MemberInfo memberInfo = sellerService.getMemberInfo(memberId, p.getMemberType());
        if (StringUtils.isEmpty(memberInfo.getUuid())) {
            return "获取用户信息失败";
        }

        UcPayPassword payPassword = payPasswordService.queryById(memberInfo.getPayPasswordId());
        if (payPassword == null || payPassword.getPassword() == null) {
            return "请先设置支付密码";
        }

        //验证交易密码
        if (!PasswordUtil.verifyPassword(p.getPayPassword(), payPassword.getPassword())) {
            return "支付密码错误";
        }

        //生成提现支付记录
        DgPayLog payLog = new DgPayLog();
        payLog.setBizOrderNo(BizSequenceUtils.generateBizNo(EnumConstants.BizCode.PayLogNo));
        payLog.setBizUserId(memberInfo.getUuid());
        payLog.setServiceType(EnumConstants.ServiceType.WITH_DRAW.getValue());
        payLog.setStatus(EnumConstants.PayStatus.WAITTING_PAY.getValue());
        payLog.setAmount(p.getAmount());
        payLog.setFee(0L);
        payLog.setBankCardNo(p.getBankCardNo());
        payLog.setRemark("提现");
        payLog = payLogService.update(payLog);

        //TODO 分账记录

        //调用通联提现接口
        ServerHandler handler = ServerFactory.orderHandler(Server.Order.WITHDRAWAPPLY);
        WithdrawApplyParams params = new WithdrawApplyParams();
        params.setBizOrderNo(payLog.getBizOrderNo());
        params.setBizUserId(memberInfo.getUuid());
        params.setAccountSetNo(AllinPayEnum.AccountSetNo.XBB.getValue());
        params.setAmount(payLog.getAmount());
        params.setFee(payLog.getFee());
        params.setValidateType(0L);
        params.setBackUrl(YunConfig.BACK_URL);
        try {
            params.setBankCardNo(RSAUtil.encrypt(p.getBankCardNo()));
        } catch (Exception e) {
            logger.error("银行卡号加密失败：{}", e);
            return "银行卡号加密失败";
        }
        params.setWithdrawType("T0"); //TODO 产品确认哪种到账方式
        params.setIndustryCode(AllinPayEnum.IndustryCode.OTHER.getCode());
        params.setIndustryName(AllinPayEnum.IndustryCode.OTHER.getName());
        params.setSource(AllinPayEnum.Source.MOBILE.getValue());

        //调用通联提现申请
        YunResponse response = yunClient.doRequest(handler, params, WithdrawApplyResult.class);
        if (response.isOK()) {
            return response.getResult();
        } else {
            //若支付失败，更新交易记录为“交易失败”
            payLog.setStatus(EnumConstants.PayStatus.PAY_FAIL.getValue());
            payLogService.update(payLog);

            logger.info("申请提现失败，交易号：{}，失败原因：{}", payLog.getBizOrderNo(), response.getMessage());
            return response.getMessage();
        }
    }


    @Override
    public Object pay(PayParam p) {
        ServerHandler handler = ServerFactory.orderHandler(Server.Order.PAY);
        PayParams params = new PayParams();
        params.setBizUserId(p.getBizUserId());
        params.setBizOrderNo(p.getBizOrderNo());
        if (StringUtils.isNotBlank(p.getTradeNo())) {
            params.setTradeNo(p.getTradeNo());
        }
        params.setVerificationCode(p.getVerificationCode());
        params.setConsumerIp(YunConfig.COMPANY_IP);

        YunResponse response = yunClient.doRequest(handler, params, PayResult.class);
        if (response.isOK()) {
            return response.getResult();
        } else {
            logger.info(response.getMessage());
            return response.getMessage();
        }
    }


    @Override
    @Transactional(readOnly = true)
    public Object withDrawStatus(Long memberId, Integer memberType, String bizOrderNo) {
        DgPayLog payLog = new DgPayLog();
        payLog.setBizOrderNo(bizOrderNo);
        payLog = payLogMapper.selectOne(payLog);
        if (payLog == null || payLog.getServiceType() != EnumConstants.ServiceType.WITH_DRAW.getValue().intValue()) {
            return "找不到提现数据";
        }

        Object o1 = payMemberService.getBankCardBin(payLog.getBankCardNo());
        if (o1 instanceof String) {
            return "查询银行卡数据失败";
        }
        BankCardBinResult bankCardResult = (BankCardBinResult) o1;

        Object o2 = getOrderDetail(payLog.getBizOrderNo());
        if (o2 instanceof String) {
            return "查询提现状态失败";
        }
        GetOrderDetailResult orderDetailResult = (GetOrderDetailResult) o2;

        WithDrawStatusResult result = new WithDrawStatusResult();
        result.setStatus(orderDetailResult.getOrderStatus().intValue());
        result.setAmountStr(new DecimalFormat(",###,##0.00").format(BigDecimalUtil.changeF2Y(payLog.getAmount())));
        result.setBankCardNo(payLog.getBankCardNo());
        result.setBankName(bankCardResult.getBankName());
        return result;
    }


    @Override
    @Transactional(readOnly = true)
    public Object queryBalance(Long memberId, Integer memberType) {
        MemberInfo memberInfo = sellerService.getMemberInfo(memberId, memberType);
        if (StringUtils.isEmpty(memberInfo.getUuid())) {
            return "获取用户UUID失败";
        }

        ServerHandler handler = ServerFactory.orderHandler(Server.Order.QUERYBALANCE);
        QueryBalanceParams params = new QueryBalanceParams();
        params.setBizUserId(memberInfo.getUuid());
        params.setAccountSetNo(AllinPayEnum.AccountSetNo.XBB.getValue());
        YunResponse response = yunClient.doRequest(handler, params, QueryBalanceResult.class);
        if (response.isOK()) {
            QueryBalanceResult result = (QueryBalanceResult) response.getResult();
            return result.getAllAmount();
        } else {
            logger.info("查询余额失败，bizUserId：{}，失败原因：{}", memberInfo.getUuid(), response.getMessage());
            return response.getMessage();
        }
    }


    /*
     * 查询支付记录，若支付状态发生变更进行处理：
     * 1.更新对应支付记录
     * 2.对不同支付业务的回调，做不同的处理
     */
    private void checkPayStatusAndFinishServiceOpts(DgPayLog payLog, Long orderStatus) {
        if (payLog.getStatus() != orderStatus.intValue()) { //如果查询到的状态与支付记录的状态不同，则进行下面的操作，否则不用处理
            payLog.setStatus(orderStatus.intValue());
            payLogService.update(payLog);

            if (payLog.getServiceType() == EnumConstants.ServiceType.PAY_ORDER.getValue().intValue() &&
                    orderStatus == EnumConstants.PayStatus.PAY_SUCCESS.getValue().longValue()) {
                //1-订单支付成功
                orderService.paySuccess(payLog.getId());

            } else if (payLog.getServiceType() == EnumConstants.ServiceType.RECIEVE.getValue().intValue() &&
                    orderStatus == EnumConstants.PayStatus.PAY_SUCCESS.getValue().longValue()) {
                //2-确认收货，代付成功
                orderService.confirmReceiveSuccess(payLog.getId());

            } else if (payLog.getServiceType() == EnumConstants.ServiceType.REFUND.getValue().intValue() &&
                    orderStatus == EnumConstants.PayStatus.PAY_SUCCESS.getValue().longValue()) {
                //3-退款成功
                orderService.agreeRefundSuccess(payLog.getId());

            } else if (payLog.getServiceType() == EnumConstants.ServiceType.WITH_DRAW.getValue().intValue() &&
                    orderStatus == EnumConstants.PayStatus.PAY_SUCCESS.getValue().longValue()) {
                //4-提现成功
                //TODO 暂时没啥做的，消息推送？
            }
        }
    }

}
