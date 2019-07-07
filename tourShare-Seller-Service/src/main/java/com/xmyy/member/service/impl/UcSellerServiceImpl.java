package com.xmyy.member.service.impl;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.toolkit.IdWorker;
import com.google.common.collect.Lists;
import com.xmyy.circle.model.DgTags;
import com.xmyy.circle.service.DgTagsService;
import com.xmyy.circle.service.UcMemberRelationService;
import com.xmyy.common.Constants;
import com.xmyy.common.EnumConstants;
import com.xmyy.common.ServiceCode;
import com.xmyy.common.util.*;
import com.xmyy.common.vo.MemberInfo;
import com.xmyy.common.vo.QrCodeInfo;
import com.xmyy.member.dao.UcSellerDao;
import com.xmyy.member.mapper.UcPasswordMapper;
import com.xmyy.member.mapper.UcSellerMapper;
import com.xmyy.member.model.*;
import com.xmyy.member.service.*;
import com.xmyy.member.vo.*;
import com.xmyy.pay.service.AllinPayMemberService;
import com.xmyy.product.model.PtProduct;
import com.xmyy.product.service.PtProductService;
import com.xmyy.product.service.TourService;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.transaction.annotation.Transactional;
import top.ibase4j.core.base.BaseServiceImpl;
import top.ibase4j.core.support.Pagination;
import top.ibase4j.core.util.CacheUtil;
import top.ibase4j.core.util.InstanceUtil;
import top.ibase4j.core.util.JsonUtil;
import top.ibase4j.core.util.TokenUtil;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

import static com.xmyy.common.Constants.*;
import static com.xmyy.common.util.ModelMapUtil.setModelMap;

/**
 * 买手  服务实现类
 *
 * @author zlp
 */
@Service(interfaceClass = UcSellerService.class)
@CacheConfig(cacheNames = "UcSeller")
public class UcSellerServiceImpl extends BaseServiceImpl<UcSeller, UcSellerMapper> implements UcSellerService {

    @Resource
    private UcInviteCodeServiceImpl codeService;
    @Resource
    private UcMemberLoginService ucMemberLoginService;
    @Resource
    private DgSmsRecordService dgSmsRecordService;
    @Resource
    private UcPasswordMapper ucPasswordMapper;
    @Resource
    private UcMemberLogService ucMemberLogService;
    @Resource
    private UcBuyerService buyerService;
    @Resource
    private DgTagsService tagsService;
    @Resource
    private PtProductService productService;
    @Resource
    private UcMemberRelationService relationService;
    @Resource
    private PhoneBelongService phoneBelongService;
    @Resource
    private TourService tourService;
    @Resource
    private AllinPayMemberService allinPayMemberService;
    @Resource
    private UcSellerDao ucSellerDao;


