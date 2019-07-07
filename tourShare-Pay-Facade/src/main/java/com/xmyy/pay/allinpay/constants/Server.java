package com.xmyy.pay.allinpay.constants;

/**
 * Created by Simon on 2018/9/5.
 */


public abstract class Server{

    public static final String MEMBERSERVICE="MemberService";
    public static final String ORDERSERVICE="OrderService";
    public static final String MERCHANTSERVICE="MerchantService";

    public enum Member{
        CREATEMEMBER("createMember"),
        SENDVERIFICATIONCODE("sendVerificationCode"),
        BINDPHONE("bindPhone"),
        CHANGEBINDPHONE("changeBindPhone"),
        SETREALNAME("setRealName"),
        SETCOMPANYINFO("setCompanyInfo"),
        GETMEMBERINFO("getMemberInfo"),
        GETBANKCARDBIN("getBankCardBin"),
        APPLYBINDBANKCARD("applyBindBankCard"),
        BINDBANKCARD("bindBankCard"),
        SETSAFECARD("setSafeCard"),
        QUERYBANKCARD("queryBankCard"),
        UNBINDBANKCARD("unbindBankCard"),
        LOCKMEMBER("lockMember"),
        UNLOCKMEMBER("unlockMember");

        private String method;

        Member(String method){
            this.method=method;
        }

        public String getMethod() {
            return method;
        }

        public void setMethod(String method) {
            this.method = method;
        }
    }

    public enum Order{
        DEPOSITAPPLY("depositApply"),
        WITHDRAWAPPLY("withdrawApply"),
        CONSUMEAPPLY("consumeApply"),
        AGENTCOLLECTAPPLY("agentCollectApply"),
        SIGNALAGENTPAY("signalAgentPay"),
        BATCHAGENTPAY("batchAgentPay"),
        PAY("pay"),
        PAYVIRTUAL("payVirtual"),
        ENTRYGOODS("entryGoods"),
        QUERYMODIFYGOODS("queryModifyGoods"),
        FREEZEMONEY("freezeMoney"),
        UNFREEZEMONEY("unfreezeMoney"),
        REFUND("refund"),
        FAILUREBIDREFUND("failureBidRefund"),
        APPLICATIONTRANSFER("applicationTransfer"),
        QUERYBALANCE("queryBalance"),
        GETORDERDETAIL("getOrderDetail"),
        QUERYINEXPDETAIL("queryInExpDetail");

        private String method;

        Order(String method){
            this.method=method;
        }

        public String getMethod() {
            return method;
        }

        public void setMethod(String method) {
            this.method = method;
        }
    }

    public enum Merchant{
        QUERYRESERVEFUNDBALANCE("queryReserveFundBalance"),
        GETCHECKACCOUNTFILE("getCheckAccountFile"),
        PLATFORMPULLOUT("platformPullout");

        private String method;

        Merchant(String method){
            this.method=method;
        }

        public String getMethod() {
            return method;
        }

        public void setMethod(String method) {
            this.method = method;
        }
    }

}



