package com.nish.basics.jms;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class FirstQueue {

	public static void main(String[] args)
	{
		//access jndi initial context
		
		InitialContext initialContext = null;
		
		try {
			initialContext = new InitialContext();
			ConnectionFactory cf =(ConnectionFactory) initialContext.lookup("ConnectionFactory");
		    Connection connection = cf.createConnection();
		    
		    Session session = connection.createSession();
		    
		   Queue queue= (Queue) initialContext.lookup("queue/myQueueNish");
		    
		   MessageProducer producer= session.createProducer(queue);
		    
		   TextMessage message = session.createTextMessage("O ho ho ho");
		   
		   producer.send(message);
		   
		   MessageConsumer consumer=session.createConsumer(queue);
		   
		   connection.start();
		   
		   TextMessage msgReceived = (TextMessage) consumer.receive(5000);
		   
		   System.out.println(msgReceived.getText());
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
