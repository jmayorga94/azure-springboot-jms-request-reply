package com.devsessions.azure.servicebusdemo.services;

import com.devsessions.azure.servicebusdemo.models.Order;
import com.microsoft.azure.servicebus.QueueClient;
import com.microsoft.azure.servicebus.ReceiveMode;
import org.apache.qpid.jms.JmsQueue;
import org.apache.qpid.jms.message.JmsObjectMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.jms.core.MessagePostProcessor;
import org.springframework.jms.support.converter.SimpleMessageConverter;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.jms.*;
import java.util.UUID;

@RestController
public class Producer {

    @Autowired
    JmsTemplate jmsTemplate;

    @Autowired
    JmsMessagingTemplate jmsMessagingTemplate;
    private final SimpleMessageConverter converter = new SimpleMessageConverter();
    @PostMapping("/order")
    public ResponseEntity sendAndReceive(@RequestBody  Order order) throws JMSException {

        try{
                jmsTemplate.convertAndSend("sbq-request",order,m->{
                    m.setJMSReplyTo(new JmsQueue("sbq-reply"));
                    m.setJMSMessageID("1234");
                    m.setJMSCorrelationID("1234");
                    return m;
                });

                Message received = jmsTemplate.receiveSelected("sbq-reply","JMSCorrelationID=1234");
                Order orderReceived = (Order) ((JmsObjectMessage) received).getObject();

                if(orderReceived!=null){

                    return new ResponseEntity(orderReceived, HttpStatus.OK);
                }
                else
                {
                    return new ResponseEntity("Error al obtener orden",HttpStatus.INTERNAL_SERVER_ERROR );
                }

        }catch (Exception ex)
        {
            return new ResponseEntity(ex.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR );
        }



    }
}