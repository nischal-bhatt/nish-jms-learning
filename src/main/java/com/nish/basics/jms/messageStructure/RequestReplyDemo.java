package com.nish.basics.jms.messageStructure;

import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSProducer;
import javax.jms.Queue;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

public class RequestReplyDemo {

	public static void main(String[] args) throws NamingException {
		
		InitialContext context = new InitialContext();
       Queue queue= (Queue) context.lookup("queue/requestQueue");
       Queue replyQueue= (Queue) context.lookup("queue/replyQueue");
       
       try (ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory();
    		 JMSContext jmscontext = cf.createContext() ) {
    	   JMSProducer producer = jmscontext.createProducer();
    	   producer.send(queue,"hi im nish");
    	   
    	   JMSConsumer consumer = jmscontext.createConsumer(queue);
    	   String receiveBody = consumer.receiveBody(String.class);
    	   
    	   System.out.println(receiveBody);
    	   
    	   
    	   JMSProducer replyProducer= jmscontext.createProducer();
    	   replyProducer.send(replyQueue,"you are awesome");
    	   
    	   JMSConsumer replyConsumer = jmscontext.createConsumer(replyQueue);
    	   
    	   
    	   System.out.println(replyConsumer.receiveBody(String.class));
       }
       
	}

}
