package com.xmyy.common;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public abstract class EnumConstants {

    //常量定义分了六块区域
    //第一块公共常量
    //第二块订单常量
    //第三块产品常量
    //第四块会员常量
    //第五块物流常量
    //第六块支付常量
    //第七块框架常量


    /*************************************** 公共常量定义开始 ***********************************************/

    /**
     * 是否可用
     */
    public enum Enable {
        ENABLE(1, "可用"),
        DISABLE(0, "不可用");

        private Integer value;
        private String name;

        Enable(Integer value, String name) {
            this.value = value;
            this.name = name;
        }

        public Integer getValue() {
            return value;
        }

        public String getName() {
            return name;
        }
    }

    /**
     * 业务码
     */
    public enum BizCode {
        InviteCodeNO("Seller_Invite_Code_NO", 6),                //数字+字母随机
        TourNo("Product_Tour_NO", "T", 9),                        //行程编号（T代表前戳，9代表数字的位数）
        BrandNo("Brand_NO", "BR", 9),                             //品牌编号
        ProductNo("Product_NO", "PR", 9),                         //商品编号
        SeriesNo("Series_NO", "S", 9),                            //系列编号
        DemandNo("Demand_No", "X", 9),                            //需求单编号
        OrderNo("Order_No", "", 12),                              //订单编号
        RefundNo("Refund_No", "RF", 12),                          //售后单编号
        PayLogNo("Pay_Log_No", "PL", 12);                         //支付单号

        private boolean ordered;
        private int length = 12;
        private String sequence;
        private String idPrefix;                                //编号前戳

        BizCode() {
        }

        BizCode(String sequence) {
            this.sequence = sequence;
        }

        BizCode(String sequence, int length) {
            this.sequence = sequence;
            this.length = length;
        }

        BizCode(boolean ordered) {
            this.ordered = ordered;
        }

        BizCode(String sequence, String idPrefix, int length) {
            this.sequence = sequence;
            this.idPrefix = idPrefix;
            this.length = length;

        }

        BizCode(int length) {
            this.length = length;
        }

        BizCode(String sequence, boolean ordered) {
            this.ordered = ordered;
            this.sequence = sequence;
        }

        public boolean ordered() {
            return ordered;
        }

        public String sequence() {
            return sequence;
        }

        public String idPrefix() {
            return idPrefix;
        }

        public int length() {
            return length;
        }
    }

    /**
     * 顶层数据状态定义，其他状态采用继承的方式扩展
     */
    public enum State {
        NORMAL(50, "有效", "上架", "已使用"),
        ABNORMAL(0, "无效", "发布中", "待使用"),
        REJECT(-1, "已失效"),
        UNAPPROVED(10, "审核不通过"),
        DELETED(-50, "认证不通过", "下架");

        private Integer value;
        private String validLabel;
        private String shelvesLabel;
        private String useLabel;

        State(Integer value) {
            this.value = value;
        }

        State(Integer value, String validLabel) {
            this.value = value;
            this.validLabel = validLabel;
        }

        State(Integer value, String validLabel, String shelvesLabel) {
            this.value = value;
            this.validLabel = validLabel;
            this.shelvesLabel = shelvesLabel;
        }

        State(Integer value, String validLabel, String shelvesLabel, String useLabel) {
            this.value = value;
            this.validLabel = validLabel;
            this.shelvesLabel = shelvesLabel;
            this.useLabel = useLabel;
        }

        public Integer getValue() {
            return value;
        }

        public String getUseLabel() {
            return useLabel;
        }

        public Integer value() {
            return this.value;
        }

        public String getShelvesLabel() {
            return shelvesLabel;
        }

        public String getValidLabel() {
            return validLabel;
        }

        public static State getState(Integer value) {
            for (State state : values()) {
                if (value.equals(state.value)) {
                    return state;
                }
            }
            return null;
        }

        public static Map<Integer, String> toValidMap() {
            Map<Integer, String> map = new HashMap<Integer, String>();
            for (State e : State.values()) {
                if (null == e.getValidLabel()) {
                    continue;
                }
                map.put(e.value(), e.getValidLabel());
            }
            return map;
        }

        //状态（0发布中，10,审核不通过，50上架，-50下架）
        public static Map<Integer, String> toShelvesMap() {
            Map<Integer, String> map = new HashMap<Integer, String>();
            for (State e : State.values()) {
                if (null == e.getShelvesLabel()) {
                    continue;
                }
                map.put(e.value(), e.getShelvesLabel());
            }
            map.put(State.UNAPPROVED.value, State.DELETED.validLabel);
            return map;
        }

        //0待使用，50已使用，-1已失效
        public static Map<Integer, String> toUseMap() {
            Map<Integer, String> map = new HashMap<Integer, String>();
            for (State e : State.values()) {
                if (null == e.getUseLabel()) {
                    continue;
                }
                map.put(e.value(), e.getUseLabel());
            }
            map.put(State.REJECT.value, State.REJECT.validLabel);
            return map;
        }
    }

    /**
     * 用户类型
     */
    public enum MemberType {
        seller(1, "买手"),
        buyer(2, "买家"),
        packer(3, "背包客");

        private final int value;
        private final String name;

        MemberType(int value, String name) {
            this.value = value;
            this.name = name;
        }

        public int getValue() {
            return value;
        }

        public String getName() {
            return name;
        }

        public static MemberType valueOf(int value) {
            for (MemberType inst : values()) {
                if (value == inst.value)
                    return inst;
            }
            throw new IllegalArgumentException("不支持的常量：" + value);
        }

        public static Map<Integer, String> getMap() {
            Map<Integer, String> map = new HashMap<Integer, String>();
            for (MemberType inst : values()) {
                map.put(inst.value, inst.name);
            }
            return map;
        }
    }

    public enum YesOrNo {
        YES(1, "是", "已认证", "资深买手"),
        NO(0, "否", "未认证", "大众买手");

        private Integer value;
        private String label;
        private String realLabel;
        private String sellerLabel;

        YesOrNo(Integer value, String label, String realLabel, String sellerLabel) {
            this.value = value;
            this.label = label;
            this.realLabel = realLabel;
            this.sellerLabel = sellerLabel;
        }

        public Integer getValue() {
            return value;
        }

        public String getLabel() {
            return label;
        }

        public String getRealLabel() {
            return realLabel;
        }

        public String getSellerLabel() {
            return sellerLabel;
        }

        public static YesOrNo of(Integer val) {
            return Stream.of(YesOrNo.values())
                    .filter(t -> t.value.equals(val))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("error arg:" + val));
        }
    }


    public enum AreaOrCountryRedisKeys {
        AREA_TREE_KEY("areaTreeInfo", "省市区县信息树"),
        AREA_JIEDAO_KEY("jiedaoInfo", "接到信息"),
        COUNTRY_TREE_KEY("countryTreeInfo", "国家信息"),
        HOT_COUNTRY_KEY("hotCountryInfo", "热门国家信息"),
        COUNTRY_KEY("countryInfo", "国家信息");

        private String key;
        private String info;

        AreaOrCountryRedisKeys(String key, String info) {
            this.key = key;
            this.info = info;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }
    }

    /**
     * 搜索引擎热词：SEARCH_HOTS；搜索引擎买手同步：SELLER_LAST_INDEX_TIME
     */
    public enum AdminDicType {
        SEARCH_HOTS("SEARCH_HOTS", "搜索引擎热词"),
        SELLER_LAST_INDEX_TIME("SELLER_LAST_INDEX_TIME", "搜索引擎买手同步标识"),
        PACKER_LAST_INDEX_TIME("PACKER_LAST_INDEX_TIME", "搜索引擎背包客同步标识"),
        CIRCLE_LAST_INDEX_TIME("CIRCLE_LAST_INDEX_TIME", "搜索引擎动态同步标识"),
        PRODUCT_LAST_INDEX_TIME("PRODUCT_LAST_INDEX_TIME", "搜索商品同步时间标识");

        private final String value;
        private final String name;

        AdminDicType(String value, String name) {
            this.value = value;
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public String getValue() {
            return value;
        }

        public static Map<String, String> getMap() {
            Map<String, String> map = new HashMap<String, String>();
            for (AdminDicType inst : values()) {
                map.put(inst.value, inst.name);
            }
            return map;
        }
    }


    /**
     * 用户设备类型
     */
    public enum DeviceType {
        Android(1, "Android"),
        IOS(2, "IOS"),
        TXapplet(3, "小程序"),
        PC(4, "PC");

        private final Integer value;
        private final String name;

        DeviceType(Integer value, String name) {
            this.value = value;
            this.name = name;
        }

        public Integer getValue() {
            return value;
        }

        public String getName() {
            return name;
        }

        public static DeviceType valueOf(Integer value) {
            for (DeviceType inst : values()) {
                if (null != value && value.equals(inst.value)) {
                    return inst;
                }
            }
            throw new IllegalArgumentException("不支持的常量：" + value);
        }
    }
    /*************************************** 公共常量定义结束 ***********************************************/


    /*************************************** 订单常量定义开始 ***********************************************/
    /**
     * 需求状态，状态文字分别对应买手端、买家端、背包客
     */
    public enum DemandStatus {
        WATI_TO_ACCEPT(1, "待受理", "等待买手受理", "待受理"),
        WATI_TO_APPOINT(2, "等待买家指定", "待指定", "等待买家指定"),
        WATI_TO_SEND(3, "待发货", "买手已受理", "待发货"),
        HAS_SENDED(4, "已发货", "买手已发货", "已发货"),
        FINISH(5, "已完成", "已完成", "已完成"),
        INVALID(6, "已失效", "已失效", "已失效");

        private final Integer value;
        private final String[] info;

        DemandStatus(Integer value, String... info) {
            this.value = value;
            this.info = info;
        }

        public Integer getValue() {
            return value;
        }

        public String[] getInfo() {
            return info;
        }

        public static String getInfo(Integer value, Integer role) {
            for (DemandStatus ds : values()) {
                if (ds.value.equals(value)) {
                    return ds.info[role - 1];
                }
            }
            throw new IllegalArgumentException("不支持的常量：" + value);
        }
    }

    /**
     * 需求接单状态
     */
    public enum DemandAcceptStatus {
        ACCEPT_WAIT_CONFIRM(1, "已接单待指定"),
        ACCEPT_SUCCESS_CONFIRM(2, "已接单且已被指定"),
        ACCEPT_FAILURE_CONFIRM(3, "已接单未被指定，买家指定了其他买手"),
        ACCEPT_SUCCESS_PASSIVE(4, "买家需求指定买手，等待买手受理"),
        INVALID(5, "已失效");

        private final Integer value;
        private final String info;

        DemandAcceptStatus(Integer value, String info) {
            this.value = value;
            this.info = info;
        }

        public Integer getValue() {
            return value;
        }

        public String getInfo() {
            return info;
        }

        public static String getInfo(Integer value) {
            for (DemandAcceptStatus ds : values()) {
                if (ds.value.equals(value)) {
                    return ds.info;
                }
            }
            throw new IllegalArgumentException("不支持的常量：" + value);
        }
    }

    /**
     * 买家取消需求原因
     */
    public enum BuyerCancelDemand {
        IN_HURRY(1, "赶时间，不想等了"),
        SELLER_NOT_FOUND(2, "找不到满意买手"),
        OTHER_WAYS(3, "通过其他方式买到了");

        private final Integer value;
        private final String name;

        BuyerCancelDemand(Integer value, String name) {
            this.value = value;
            this.name = name;
        }

        public Integer getValue() {
            return value;
        }

        public String getName() {
            return name;
        }
    }

    /**
     * 买手取消需求原因
     */
    public enum SellerCancelDemand {
        IN_HURRY(1, "赶时间，不想等了"),
        TOUR_CHANGED(2, "行程有变"),
        PRICE_RISING(3, "商品涨价了");

        private final Integer value;
        private final String name;

        SellerCancelDemand(Integer value, String name) {
            this.value = value;
            this.name = name;
        }

        public Integer getValue() {
            return value;
        }

        public String getName() {
            return name;
        }
    }

    /**
     * 订单状态，状态文字分别对应买手端、买家端、背包客
     */
    public enum OrderStatus {
        WATI_TO_PAY(1, "等待买家付款", "待付款", "等待买家付款"),
        WATI_TO_SEND(2, "待发货", "待发货", "待发货"),
        WATI_TO_RECIEVE(3, "已发货", "待收货", "已发货"),
        SALE_SUCCESS(4, "交易成功", "交易成功", "交易成功"),
        AFTER_SALE(5, "售后处理中", "售后处理中", "售后处理中"),
        REFUND_SUCCESS(6, "退款成功", "退款成功", "退款成功"),
        SALE_CLOSE(7, "交易关闭", "交易关闭", "交易关闭");

        private final Integer value;
        private final String[] info;

        OrderStatus(Integer value, String... info) {
            this.value = value;
            this.info = info;
        }

        public Integer getValue() {
            return value;
        }

        public String[] getInfo() {
            return info;
        }

        public static String getInfo(Integer value, Integer role) {
            for (OrderStatus os : values()) {
                if (os.value.equals(value)) {
                    return os.info[role - 1];
                }
            }
            throw new IllegalArgumentException("不支持的常量：" + value);
        }
    }

    /**
     * 订单业务类型/商品类型
     */
    public enum OrderType {
        PRE_SALE(1, "预售"),
        STOCKED_SALE(2, "现货"),
        DEMAND_SALE(3, "需求");

        private final Integer value;
        private final String type;

        OrderType(Integer value, String type) {
            this.value = value;
            this.type = type;
        }

        public Integer getValue() {
            return value;
        }

        public String getType() {
            return type;
        }
    }

    /**
     * 订单评价状态，文字信息为返回前端的文字
     */
    public enum EvaluateStatus {
        NO_EVALUATE(0, "评价"), //未评价
        HAS_EVALUATE(1, "追加评价"), //已评价，未追评
        FINISH_EVALUATE(2, ""); //已追评

        private final Integer value;
        private final String info;

        EvaluateStatus(Integer value, String info) {
            this.value = value;
            this.info = info;
        }

        public Integer getValue() {
            return value;
        }

        public String getInfo() {
            return info;
        }

        public static String getInfo(Integer value) {
            for (EvaluateStatus es : values()) {
                if (es.value.equals(value)) {
                    return es.info;
                }
            }
            throw new IllegalArgumentException("不支持的常量：" + value);
        }
    }

    /**
     * 订单操作行为
     */
    public enum OrderOpt {
        CREATE_ORDER(1, "生成待支付订单"),
        CANCEL_PAY(2, "取消支付订单"),
        PAY(3, "支付订单"),
        CREATE_DEMAND_ORDER(4, "需求生成订单"),
        DELIVER(5, "买手发货"),
        CONFIRM_RECEIVE(6, "确认收货"),
        AFTER_SALE(7, "申请售后"),
        AGREE_REFUND(8, "确认退款"),
        CANCEL_WAIT_TO_SEND(9, "取消待发货订单");

        private final Integer status;
        private final String opt;

        OrderOpt(Integer status, String opt) {
            this.status = status;
            this.opt = opt;
        }

        public Integer getStatus() {
            return status;
        }

        public String getOpt() {
            return opt;
        }
    }

    /**
     * 取消订单原因
     */
    public enum CancelOrderReason {
        WRONG_OPTION(1, "我不想买了"),
        REORDER(2, "信息填写错误，重新拍"),
        REGRET_BUY(3, "买手缺货"),
        OTHER(4, "其他"),
        TOUR_CLOSE(5, "行程关闭");

        private final Integer value;
        private final String name;

        CancelOrderReason(Integer value, String name) {
            this.value = value;
            this.name = name;
        }

        public Integer getValue() {
            return value;
        }

        public String getName() {
            return name;
        }

        public static String getName(Integer value) {
            for (CancelOrderReason cor : values()) {
                if (cor.value.equals(value)) {
                    return cor.name;
                }
            }
            throw new IllegalArgumentException("不支持的常量：" + value);
        }
    }

    /**
     * 售后原因
     */
    public enum AfterSaleReason {
        JUST_CANCEL(1, "拍多/拍错/不想买"),
        CONSENSUS(2, "协商一致退款"),
        OUT_OF_STOCK(3, "买手缺货"),
        NEVER_SEND(4, "未按约定时间发货"),
        //以上为待发货的售后原因
        //下面为已发货的售后原因
        NEVER_RECIEVE(5, "一直未收到货"),
        GOODS_BROKE(6, "物品破损"),
        WRONG_THING(7, "物品错发/漏发"),
        NO_ACCORD_WITH(8, "与描述不符"),
        QUALITY_PROBLEM(9, "质量问题"),
        OTHER(10, "其他");

        private final Integer value;
        private final String name;

        AfterSaleReason(Integer value, String name) {
            this.value = value;
            this.name = name;
        }

        public Integer getValue() {
            return value;
        }

        public String getName() {
            return name;
        }

        public static String getName(Integer value) {
            for (AfterSaleReason asr : values()) {
                if (asr.value.equals(value)) {
                    return asr.name;
                }
            }
            throw new IllegalArgumentException("不支持的常量：" + value);
        }
    }

    /**
     * 售后处理状态
     */
    public enum AfterSaleStatus {
        ON_DEAL(0, "售后处理中"),
        REFUND_SUCCESS(1, "退款成功");

        private final Integer status;
        private final String name;

        AfterSaleStatus(Integer status, String name) {
            this.status = status;
            this.name = name;
        }

        public Integer getStatus() {
            return status;
        }

        public String getName() {
            return name;
        }
    }

    /**
     * 账单删除状态
     */
    public enum BillDeleteStatus {
        ENABLE(0, "未删除"),
        DELETE(1, "已删除");

        private final Integer status;
        private final String name;

        BillDeleteStatus(Integer status, String name) {
            this.status = status;
            this.name = name;
        }

        public Integer getStatus() {
            return status;
        }

        public String getName() {
            return name;
        }
    }

    /*************************************** 订单常量定义结束 ***********************************************/


    /*************************************** 产品常量定义开始 ***********************************************/
    /**
     * 行程站点签到状态
     */
    public enum SignInStatus {
        NO(0, "未签到"),
        YES(1, "以签到");

        private final Integer value;
        private final String type;

        SignInStatus(Integer value, String type) {
            this.value = value;
            this.type = type;
        }

        public Integer getValue() {
            return value;
        }

        public String getType() {
            return type;
        }
    }

    /**
     * 行程取消原因
     */
    public enum CancelWay {
        OVER_TIME(0, "系统超时"),
        CANCEL_BY_HAND(1, "手动取消");

        private final Integer value;
        private final String type;

        CancelWay(Integer value, String type) {
            this.value = value;
            this.type = type;
        }

        public Integer getValue() {
            return value;
        }

        public String getType() {
            return type;
        }
    }

    /**
     * 行程状态
     */
    public enum TourStatus {
        INVALID(0, "已失效，已下架"),
        WAITING(1, "未进行"),
        RUNNING(2, "进行中"),//后台管理 运行中
        FINISH(3, "已完成");

        private final Integer value;
        private final String type;

        TourStatus(Integer value, String type) {
            this.value = value;
            this.type = type;
        }

        public Integer getValue() {
            return value;
        }

        public String getType() {
            return type;
        }

        public static String getType(Integer value) {
            for (TourStatus asr : values()) {
                if (asr.value.equals(value)) {
                    return asr.type;
                }
            }
            throw new IllegalArgumentException("不支持的常量：" + value);
        }
    }

    /**
     * 行程登机牌类型
     */
    public enum CheckType {
        NO_CERT(0, "登机牌"),
        WAIT_AUDIT(1, "登机小票");

        private final Integer value;
        private final String type;

        CheckType(Integer value, String type) {
            this.value = value;
            this.type = type;
        }

        public Integer getValue() {
            return value;
        }

        public String getType() {
            return type;
        }
    }

    /**
     * 行程审核状态
     */
    public enum TourAuditStatus {
        NO_CERT(0, "无证件，未上传"),
        WAIT_AUDIT(1, "待审核，审核中"),
        PASS_AUDIT(2, "审核通过"),
        UNPASS_AUDIT(3, "审核不通过");

        private final Integer value;
        private final String type;

        TourAuditStatus(Integer value, String type) {
            this.value = value;
            this.type = type;
        }

        public Integer getValue() {
            return value;
        }

        public String getType() {
            return type;
        }
    }

    /**
     * 站点类型
     */
    public enum TourSiteType {
        START_STIE(0, "起点站"),
        MIDDLE_SITE(1, "中途站"),
        RETURN_SITE(2, "返程站");

        private final Integer value;
        private final String type;

        TourSiteType(Integer value, String type) {
            this.value = value;
            this.type = type;
        }

        public Integer getValue() {
            return value;
        }

        public String getType() {
            return type;
        }
    }

    /**
     * 行程卡片位置
     */
    public enum TourCardType {
        BUYER_INDEX_ONE(0, "买家首页轮播展示", "325x160"),
        BUYER_INDEX_TWO(1, "买家首页更多展示", "335x165"),
        TOUR_SITE_DETAIL(2, "行程详情展示", "288x110");

        private final Integer value;
        private final String type;
        private final String style;

        TourCardType(Integer value, String type, String style) {
            this.value = value;
            this.type = type;
            this.style = style;
        }

        public Integer getValue() {
            return value;
        }

        public String getType() {
            return type;
        }

        public String getStyle() {
            return style;
        }
    }

    /**
     * 行程卡片分辨率大小
     */
    public enum TourCardSize {
        S_STYLE("HDPI", "S码", "drawable-hdpi"),
        X_STYLE("XHDPI", "X码", "drawable-xhdpi"),
        XX_STYLE("XXHDPI", "XX码", "drawable-xxhdpi"),
        XXX_STYLE("XXXHDPI", "XXX码", "drawable-xxxhdpi");

        private final String value;
        private final String type;
        private final String style;

        TourCardSize(String value, String type, String style) {
            this.value = value;
            this.type = type;
            this.style = style;
        }

        public String getValue() {
            return value;
        }

        public String getType() {
            return type;
        }

        public String getStyle() {
            return style;
        }
    }

    public enum TourOrderBy {
        ORDER_DEFALUT(0, "创建时间"),
        TOUR_FIRSTTIME(1, "行程出发时间"),
        SELLER_TRUST_VALUE(2, "买手信誉分"),
        SELLER_TRADE_COUNT(3, "买手成交量");

        private final Integer value;
        private final String type;

        TourOrderBy(Integer value, String type) {
            this.value = value;
            this.type = type;
        }

        public Integer getValue() {
            return value;
        }

        public String getType() {
            return type;
        }
    }

    /*************************************** 产品常量定义结束 ***********************************************/


    /*************************************** 用户常量定义开始 ***********************************************/

    /**
     * 性别
     */
    public enum Gender {
        MAN(1, "男"),
        WOMAN(2, "女"),
        NULL(-1, null);

        private Integer value;
        private String label;

        Gender(Integer value, String label) {
            this.value = value;
            this.label = label;
        }

        public static Gender valueOf(Integer value) {
            for (Gender e : values()) {
                if (e.value.equals(value)) {
                    return e;
                }
            }
            return NULL;
        }

        public static Gender labelOf(String label) {
            for (Gender e : values()) {
                if (e.label.equals(label)) {
                    return e;
                }
            }
            return NULL;
        }


        public Integer getValue() {
            return this.value;
        }

        public String getLabel() {
            return this.label;
        }

        public static Map<Integer, String> toMap() {
            Map<Integer, String> map = new HashMap<Integer, String>();
            for (Gender e : Gender.values()) {
                map.put(e.getValue(), e.getLabel());
            }
            return map;
        }
    }

    /**
     * 买家/买手操作Event
     */
    public static enum MemberEvent {
        注册,
        修改信息,
        认证,
        密码重置
    }

    /**
     * 登录类型（1，密码登录2，动态码登录,3，第三方）
     */
    public enum LoginType {
        pwd(1, "密码登录"),
        code(2, "动态码登录"),
        third(3, "第三方登录");

        private final int value;
        private final String name;

        LoginType(int value, String name) {
            this.value = value;
            this.name = name;
        }

        public int getValue() {
            return value;
        }

        public String getName() {
            return name;
        }

        public static LoginType valueOf(int value) {
            for (LoginType inst : values()) {
                if (value == inst.value)
                    return inst;
            }
            throw new IllegalArgumentException("不支持的常量：" + value);
        }

        public static Map<Integer, String> getMap() {
            Map<Integer, String> map = new HashMap<Integer, String>();
            for (LoginType inst : values()) {
                map.put(inst.value, inst.name);
            }
            return map;
        }
    }

    /**
     * 渠道(1运营推广，2线下推广，3用户邀请)
     */
    public enum ChannelType {
        yunying(1, "运营推广"),
        xianzai(2, "线下推广"),
        yaoqing(3, "用户邀请");

        private final int value;
        private final String name;

        ChannelType(int value, String name) {
            this.value = value;
            this.name = name;
        }

        public int getValue() {
            return value;
        }

        public String getName() {
            return name;
        }

        public static ChannelType valueOf(int value) {
            for (ChannelType inst : values()) {
                if (value == inst.value)
                    return inst;
            }
            throw new IllegalArgumentException("不支持的常量：" + value);
        }

        public static Map<Integer, String> getMap() {
            Map<Integer, String> map = new HashMap<Integer, String>();
            for (ChannelType inst : values()) {
                map.put(inst.value, inst.name);
            }
            return map;
        }
    }

    /*************************************** 用户常量定义结束 ***********************************************/


    /*************************************** 物流常量定义开始 ***********************************************/
    /**
     * 返回格式
     */
    public enum ShowType {
        JSON_STR("0", "json字符串格式"),
        XML("1", "xml格式"),
        HTML("2", "html格式"),
        TEXT("3", "text文本格式");

        private final String value;
        private final String name;

        ShowType(String value, String name) {
            this.value = value;
            this.name = name;
        }

        public String getValue() {
            return value;
        }

        public String getName() {
            return name;
        }

        public static Map<String, String> getMap() {
            Map<String, String> map = new HashMap<String, String>();
            for (ShowType showType : values()) {
                map.put(showType.value, showType.name);
            }
            return map;
        }
    }

    /**
     * 返回顺序
     */
    public enum ShowOrder {
        DESC("desc", "按时间由新到旧排列"),
        ASC("asc", "按时间由旧到新排列");

        private final String value;
        private final String name;

        ShowOrder(String value, String name) {
            this.value = value;
            this.name = name;
        }

        public String getValue() {
            return value;
        }

        public String getName() {
            return name;
        }

        public static Map<String, String> getMap() {
            Map<String, String> map = new HashMap<String, String>();
            for (ShowOrder showOrder : values()) {
                map.put(showOrder.value, showOrder.name);
            }
            return map;
        }
    }

    /**
     * 返回信息量数量
     */
    public enum ShowMuti {
        SINGLE("0", "只返回一行信息"),
        MUTI("1", "返回多行完整的信息");

        private final String value;
        private final String name;

        ShowMuti(String value, String name) {
            this.value = value;
            this.name = name;
        }

        public String getValue() {
            return value;
        }

        public String getName() {
            return name;
        }

        public static Map<String, String> getMap() {
            Map<String, String> map = new HashMap<String, String>();
            for (ShowMuti showMuti : values()) {
                map.put(showMuti.value, showMuti.name);
            }
            return map;
        }
    }

    /**
     * 常用的快递公司（国内）
     */
    public enum ChinaExpressCompany {
        YUANTONG("yuantong", "圆通快递"),
        SHENGTONG("shentong", "申通快递"),
        YUNDA("yunda", "韵达快递"),
        BAISHI("huitongkuaidi", "百世快递"),
        TIANTIAN("tiantian", "天天快递"),
        SHUNFENG("shunfeng", "顺丰速运"),
        EMS("ems", "EMS"),
        YOUZHENGGUONEI("youzhengguonei", "中国邮政快递"),
        GUOTONG("guotongkuaidi", "国通快递"),
        ZHAIJISONG("zhaijisong", "宅急送");

        private final String value;
        private final String name;

        ChinaExpressCompany(String value, String name) {
            this.value = value;
            this.name = name;
        }

        public String getValue() {
            return value;
        }

        public String getName() {
            return name;
        }

        public static String getName(String code) {
            for (ChinaExpressCompany obj : values()) {
                if (obj.value.equals(code)) {
                    return obj.name;
                }
            }
            throw new IllegalArgumentException("不支持的常量：" + code);
        }

        public static Map<String, String> getMap() {
            Map<String, String> map = new HashMap<String, String>();
            for (ChinaExpressCompany company : values()) {
                map.put(company.value, company.name);
            }
            return map;
        }
    }

    /**************************************** 物流常量定义结束***********************************************/


    /*************************************** 支付常量定义开始 ***********************************************/

    /**
     * 支付状态
     */
    public enum PayStatus {
        WAITTING_PAY(1, "未支付"),
        PAY_FAIL(3, "交易失败"),
        PAY_SUCCESS(4, "交易成功"),
        REFUND_SUCCESS(5, "退款成功"),
        PAY_CLOSE(6, "关闭"),
        FOR_REFUND(7, "退款受理中"),
        PAY_ONGOING(99, "进行中");

        private final Integer value;
        private final String name;

        PayStatus(Integer value, String name) {
            this.value = value;
            this.name = name;
        }

        public Integer getValue() {
            return value;
        }

        public String getName() {
            return name;
        }
    }

    /**
     * 小背包的支付方式
     */
    public enum XBBPayMethod {
        ALIPAY_APP("支付宝APP支付", 1),
        WECHATPAY_APP("微信APP支付", 2),
        QUICKPAY("快捷支付", 3);

        private String name;
        private Integer value;

        XBBPayMethod(String name, Integer value) {
            this.name = name;
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }

        public static String getName(Integer value) {
            for (XBBPayMethod method : values()) {
                if (method.value.equals(value)) {
                    return method.name;
                }
            }
            throw new IllegalArgumentException("不支持的常量：" + value);
        }
    }

    /**
     * 支付业务类型
     */
    public enum ServiceType {
        PAY_ORDER(1, "订单支付"),
        RECIEVE(2, "确认收货 "),
        REFUND(3, "确认退款"),
        WITH_DRAW(4, "申请提现");

        private final Integer value;
        private final String name;

        ServiceType(Integer value, String name) {
            this.value = value;
            this.name = name;
        }

        public Integer getValue() {
            return value;
        }

        public String getName() {
            return name;
        }
    }


    //后台对账文件类型
    public enum FileType {
        DETAIL("明细", 1L),
        SUMMARY("汇总", 2L);
        private String name;
        private Long value;

        FileType(String name, Long value) {
            this.name = name;
            this.value = value;
        }

        public String getName() {
            return name;
        }

        public Long getValue() {
            return value;
        }
    }

    /*************************************** 支付常量定义结束 ***********************************************/


    /*************************************** 身份认证常量定义 ***********************************************/
    public enum identityAuthStatus {
        SUBMIT(0, "提交认证"),
        PASS(50, "认证审核通过"),
        NOT_PASS(-50, "认证审核不通过");

        private Integer value;
        private String label;

        identityAuthStatus(Integer value, String label) {
            this.value = value;
            this.label = label;
        }

        public static identityAuthStatus valueOf(Integer value) {
            for (identityAuthStatus e : values()) {
                if (e.value.equals(value)) {
                    return e;
                }
            }
            return null;
        }

        public Integer getValue() {
            return this.value;
        }

        public String getLabel() {
            return this.label;
        }

    }

    /*************************************** 生活圈常量定义开始 ***********************************************/
    /**
     * 圈类型（1，笔记；2，视频；3，商品）
     */
    public enum CircleType {
        note(1, "笔记"),
        video(2, "视频"),
        product(3, "商品");

        private final int value;
        private final String name;

        CircleType(int value, String name) {
            this.value = value;
            this.name = name;
        }

        public int getValue() {
            return value;
        }

        public String getName() {
            return name;
        }

        public static CircleType valueOf(int value) {
            for (CircleType inst : values()) {
                if (value == inst.value)
                    return inst;
            }
            throw new IllegalArgumentException("不支持的常量：" + value);
        }

        public static Map<Integer, String> getMap() {
            Map<Integer, String> map = new HashMap<Integer, String>();
            for (CircleType inst : values()) {
                map.put(inst.value, inst.name);
            }
            return map;
        }
    }
    /*************************************** 生活圈常量定义结束 ***********************************************/


    /*************************************** 直播状态常量定义 ***********************************************/
    public enum VideoStatus {
        START(0, "初始化"),
        PLAYING(1, "直播中"),
        END(2, "已结束");
        private Integer value;
        private String label;

        VideoStatus(Integer value, String label) {
            this.value = value;
            this.label = label;
        }

        public static VideoStatus valueOf(Integer value) {
            for (VideoStatus e : values()) {
                if (e.value.equals(value)) {
                    return e;
                }
            }
            return null;
        }

        public static VideoStatus labelOf(String label) {
            for (VideoStatus e : values()) {
                if (e.label.equals(label)) {
                    return e;
                }
            }
            return null;
        }


        public Integer getValue() {
            return this.value;
        }

        public String getLabel() {
            return this.label;
        }

        public static Map<Integer, String> toMap() {
            Map<Integer, String> map = new HashMap<Integer, String>();
            for (VideoStatus e : VideoStatus.values()) {
                map.put(e.getValue(), e.getLabel());
            }
            return map;
        }
    }

    public enum ActionType {
        COUNT(2, "统计"),
        ENTER(1, "进群"),
        EXIT(0, "退群");

        private final Integer value;
        private final String name;

        ActionType(Integer value, String name) {
            this.value = value;
            this.name = name;
        }

        public Integer getValue() {
            return value;
        }

        public String getName() {
            return name;
        }
    }

    /*************************************** 直播状态常量定义结束 ***********************************************/


    /*************************************** 第七块框架常量开始 ***********************************************/

    /**
     * 热门
     */
    public enum RegionHot {
        NO(0, "不是热门"),
        YES(1, "是热门");

        private Integer value;
        private String name;

        RegionHot(Integer value, String name) {
            this.name = name;
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }

        public String getName() {
            return name;
        }
    }

    /**
     * 区域树列表展示风格
     */
    public enum ShowStyle {
        LIST_STYLE(0, "列表"),
        ZGS_STYLE(1, "洲国市"),
        ZIMU_STYLE(2, "字母"),
        SSQ_STYLE(3, "省市区县");

        private Integer value;
        private String name;

        ShowStyle(Integer value, String name) {
            this.name = name;
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }

        public String getName() {
            return name;
        }
    }

    /*************************************** 第七块框架常量结束 ***********************************************/


    /*************************************** 信鸽推送 开始***********************************************/

    /**
     * 今日提醒的类型
     */
    public enum EveryDayRadioType {
        SELLER_ACCEPT(1, "买手接收需求", "买家%s的需求已成功受理，等待买家指定中"),
        BUYER_PAY_SUCCESS(2, "买家付款成功", "买家%s已付款成功，请注意发货"),
        BUYER_REMIND_DELIVERY(3, "买家提醒发货", "买家%s提醒您发货，请您尽快处理"),
        BUYER_APPLY_REFUND(4, "买家申请售后", "买家%s申请售后，请您尽快处理");
        private final Integer value;
        private final String name;
        private final String content;

        EveryDayRadioType(Integer value, String name, String content) {
            this.value = value;
            this.name = name;
            this.content = content;
        }

        public Integer getValue() {
            return value;
        }

        public String getName() {
            return name;
        }

        public String getContent() {
            return content;
        }

        public static String getContentByValue(Integer value) {
            for (EveryDayRadioType radioEnum : EveryDayRadioType.values()) {
                if (value.equals(radioEnum.getValue())) {
                    return radioEnum.content;
                }
            }
            return null;
        }
    }

    //推送类型
    public enum PushType {
        EVERYDAY_RADIO(1, "今日提醒"),
        SYS_MSG(2, "系统消息"),
        NEW_DEMAND(3, "新需求提醒");

        private final Integer value;
        private final String name;

        public Integer getValue() {
            return value;
        }

        public String getName() {
            return name;
        }

        PushType(Integer value, String name) {
            this.value = value;
            this.name = name;
        }
    }

    /*************************************** 信鸽推送 结束 ***********************************************/

}