    @Transactional
    @Override
    public Object add(SellerAddParam param) {

        if (StringUtils.isBlank(param.getLoginCountry())) {
            String area = phoneBelongService.getBelongByMobile(param.getMobile());
            if (!StringUtils.isBlank(area)) {
                param.setLoginCountry(area);
            }
        }

        param.setLoginCountry(StringUtils.isBlank(param.getLoginCountry()) ? "未知" : param.getLoginCountry());

        UcSeller cs = new UcSeller();
        cs.setMobile(param.getMobile());

        UcSeller seller = super.selectOne(cs);
        if (seller != null) {
            return "该手机号码已注册";
        }

        UcInviteCode inviteCode = codeService.findByNo(param.getInvitationCode());

        if (inviteCode == null) {
            return "该邀请码无效";
        }

        if (StringUtils.isBlank(param.getSmsCode())) {

            String smsCache = dgSmsRecordService.sendSmsandCache(REDIS_SELLER_ADD_DYNAMIC_CODE_COUNT, TEMPLATE_CODE_ADD, param.getMobile(), REDIS_SELLER_ADD_DYNAMIC_CODE);
            if (!StringUtils.isBlank(smsCache)) {
                return smsCache;
            }

            return setModelMap(ServiceCode.ServeCode_600, "短信验证码已发送，请您注意查收");
        }

        //get redis sms code
        Object redisSmsCode = CacheUtil.getCache().get(REDIS_SELLER_ADD_DYNAMIC_CODE + param.getMobile());
        if (redisSmsCode == null) {
            return "请输入有效的短信动态码";
        }

        if (!redisSmsCode.toString().equals(param.getSmsCode())) {
            return "请输入有效的短信动态码";
        }

        if (StringUtils.isBlank(param.getPassword())) {
            return setModelMap(ServiceCode.ServeCode_601, "密码不能为空");
        }

        if (StringUtils.isBlank(param.getTwoPassword())) {
            return setModelMap(ServiceCode.ServeCode_601, "密码不能为空");
        }

        if (!param.getPassword().equals(param.getTwoPassword())) {
            return "密码输入不一致";
        }

        if (!ToolsUtil.isYooyopwd(param.getPassword())) {
            return "密码必须为6-16位，数字与字母组合";
        }
        Date now = new Date();

        cs.setIsPassIdentity(EnumConstants.YesOrNo.NO.getValue());
        cs.setIsPassZhima(EnumConstants.YesOrNo.NO.getValue());
        cs.setIsPassPassport(EnumConstants.YesOrNo.NO.getValue());
        cs.setState(EnumConstants.State.NORMAL.value());
        cs.setUuid(IdWorker.get32UUID().toLowerCase());
        cs.setTrustValue(100);//默认100分
        cs.setCreateTime(now);
        cs.setEnable(EnumConstants.YesOrNo.YES.getValue());
        cs.setInvitationCode(param.getInvitationCode());
        cs.setSource(param.getSource());
        cs.setSellerNo(BizSequenceUtils.generateMemberNo("SellerNo"));

        if (StringUtils.isBlank(cs.getNickName())) {
            cs.setNickName("MS"
                    + BizSequenceUtils.generateRandomStr(2) +
                    RandomStringUtils.randomNumeric(3) +
                    BizSequenceUtils.generateRandomStr(2));
        }
        if (StringUtils.isBlank(cs.getAvatarRsurl())) cs.setAvatarRsurl(Constants.AVATAR_DEFAULT);

        //设置密码
        //set 密码 to redis
        CacheUtils.setRedisPassword("seller", param.getPassword(), cs.getMobile());

        UcPassword passw = new UcPassword();
        passw.setPassword(PasswordUtil.encodePassword(param.getPassword()));
        passw.setCreateTime(now);
        this.ucPasswordMapper.insert(passw);

        cs.setPasswordId(passw.getId());
        cs.setUpdateTime(now);
        super.update(cs);

        //通联创建会员
        if (!allinPayMemberService.createMember(cs.getUuid())) {
            return "用户注册失败";
        }

        //登录并返回登录信息
        TokenUtil.setTokenInfo(cs.getUuid(), cs.getId().toString());

        inviteCode.setState(EnumConstants.State.NORMAL.value());
        this.codeService.update(inviteCode);

        String sessId = saveSellerLogin(cs, param.getLoginCountry(), EnumConstants.LoginType.pwd, param.getSource());

        ucMemberLogService.saveLog(
                EnumConstants.MemberEvent.注册.name(),
                cs.getId(),
                EnumConstants.MemberType.seller.getValue(),
                "创建用户", null
        );

        //set info to redis
        CacheUtils.setMemberInfo(cs.getId(), EnumConstants.MemberType.seller, InstanceUtil.to(cs, MemberInfo.class));

        MemberInfoResult result = toSellerInfoResult(cs);
        result.setSessid(sessId);

        return result;
    }


