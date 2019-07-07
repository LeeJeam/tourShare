package com.xmyy.pay.allinpay.constants;

/**
 * 云商通数据字典枚举
 *
 * @author AnCheng
 */
public abstract class AllinPayEnum {

    //6.3会员类型
    public enum MemberType{
        COMPANY(2L),
        PERSON(3L);
        private Long value;
        MemberType(Long value){
            this.value=value;
        }
        public Long getValue() {
            return value;
        }
    }

    //6.4证件类型
    public enum IdentityType{
        IDENTITY(1L),
        PASSPORT(2L);
        private Long value;
        IdentityType(Long value){
            this.value=value;
        }
        public Long getValue() {
            return value;
        }
    }

    //6.9访问终端类型
    public enum Source{
        MOBILE(1L),
        PC(2L);
        private Long value;
        Source(Long value){
            this.value=value;
        }
        public Long getValue() {
            return value;
        }
    }

    //6.10验证码类型
    public enum VerfificationCodeType{
        UNBINDPHONE(6L),
        BINDPHONE(9L);
        private Long value;
        VerfificationCodeType(Long value){
            this.value=value;
        }
        public Long getValue() {
            return value;
        }
    }

    //6.15业务码
    public enum TradeCode{
        AGENT_COLLECT("代收消费金", "3001"),
        AGENT_PAY("代付购买金", "4001"),
        AGENT_COLLECT_FEE("代收（佣金/返利）金", "3002"),
        AGENT_PAY_FEE("代付（佣金/返利）金", "4002");
        private String name;
        private String value;
        TradeCode(String name, String value){
            this.name=name;
            this.value=value;
        }
        public String getName() {
            return name;
        }
        public String getValue() {
            return value;
        }
    }

    //6.16行业代码和行业名称
    public enum IndustryCode{
        OTHER("其他", "1910");
        private String name;
        private String code;
        IndustryCode(String name, String code){
            this.name=name;
            this.code=code;
        }
        public String getName() {
            return name;
        }
        public String getCode() {
            return code;
        }
    }

    //6.21帐户集编号
    public enum AccountSetNo{
        BALANCE("标准余额账户集", "100001"),
        BOND("标准保证金账户集", "100002"),
        RESERVE("准备金额度账户集", "100003"),
        INTERMEDIATE_A("中间账户集A", "100004"),
        INTERMEDIATE_B("中间账户集B", "100005"),
        MARKETING("标准营销账户集", "2000000"),
        CUSTOM_A("自定义帐户集A", "3000001"),
        XBB("小背包托管账户集", "12985739202038");
        private String name;
        private String value;
        AccountSetNo(String name, String value){
            this.name=name;
            this.value=value;
        }
        public String getName() {
            return name;
        }
        public String getValue() {
            return value;
        }
    }

}
