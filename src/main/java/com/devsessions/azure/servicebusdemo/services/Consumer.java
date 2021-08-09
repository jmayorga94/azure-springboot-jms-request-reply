package com.devsessions.azure.servicebusdemo.services;

import com.devsessions.azure.servicebusdemo.models.Order;
import lombok.extern.slf4j.Slf4j;
import org.apache.qpid.jms.JmsQueue;
import org.apache.qpid.jms.message.JmsObjectMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.jms.listener.SessionAwareMessageListener;
import org.springframework.stereotype.Component;

import javax.jms.*;

@Component
@Slf4j
public class Consumer {

    private static final String QUEUE_NAME = "sbq-request";
    @Autowired JmsTemplate jmsTemplate;


    @JmsListener(destination = QUEUE_NAME)
   public void Reply(Message message) throws JMSException {

        Order order = (Order) ((JmsObjectMessage) message).getObject();
        String replyTo = message.getJMSReplyTo().toString();
        order.setIsProccesed(true);

        jmsTemplate.convertAndSend(message.getJMSReplyTo(),order,m->{
            m.setJMSCorrelationID(message.getJMSCorrelationID().toString());
            return m;
        });


    }
}
