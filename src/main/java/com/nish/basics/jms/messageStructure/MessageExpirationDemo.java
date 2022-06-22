package com.nish.basics.jms.messageStructure;

import javax.jms.JMSContext;
import javax.jms.JMSProducer;
import javax.jms.Message;
import javax.jms.Queue;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

public class MessageExpirationDemo {

	public static void main(String[] args) throws NamingException, InterruptedException {
		
		InitialContext context = new InitialContext();
       Queue queue= (Queue) context.lookup("queue/myQueueNishi");
       
       try (ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory();
    		 JMSContext jmscontext = cf.createContext() ) {
    	   JMSProducer producer = jmscontext.createProducer();
    	 //  producer.setTimeToLive(2000);
    	   producer.send(queue,"hi im nish");
    	   Thread.sleep(5000);
    	   Message receiveBody = jmscontext.createConsumer(queue).receive(5000);
    	   
    	   System.out.println(receiveBody);
    	   
       }
       
	}

}
