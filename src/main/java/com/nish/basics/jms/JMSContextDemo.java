package com.nish.basics.jms;

import javax.jms.JMSContext;
import javax.jms.Queue;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

public class JMSContextDemo {

	public static void main(String[] args) throws NamingException {
		
		InitialContext context = new InitialContext();
       Queue queue= (Queue) context.lookup("queue/myQueueNishi");
       
       try (ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory();
    		 JMSContext jmscontext = cf.createContext() ) {
    	   jmscontext.createProducer().send(queue,"hi im nish");
    	   String receiveBody = jmscontext.createConsumer(queue).receiveBody(String.class);
    	   
    	   System.out.println(receiveBody);
    	   
       }
       
	}

}
