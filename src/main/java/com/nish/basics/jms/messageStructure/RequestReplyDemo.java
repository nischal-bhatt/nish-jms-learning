package com.nish.basics.jms.messageStructure;

import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.Queue;
import javax.jms.TextMessage;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

public class RequestReplyDemo {

	public static void main(String[] args) throws NamingException, JMSException {
		
		InitialContext context = new InitialContext();
       Queue queue= (Queue) context.lookup("queue/requestQueue");
       Queue replyQueue= (Queue) context.lookup("queue/replyQueue");
       
       try (ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory();
    		 JMSContext jmscontext = cf.createContext() ) {
    	   JMSProducer producer = jmscontext.createProducer();
    	   TextMessage message = jmscontext.createTextMessage("hi im nish");
    	   message.setJMSReplyTo(replyQueue);
    	   producer.send(queue,message);
    	   
    	   JMSConsumer consumer = jmscontext.createConsumer(queue);
    	   TextMessage receiveBody = (TextMessage)consumer.receive();
    	   
    	   System.out.println(receiveBody.getText());
    	   
    	   
    	   JMSProducer replyProducer= jmscontext.createProducer();
    	   replyProducer.send(receiveBody.getJMSReplyTo(),"you are awesome");
    	   
    	   JMSConsumer replyConsumer = jmscontext.createConsumer(receiveBody.getJMSReplyTo());
    	   
    	   
    	   System.out.println(replyConsumer.receiveBody(String.class));
       }
       
	}

}
