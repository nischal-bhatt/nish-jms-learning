package com.nish.basics.jms.messageStructure;

import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.TextMessage;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

public class MessagePropertiesDemo {

	public static void main(String[] args) throws NamingException, InterruptedException, JMSException {
		
		InitialContext context = new InitialContext();
       Queue queue= (Queue) context.lookup("queue/myQueueNishi");
     //  Queue expiryqueue= (Queue) context.lookup("queue/expiryQueue");
       try (ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory();
    		 JMSContext jmscontext = cf.createContext() ) {
    	   JMSProducer producer = jmscontext.createProducer();
    	   TextMessage createTextMessage = jmscontext.createTextMessage("hi im nish");
    	   createTextMessage.setBooleanProperty("LoggedIn",true);
    	   createTextMessage.setStringProperty("userToken", "abc123");
    	   
    	   
    	   
    	   // producer.setDeliveryDelay(3000);
    	   //producer.setTimeToLive(2000);
    	   producer.send(queue,createTextMessage);
    	  // Thread.sleep(5000);
    	   Message receiveBody = jmscontext.createConsumer(queue).receive(5000);
    	   
    	   System.out.println(receiveBody);
    	   System.out.println(receiveBody.getBooleanProperty("LoggedIn"));
    	   System.out.println(receiveBody.getStringProperty("userToken"));
    	   
    	 //  System.out.println(jmscontext.createConsumer(expiryqueue).receiveBody(String.class));
    	   
    	   
       }
       
	}

}