    @Override
    @Transactional
    public Object resetpwd(ModifyPwdParam param) {
        UcSeller cs = new UcSeller();
        cs.setMobile(param.getMobile());
        cs.setState(EnumConstants.State.NORMAL.value());
        cs.setEnable(EnumConstants.YesOrNo.YES.getValue());

        UcSeller seller = super.selectOne(cs);
        if (seller == null) {
            return "不存在该会员";
        }

        cs = super.queryById(seller.getId());

        if (StringUtils.isBlank(param.getSmsCode())) {

            String smsCache = dgSmsRecordService.sendSmsandCache(REDIS_SELLER_RESETPWD_DYNAMIC_CODE_COUNT, TEMPLATE_CODE_RESTPWD, param.getMobile(), REDIS_SELLER_RESETPWD_DYNAMIC_CODE);
            if (!StringUtils.isBlank(smsCache)) {
                return smsCache;
            }

            return setModelMap(ServiceCode.ServeCode_600, "短信验证码已发送，请您注意查收");
        }

        //get redis sms code
        Object redisSmsCode = CacheUtil.getCache().get(REDIS_SELLER_RESETPWD_DYNAMIC_CODE + param.getMobile());
        if (redisSmsCode == null) {
            return "请输入有效的短信动态码";
        }

        if (!redisSmsCode.toString().equals(param.getSmsCode())) {
            return "请输入有效的短信动态码";
        }

        if (StringUtils.isBlank(param.getPassword())) {
            return setModelMap(ServiceCode.ServeCode_601, "密码不能为空");
        }

        if (StringUtils.isBlank(param.getTwoPassword())) {
            return setModelMap(ServiceCode.ServeCode_601, "密码不能为空");
        }

        if (!param.getPassword().equals(param.getTwoPassword())) {
            return "密码输入不一致";
        }

        if (!ToolsUtil.isYooyopwd(param.getPassword())) {
            return "密码必须为6-16位，数字与字母组合";
        }

        Date now = new Date();
        //set 密码 to redis
        CacheUtils.setRedisPassword("seller", param.getPassword(), cs.getMobile());

        UcPassword password = ucPasswordMapper.selectById(cs.getPasswordId());
        password.setPassword(PasswordUtil.encodePassword(param.getPassword()));
        password.setCreateTime(now);
        this.ucPasswordMapper.updateById(password);

        ucMemberLogService.saveLog(
                EnumConstants.MemberEvent.密码重置.name(),
                cs.getId(),
                EnumConstants.MemberType.seller.getValue(),
                "密码重置", null
        );

        //MemberInfoResult result = toSellerInfoResult(cs);
        return null;
    }


    @Override
    @Transactional
    public Object login(LoginParam user) {
        if (StringUtils.isBlank(user.getLoginCountry())) {
            String area = phoneBelongService.getBelongByMobile(user.getMobile());
            if (!StringUtils.isBlank(area)) {
                user.setLoginCountry(area);
            }
        }

        user.setLoginCountry(StringUtils.isBlank(user.getLoginCountry()) ? "未知" : user.getLoginCountry());

        UcSeller cs = new UcSeller();
        cs.setMobile(user.getMobile());
        cs.setState(EnumConstants.State.NORMAL.value());
        cs.setEnable(EnumConstants.YesOrNo.YES.getValue());

        UcSeller seller = super.selectOne(cs);

        if (seller == null) {
            return ModelMapUtil.setModelMap(ServiceCode.ServeCode_600, "该手机号还没注册过哦");
        }

        // String password = (String)CacheUtil.getCache().get("S:"+top.ibase4j.core.Constants.MSGCHKTYPE.LOGIN+"seller-"+ user.getMobile());
        String password = (String) CacheUtil.getLockManager().hget("S:iBase4J:seller:password:", user.getMobile());
        //set 密码 to redis
        if (PasswordUtil.verifyPassword(user.getPassword(), password)) {

            String sessId = saveSellerLogin(seller, user.getLoginCountry(), EnumConstants.LoginType.pwd, user.getLoginSource());

            MemberInfoResult result = toSellerInfoResult(seller);
            result.setLoginCountry(user.getLoginCountry());
            result.setSessid(sessId);

            return result;
        } else {

            UcPassword cpassword = ucPasswordMapper.selectById(seller.getPasswordId());
            if (cpassword != null && PasswordUtil.verifyPassword(user.getPassword(), cpassword.getPassword())) {

                String sessId = saveSellerLogin(seller, user.getLoginCountry(), EnumConstants.LoginType.pwd, user.getLoginSource());

                MemberInfoResult result = toSellerInfoResult(seller);
                result.setLoginCountry(user.getLoginCountry());
                result.setSessid(sessId);

                //set 密码 to redis
                CacheUtils.setRedisPassword("seller", user.getPassword(), user.getMobile());

                return result;
            }
            return "用户名或者密码无效";
        }
    }


