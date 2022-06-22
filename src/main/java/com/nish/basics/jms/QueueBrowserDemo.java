package com.nish.basics.jms;

import java.util.Enumeration;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.QueueBrowser;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class QueueBrowserDemo {

	public static void main(String[] args) {
		// access jndi initial context

		InitialContext initialContext = null;
		Connection connection = null;

		try {
			initialContext = new InitialContext();
			ConnectionFactory cf = (ConnectionFactory) initialContext.lookup("ConnectionFactory");
			connection = cf.createConnection();

			Session session = connection.createSession();

			Queue queue = (Queue) initialContext.lookup("queue/myQueueNishi");

			MessageProducer producer = session.createProducer(queue);

			TextMessage message1 = session.createTextMessage("message 1");
			TextMessage message2 = session.createTextMessage("message 2");

			producer.send(message1);
			producer.send(message2);
			
			QueueBrowser browser = session.createBrowser(queue);
			
			Enumeration messagesEnum = browser.getEnumeration();
			
			while(messagesEnum.hasMoreElements())
			{
				TextMessage textMsg = (TextMessage) messagesEnum.nextElement();
			    System.out.println("printing in loop" +textMsg.getText());
			}
			System.out.println("changed");
			
			MessageConsumer consumer = session.createConsumer(queue);

			connection.start();

			TextMessage msgReceived = (TextMessage) consumer.receive(5000);

			System.out.println(msgReceived.getText());
			
			 msgReceived = (TextMessage) consumer.receive(5000);

			System.out.println(msgReceived.getText());
			
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (initialContext != null) {
				try {
					initialContext.close();
				} catch (NamingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			if (connection != null) {
				try {
					connection.close();
				} catch (JMSException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}

	}
}
