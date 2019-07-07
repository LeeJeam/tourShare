package com.xmyy.order.listener;

import com.xmyy.common.EnumConstants;
import com.xmyy.member.model.UcBuyer;
import com.xmyy.member.model.UcSeller;
import com.xmyy.member.service.UcBuyerService;
import com.xmyy.member.service.UcSellerService;
import org.springframework.jms.listener.SessionAwareMessageListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.TextMessage;

/**
 * 订单成交时，更新买手/背包客 的成交量
 */
@Component("addTradeCountListener")
public class AddTradeCountMessageListener implements SessionAwareMessageListener<TextMessage> {

    @Resource
    private UcSellerService sellerService;
    @Resource
    private UcBuyerService buyerService;

    @Override
    @Transactional
    public void onMessage(TextMessage textMessage, Session session) throws JMSException {
        int memberType = Integer.parseInt(textMessage.getText().split(",")[0]);
        Long orderId = Long.parseLong(textMessage.getText().split(",")[1]);

        if (memberType == EnumConstants.MemberType.seller.getValue()) {
            //更新买手成交量
            UcSeller seller = sellerService.queryById(orderId);
            if (seller != null) {
                seller.setTradeCount(seller.getTradeCount() + 1);
                sellerService.update(seller);
            }
        } else if (memberType == EnumConstants.MemberType.packer.getValue()) {
            //更新背包客成交量
            UcBuyer buyer = buyerService.queryById(orderId);
            if (buyer != null) {
                buyer.setTradeCount(buyer.getTradeCount() + 1);
                buyerService.update(buyer);
            }
        }
    }
}