    @Override
    @Transactional
    public Object updateInfo(MemberUpdateParam params) {
        UcSeller cSeller = super.queryById(params.getId());

        if (cSeller == null) {
            return "不存在该会员";
        }

        if (!StringUtils.isBlank(params.getPersonalizedSignature())) {
            ucMemberLogService.saveLog(EnumConstants.MemberEvent.修改信息.name(),
                    cSeller.getId(),
                    EnumConstants.MemberType.seller.getValue(),
                    "个性签名修改为：" + params.getPersonalizedSignature(), null);
        }

        if (!StringUtils.isBlank(params.getClassifyTags())) {
            ucMemberLogService.saveLog(EnumConstants.MemberEvent.修改信息.name(),
                    cSeller.getId(),
                    EnumConstants.MemberType.seller.getValue(),
                    "用户标签修改为：" + params.getClassifyTags(), null);
        }

        if (!StringUtils.isBlank(params.getLiveCountry())) {
            ucMemberLogService.saveLog(EnumConstants.MemberEvent.修改信息.name(),
                    cSeller.getId(),
                    EnumConstants.MemberType.seller.getValue(),
                    "居住地修改为：" + params.getLiveCountry(), null);
        }

        if (!StringUtils.isBlank(params.getNickName())) {
            ucMemberLogService.saveLog(EnumConstants.MemberEvent.修改信息.name(),
                    cSeller.getId(),
                    EnumConstants.MemberType.seller.getValue(),
                    "昵称修改为：" + params.getNickName(), null);
        }

        if (!StringUtils.isBlank(params.getOftenPlace())) {
            ucMemberLogService.saveLog(EnumConstants.MemberEvent.修改信息.name(),
                    cSeller.getId(),
                    EnumConstants.MemberType.seller.getValue(),
                    "常去地修改为：" + params.getOftenPlace(), null);
        }

        if (!StringUtils.isBlank(params.getAvatarRsurl())) {
            cSeller.setAvatarRsurl(params.getAvatarRsurl());
            ucMemberLogService.saveLog(EnumConstants.MemberEvent.修改信息.name(),
                    cSeller.getId(),
                    EnumConstants.MemberType.seller.getValue(),
                    "头像变更", null);
        }

        BeanUtils.copyProperties(params, cSeller);
        if (StringUtils.isBlank(cSeller.getSellerNo())) {
            cSeller.setSellerNo(BizSequenceUtils.generateMemberNo("SellerNo"));
        }
        super.update(cSeller);

        //清除缓存
        CacheUtils.delSellerInfo(cSeller.getId());

        return null;
    }


