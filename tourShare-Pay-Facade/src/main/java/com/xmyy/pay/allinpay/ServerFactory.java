package com.xmyy.pay.allinpay;

import com.xmyy.pay.allinpay.constants.Server;
import com.xmyy.pay.allinpay.request.ServerHandler;

/**
 * Created by Simon on 2018/9/5.
 */
public class ServerFactory {

    public static ServerHandler memberHandler(Server.Member method){

        switch (method){
            case CREATEMEMBER:
                return new ServerHandler(Server.MEMBERSERVICE,Server.Member.CREATEMEMBER.getMethod());
            case SENDVERIFICATIONCODE:
                return new ServerHandler(Server.MEMBERSERVICE,Server.Member.SENDVERIFICATIONCODE.getMethod());
            case BINDPHONE:
                return new ServerHandler(Server.MEMBERSERVICE,Server.Member.BINDPHONE.getMethod());
            case CHANGEBINDPHONE:
                return new ServerHandler(Server.MEMBERSERVICE,Server.Member.CHANGEBINDPHONE.getMethod());
            case SETREALNAME:
                return new ServerHandler(Server.MEMBERSERVICE,Server.Member.SETREALNAME.getMethod());
            case SETCOMPANYINFO:
                return new ServerHandler(Server.MEMBERSERVICE,Server.Member.SETCOMPANYINFO.getMethod());
            case GETMEMBERINFO:
                return new ServerHandler(Server.MEMBERSERVICE,Server.Member.GETMEMBERINFO.getMethod());
            case GETBANKCARDBIN:
                return new ServerHandler(Server.MEMBERSERVICE,Server.Member.GETBANKCARDBIN.getMethod());
            case APPLYBINDBANKCARD:
                return new ServerHandler(Server.MEMBERSERVICE,Server.Member.APPLYBINDBANKCARD.getMethod());
            case BINDBANKCARD:
                return new ServerHandler(Server.MEMBERSERVICE,Server.Member.BINDBANKCARD.getMethod());
            case SETSAFECARD:
                return new ServerHandler(Server.MEMBERSERVICE,Server.Member.SETSAFECARD.getMethod());
            case QUERYBANKCARD:
                return new ServerHandler(Server.MEMBERSERVICE,Server.Member.QUERYBANKCARD.getMethod());
            case UNBINDBANKCARD:
                return new ServerHandler(Server.MEMBERSERVICE,Server.Member.UNBINDBANKCARD.getMethod());
            case LOCKMEMBER:
                return new ServerHandler(Server.MEMBERSERVICE,Server.Member.LOCKMEMBER.getMethod());
            case UNLOCKMEMBER:
                return new ServerHandler(Server.MEMBERSERVICE,Server.Member.UNLOCKMEMBER.getMethod());
            default:
                return null;
        }

    }

    public static ServerHandler orderHandler(Server.Order method){

        switch (method){
            case DEPOSITAPPLY:
                return new ServerHandler(Server.ORDERSERVICE,Server.Order.DEPOSITAPPLY.getMethod());
            case WITHDRAWAPPLY:
                return new ServerHandler(Server.ORDERSERVICE,Server.Order.WITHDRAWAPPLY.getMethod());
            case CONSUMEAPPLY:
                return new ServerHandler(Server.ORDERSERVICE,Server.Order.CONSUMEAPPLY.getMethod());
            case AGENTCOLLECTAPPLY:
                return new ServerHandler(Server.ORDERSERVICE,Server.Order.AGENTCOLLECTAPPLY.getMethod());
            case SIGNALAGENTPAY:
                return new ServerHandler(Server.ORDERSERVICE,Server.Order.SIGNALAGENTPAY.getMethod());
            case BATCHAGENTPAY:
                return new ServerHandler(Server.ORDERSERVICE,Server.Order.BATCHAGENTPAY.getMethod());
            case PAY:
                return new ServerHandler(Server.ORDERSERVICE,Server.Order.PAY.getMethod());
            case PAYVIRTUAL:
                return new ServerHandler(Server.ORDERSERVICE,Server.Order.PAYVIRTUAL.getMethod());
            case ENTRYGOODS:
                return new ServerHandler(Server.ORDERSERVICE,Server.Order.ENTRYGOODS.getMethod());
            case QUERYMODIFYGOODS:
                return new ServerHandler(Server.ORDERSERVICE,Server.Order.QUERYMODIFYGOODS.getMethod());
            case FREEZEMONEY:
                return new ServerHandler(Server.ORDERSERVICE,Server.Order.FREEZEMONEY.getMethod());
            case UNFREEZEMONEY:
                return new ServerHandler(Server.ORDERSERVICE,Server.Order.UNFREEZEMONEY.getMethod());
            case REFUND:
                return new ServerHandler(Server.ORDERSERVICE,Server.Order.REFUND.getMethod());
            case FAILUREBIDREFUND:
                return new ServerHandler(Server.ORDERSERVICE,Server.Order.FAILUREBIDREFUND.getMethod());
            case APPLICATIONTRANSFER:
                return new ServerHandler(Server.ORDERSERVICE,Server.Order.APPLICATIONTRANSFER.getMethod());
            case QUERYBALANCE:
                return new ServerHandler(Server.ORDERSERVICE,Server.Order.QUERYBALANCE.getMethod());
            case GETORDERDETAIL:
                return new ServerHandler(Server.ORDERSERVICE,Server.Order.GETORDERDETAIL.getMethod());
            case QUERYINEXPDETAIL:
                return new ServerHandler(Server.ORDERSERVICE,Server.Order.QUERYINEXPDETAIL.getMethod());
            default:
                return null;
        }

    }

    public static ServerHandler merchantHandler(Server.Merchant method){

        switch (method){
            case QUERYRESERVEFUNDBALANCE:
                return new ServerHandler(Server.MERCHANTSERVICE,Server.Merchant.QUERYRESERVEFUNDBALANCE.getMethod());
            case GETCHECKACCOUNTFILE:
                return new ServerHandler(Server.MERCHANTSERVICE,Server.Merchant.GETCHECKACCOUNTFILE.getMethod());
            case PLATFORMPULLOUT:
                return new ServerHandler(Server.MERCHANTSERVICE,Server.Merchant.PLATFORMPULLOUT.getMethod());
            default:
                return null;
        }

    }

}
