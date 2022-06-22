package com.nish.basics.jms.messageStructure;

import javax.jms.BytesMessage;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.StreamMessage;
import javax.jms.TextMessage;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

public class MessageTypesDemo {

	public static void main(String[] args) throws NamingException, InterruptedException, JMSException {
		
		InitialContext context = new InitialContext();
       Queue queue= (Queue) context.lookup("queue/myQueueNishi");
     //  Queue expiryqueue= (Queue) context.lookup("queue/expiryQueue");
       try (ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory();
    		 JMSContext jmscontext = cf.createContext() ) {
    	   JMSProducer producer = jmscontext.createProducer();
    	   TextMessage createTextMessage = jmscontext.createTextMessage("hi im nish");
    	    BytesMessage createBytesMessage = jmscontext.createBytesMessage();
    	    createBytesMessage.writeUTF("john");
    	    createBytesMessage.writeLong(123L);
    	   
    	    StreamMessage createStreamMessage = jmscontext.createStreamMessage();
    	    createStreamMessage.writeBoolean(true);
    	    createStreamMessage.writeFloat(2.5f);
    	    
    	    MapMessage createMapMessage = jmscontext.createMapMessage();
    	    createMapMessage.setBoolean("isValid", false);
    	    createMapMessage.setString("aloha","hi transalted");
    	    
    	    ObjectMessage createObjectMessage = jmscontext.createObjectMessage();
    	    Patient object = new Patient();
    	    object.setId(123);
    	    object.setName("jac");
			createObjectMessage.setObject(object);
    	    
    	    // producer.setDeliveryDelay(3000);
    	   //producer.setTimeToLive(2000);
    	   producer.send(queue,createObjectMessage);
    	  // Thread.sleep(5000);
    	 //  BytesMessage receiveBody = (BytesMessage) jmscontext.createConsumer(queue).receive(5000);
    	   
    	  // System.out.println(receiveBody.readUTF());
    	  // System.out.println(receiveBody.readLong());
    	  
    	   
    	 //  System.out.println(jmscontext.createConsumer(expiryqueue).receiveBody(String.class));
    	   
    	  // MapMessage receiveBody = (MapMessage) jmscontext.createConsumer(queue).receive(5000);
    	 //  System.out.println(receiveBody.getBoolean("isValid"));
    	 //  System.out.println(receiveBody.getString("aloha"));
    	   
    	   ObjectMessage receiveBody = (ObjectMessage) jmscontext.createConsumer(queue).receive(5000);
    	   Patient pat = (Patient)receiveBody.getObject();
    	   System.out.println(pat);
       }
       
	}

}
