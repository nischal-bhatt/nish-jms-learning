package com.nish.basics.jms;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.naming.InitialContext;

public class FirstTopic {

	public static void main(String[] args) throws Exception {
		
		
		InitialContext initialContext = new InitialContext();
		
		Topic topic = (Topic)initialContext.lookup("topic/myTopic");
        ConnectionFactory cf = (ConnectionFactory) initialContext.lookup("ConnectionFactory");
	    Connection connection = cf.createConnection();
	    
	    Session createSession = connection.createSession();
	    MessageProducer producer = createSession.createProducer(topic);
	    
	    MessageConsumer consumer1 = createSession.createConsumer(topic);
	    MessageConsumer consumer2 = createSession.createConsumer(topic);	    
	
	    TextMessage message = createSession.createTextMessage("i am the creatir of my destiny with wrong spelling");
	
	    
	    
	    producer.send(message);
	    
	    connection.start();
	    
	    TextMessage msg1 = (TextMessage) consumer1.receive(5000);
	    System.out.println(msg1.getText());
	    
	    
	    TextMessage msg2 = (TextMessage) consumer2.receive(5000);
	    System.out.println(msg2.getText());
	    
	    connection.close();
	    initialContext.close();
	}

}
