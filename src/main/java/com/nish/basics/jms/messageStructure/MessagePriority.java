package com.nish.basics.jms.messageStructure;

import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSProducer;
import javax.jms.Queue;
import javax.naming.InitialContext;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

public class MessagePriority {
  
	 public static void main(String[] args) throws Exception
	 {
		 InitialContext context = new InitialContext();
	       Queue queue= (Queue) context.lookup("queue/myQueueNishi");
	       
	       try (ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory();
	    		   JMSContext jmscontext = cf.createContext()){
	    	   JMSProducer producer = jmscontext.createProducer();
	           
	    	   String[] messages =new String[3];
	    	   
	    	   messages[0] = "message one";
	    	   messages[1] = "message two";
	    	   messages[2] = "message three";
	    	   
	    	   producer.setPriority(3);
	    	   producer.send(queue, messages[0]);
	    	   
	    	   producer.setPriority(1);
	    	   producer.send(queue, messages[1]);
	    	   
	    	   producer.setPriority(9);
	    	   producer.send(queue, messages[2]);
	    	   //message 3 has highest priority of 9
	    	   
	    	   JMSConsumer createConsumer = jmscontext.createConsumer(queue);
	      
	           for (int i =0; i<3; i++)
	           {
	        	   System.out.println(createConsumer.receiveBody(String.class));
	        	   
	           }
	       
	       }
	 }
}