    @Override
    @Transactional
    public Object updateMobile(Long currUser, String newMobile, String smsCode) {
        if (currUser == null) return "请重新登录";

        if (StringUtils.isBlank(newMobile)) {
            return "请输入手机号码";
        }

        if (!ToolsUtil.isMobile(newMobile)) {
            return "请输入有效的手机号码";
        }

        UcSeller cSeller = super.queryById(currUser);

        if (cSeller == null) {
            return "不存在该会员";
        }

        UcSeller cb = new UcSeller();
        cb.setMobile(newMobile);
        cb.setState(EnumConstants.State.NORMAL.value());
        cb.setEnable(EnumConstants.YesOrNo.YES.getValue());

        UcSeller seller = super.selectOne(cb);
        if (seller != null) {
            return "该手机号码已被绑定";
        }

        if (StringUtils.isBlank(smsCode)) {

            String smsCache = dgSmsRecordService.sendSmsandCache(REDIS_SELLER_UPDATEMOBILE_DYNAMIC_CODE_COUNT, TEMPLATE_CODE_RESTPWD, newMobile, REDIS_SELLER_UPDATEMOBILE_DYNAMIC_CODE);
            if (!StringUtils.isBlank(smsCache)) {
                return smsCache;
            }
            return setModelMap(ServiceCode.ServeCode_600, "短信验证码已发送，请您注意查收");
        }

        //get redis sms code
        Object redisSmsCode = CacheUtil.getCache().get(REDIS_SELLER_UPDATEMOBILE_DYNAMIC_CODE + newMobile);
        if (redisSmsCode == null) {
            return "请输入有效的短信动态码";
        }

        if (!redisSmsCode.toString().equals(smsCode)) {
            return "请输入有效的短信动态码";
        }
        cSeller.setMobile(newMobile);

        //set info to redis
        CacheUtils.setMemberInfo(cSeller.getId(), EnumConstants.MemberType.seller, InstanceUtil.to(cSeller, MemberInfo.class));

        ucMemberLogService.saveLog(EnumConstants.MemberEvent.修改信息.name(),
                cSeller.getId(),
                EnumConstants.MemberType.seller.getValue(),
                "手机号码变更为：" + newMobile, null);

        super.update(cSeller);
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public Object querySellerListByName(SellerPageParam param) {
        //封装查询参数
        EntityWrapper<UcSeller> ew = new EntityWrapper<>();
        ew.eq("state", EnumConstants.State.NORMAL.value());
        ew.eq("enable_", EnumConstants.YesOrNo.YES.getValue());
        if (!StringUtils.isBlank(param.getKeyword())) {
            ew.and("nick_name like {0} or seller_no = {1}", "%" + param.getKeyword().trim() + "%", param.getKeyword().trim());
        }

        List<SellerPageResult> list = getSellerPageResults(param, ew);

        Pagination<SellerPageResult> page = new Pagination<>();
        if (list.size() > 0) {
            page.setRecords(list);
            page.setCurrent(param.getCurrent());
            page.setSize(param.getSize());
            page.setTotal(list.size());
        }

        return page;
    }


    private List<SellerPageResult> getSellerPageResults(SellerPageParam param, EntityWrapper<UcSeller> ew) {
        List<SellerPageResult> list;
        RowBounds rb = new RowBounds((param.getCurrent() - 1) * param.getSize(), param.getSize());
        List<UcSeller> mlist = mapper.selectPage(rb, ew);

        list = mlist.stream().map(o -> {
            SellerPageResult r = InstanceUtil.to(o, SellerPageResult.class);
            List<PtProduct> products = productService.getProductBySellerId(r.getId());
            if (org.apache.commons.collections.CollectionUtils.isNotEmpty(products)) {
                List<String> imgs = products.stream().map(PtProduct::getCover).collect(Collectors.toCollection(ArrayList::new));
                r.setProductRsurlList(imgs);
            }
            r.setIsSelfStr(EnumConstants.YesOrNo.of(o.getIsSelf()).getSellerLabel());
            if(!StringUtils.isBlank(o.getClassifyTags())){
                r.setClassifyTagsList(Arrays.asList(o.getClassifyTags().split(",")));
            }
            return r;
        }).collect(Collectors.toCollection(ArrayList::new));
        return list;
    }


    /**
     * 1.按置顶查询
     * 2.没有置顶买手按照个人标签查询
     * 3.按个人标签再查不到，按照系统热门标签查询
     */
    @Override
    @Transactional(readOnly = true)
    public Pagination<SellerPageResult> topList(SellerPageParam param) {
        //封装查询参数
        EntityWrapper<UcSeller> ew = new EntityWrapper<>();
        ew.eq("state", EnumConstants.State.NORMAL.value());
        ew.eq("enable_", EnumConstants.YesOrNo.YES.getValue());
        ew.eq("is_top", EnumConstants.YesOrNo.YES.getValue());
        ew.orderDesc(Lists.newArrayList("top_time"));
        int count = mapper.selectCount(ew);

        if (count == 0) {
            ew = new EntityWrapper<>();
            ew.eq("state", EnumConstants.State.NORMAL.value());
            ew.eq("enable_", EnumConstants.YesOrNo.YES.getValue());

            if (param.getBuyerId() != null) {
                //个人标签
                UcBuyer buyer = buyerService.queryById(param.getBuyerId());
                if (buyer != null && org.apache.commons.lang3.StringUtils.isNotBlank(buyer.getClassifyTags())) {
                    ew.where("classify_tags REGEXP replace({0}, ',', '|')", buyer.getClassifyTags());
                    count = mapper.selectCount(ew);
                }
            }

            if (count == 0) {
                //系统默认热门标签
                Map<String, Object> map = InstanceUtil.newHashMap("isRecommend", EnumConstants.YesOrNo.YES.getValue());
                map.put("enable", EnumConstants.YesOrNo.YES.getValue());
                List<DgTags> tags = tagsService.queryList(map);
                if (!CollectionUtils.isEmpty(tags)) {
                    List<String> tagStr = tags.parallelStream().map(DgTags::getTagName).collect(Collectors.toList());
                    ew.where("classify_tags REGEXP replace({0}, ',', '|')", org.apache.commons.lang3.StringUtils.join(tagStr, ","));
                    count = mapper.selectCount(ew);
                }
            }

            ew.orderDesc(Lists.newArrayList("trade_count", "trust_value"));
        }

        List<SellerPageResult> list = null;
        if (count > 0) {
            //分页查询，封装订单信息
            list = getSellerPageResults(param, ew);
        }

        Pagination<SellerPageResult> page = new Pagination<>();
        page.setRecords(list);
        page.setCurrent(param.getCurrent());
        page.setSize(param.getSize());
        page.setTotal(count);

        return page;
    }


    @Override
    @Transactional(readOnly = true)
    public List<UcSeller> querySellerList(List<Long> ids) {
        if (CollectionUtils.isNotEmpty(ids)) {
            EntityWrapper<UcSeller> ew = new EntityWrapper<>();
            ew.eq("enable_", EnumConstants.YesOrNo.YES.getValue());
            ew.eq("state", EnumConstants.State.NORMAL.value());
            ew.in("id_", ids);
            return mapper.selectList(ew);
        }
        return null;
    }


    @Override
    @Transactional(readOnly = true)
    public MemberInfo getMemberInfo(Long memberId, Integer memberType) {
        MemberInfo info = CacheUtils.getMemberInfo(memberId, EnumConstants.MemberType.valueOf(memberType));

        if (info == null) {
            if (memberType == EnumConstants.MemberType.seller.getValue()) {
                UcSeller ucSeller = super.queryById(memberId);
                if (ucSeller != null) {
                    info = InstanceUtil.to(ucSeller, MemberInfo.class);
                }
            } else {
                UcBuyer buyer = buyerService.queryById(memberId);
                if (buyer != null) {
                    info = InstanceUtil.to(buyer, MemberInfo.class);
                }
            }
            CacheUtils.setMemberInfo(memberId, EnumConstants.MemberType.valueOf(memberType), info);
        }

        return info;
    }


    @Override
    @Transactional(readOnly = true)
    public Map<String, MemberInfo> getManyMemberInfos(List<Long> memberIds, Integer memberType) {
        Map<String, MemberInfo> memberMap = new HashMap<>();
        List<Long> unCacheMemberIds = new ArrayList<>();
        if (memberIds != null && memberIds.size() > 0) {
            for (Long memberId : memberIds) {
                MemberInfo info = CacheUtils.getMemberInfo(memberId, EnumConstants.MemberType.valueOf(memberType));
                if (info != null && memberMap.containsKey(memberId.toString())) {
                    memberMap.put(memberId.toString(), info);
                } else {
                    //当前会员没有在缓存中,需要进一步查询数据库
                    unCacheMemberIds.add(memberId);
                }
            }
        }

        //从数据库查询会员信息
        if (unCacheMemberIds.size() > 0) {
            if (memberType == EnumConstants.MemberType.seller.getValue()) {
                //批量查询买手信息
                List<MemberInfo> sellerList = getManySellerInfos(memberIds);
                sellerList.forEach(t -> {
                    memberMap.put(t.getId().toString(), t);
                });
            } else {
                //批量查询买家信息
                Object buyerResult = buyerService.queryBuyerList(memberIds);
                if (buyerResult != null) {
                    List<UcSeller> buyerList = (List<UcSeller>) buyerResult;
                    buyerList.forEach(t -> {
                        memberMap.put(t.getId().toString(), InstanceUtil.to(t, MemberInfo.class));
                    });
                }
            }
        }
        return memberMap;
    }


    @Override
    @Transactional(readOnly = true)
    public List<MemberInfo> getManySellerInfos(List<Long> sellerIds) {
        List<MemberInfo> sellerInfoList = ucSellerDao.getManySellerInfos(sellerIds);
        sellerInfoList.forEach(t -> CacheUtils.setMemberInfo(t.getId(), EnumConstants.MemberType.seller, t));
        return sellerInfoList;
    }


    @Override
    @Transactional(readOnly = true)
    public Object getSellerList() {
        EntityWrapper<UcSeller> ew = new EntityWrapper<>();
        ew.eq("enable_", EnumConstants.YesOrNo.YES.getValue());
        ew.eq("state", EnumConstants.State.NORMAL.value());
        return mapper.selectList(ew);
    }


    @Override
    @Transactional(readOnly = true)
    public MemberInfoResult getInfo(Long id) {
        UcSeller ucSeller = super.queryById(id);
        ucSeller.setPasswordId(null);

        MemberInfoResult result = InstanceUtil.to(ucSeller, MemberInfoResult.class);
        if (result.getGender() != null) {
            result.setGenderLabel(EnumConstants.Gender.valueOf(result.getGender()).getLabel());
        }
        if (result.getIsPassIdentity() != null) {
            result.setIsPassIdentityLabel(EnumConstants.YesOrNo.of(result.getIsPassIdentity()).getRealLabel());
        }
        if (result.getIsPassPassport() != null) {
            result.setIsPassPassportLabel(EnumConstants.YesOrNo.of(result.getIsPassPassport()).getRealLabel());
        }
        if (result.getIsPassZhima() != null) {
            result.setIsPassZhimaLabel(EnumConstants.YesOrNo.of(result.getIsPassZhima()).getRealLabel());
        }

        QrCodeInfo info = new QrCodeInfo();
        info.setType(1);
        info.setContent(id.toString());
        result.setQrCode(QRCodeUtils.createQrcode(JsonUtil.toJson(info)));
        result.setFollowCount(relationService.countFansByToMerberId(id));

        //个人信息设置与未上传登机牌的行程
        result.setIsFinishSetInfo(isFinishSetInfo(id, EnumConstants.MemberType.seller.getValue()));
        result.setNoCheckPicTourIds(tourService.queryTourIdsNoCheckPic(id, EnumConstants.YesOrNo.NO.getValue()));

        return result;
    }


    @Override
    @Transactional(readOnly = true)
    public Integer isFinishSetInfo(Long id, Integer memberType) {
        if (memberType == EnumConstants.MemberType.seller.getValue()) {
            //买手
            UcSeller seller = super.queryById(id);
            if (Constants.AVATAR_DEFAULT.equals(seller.getAvatarRsurl()) || StringUtils.isEmpty(seller.getNickName())
                    || StringUtils.isEmpty(seller.getPersonalizedSignature())
                    || StringUtils.isEmpty(seller.getLiveCountry())
                    || StringUtils.isEmpty(seller.getOftenPlace())) {
                return EnumConstants.YesOrNo.NO.getValue();
            }
        } else {
            //买家、背包客
            UcBuyer buyer = buyerService.queryById(id);
            if (Constants.AVATAR_DEFAULT.equals(buyer.getAvatarRsurl()) || StringUtils.isEmpty(buyer.getNickName())
                    || StringUtils.isEmpty(buyer.getPersonalizedSignature())
                    || StringUtils.isEmpty(buyer.getLiveCountry())
                    || StringUtils.isEmpty(buyer.getOftenPlace())) {
                return EnumConstants.YesOrNo.NO.getValue();
            }
        }
        return EnumConstants.YesOrNo.YES.getValue();
    }


    private String saveSellerLogin(UcSeller seller, String loginCountry, EnumConstants.LoginType code, Integer loginSource) {
        String sessid = UUID.randomUUID().toString();

        //踢出其他登录用户
        ucMemberLoginService.kickOut(seller.getId(), EnumConstants.MemberType.seller.getValue(), sessid);

        TokenUtil.setTokenInfo(sessid, seller.getId().toString());

        UcMemberLogin login = new UcMemberLogin();
        login.setLoginCode(seller.getRealName());
        login.setLoginCountry(loginCountry);
        login.setLoginType(code.getValue());
        login.setMemberId(seller.getId());
        login.setMemberType(EnumConstants.MemberType.seller.getValue());
        login.setMobile(seller.getMobile());
        login.setToken(sessid);
        login.setLoginSource(loginSource);
        this.ucMemberLoginService.saveLogin(login);

        seller.setLoginSource(loginSource);
        super.update(seller);

        return sessid;
    }

    private MemberInfoResult toSellerInfoResult(UcSeller ucSeller) {
        MemberInfoResult result = InstanceUtil.to(ucSeller, MemberInfoResult.class);
        if (result.getGender() != null) result.setGenderLabel(EnumConstants.Gender.valueOf(result.getGender()).getLabel());
        if (result.getIsPassIdentity() != null) result.setIsPassIdentityLabel(EnumConstants.YesOrNo.of(result.getIsPassIdentity()).getRealLabel());
        if (result.getIsPassPassport() != null) result.setIsPassPassportLabel(EnumConstants.YesOrNo.of(result.getIsPassPassport()).getRealLabel());
        if (result.getIsPassZhima() != null) result.setIsPassZhimaLabel(EnumConstants.YesOrNo.of(result.getIsPassZhima()).getRealLabel());
        return result;
    }


}
