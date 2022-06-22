package com.nish.basics.jms.messageStructure;

import java.util.HashMap;
import java.util.Map;

import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.TemporaryQueue;
import javax.jms.TextMessage;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

public class RequestReplyDemo {

	public static void main(String[] args) throws NamingException, JMSException {

		InitialContext context = new InitialContext();
		Queue queue = (Queue) context.lookup("queue/requestQueue");
		// Queue replyQueue= (Queue) context.lookup("queue/replyQueue");

		try (ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory();
				JMSContext jmscontext = cf.createContext()) {
			JMSProducer producer = jmscontext.createProducer();
			TemporaryQueue replyQueue = jmscontext.createTemporaryQueue();
			TextMessage message = jmscontext.createTextMessage("hi im nish");
			message.setJMSReplyTo(replyQueue);
			producer.send(queue, message);
			System.out.println(message.getJMSMessageID());

			Map<String, TextMessage> requestMessages = new HashMap<>();
			requestMessages.put(message.getJMSMessageID(), message);

			JMSConsumer consumer = jmscontext.createConsumer(queue);
			TextMessage receiveBody = (TextMessage) consumer.receive();
			System.out.println(receiveBody.getText());

			JMSProducer replyProducer = jmscontext.createProducer();
			TextMessage replyMessage = jmscontext.createTextMessage("you are awesome");
			replyMessage.setJMSCorrelationID(receiveBody.getJMSMessageID());
			replyProducer.send(receiveBody.getJMSReplyTo(), replyMessage);

			JMSConsumer replyConsumer = jmscontext.createConsumer(receiveBody.getJMSReplyTo());
			// System.out.println(replyConsumer.receiveBody(String.class));
			TextMessage replyReceived = (TextMessage) replyConsumer.receive();
			System.out.println(replyReceived.getJMSCorrelationID());
			System.out.println(requestMessages.get(replyReceived.getJMSCorrelationID()).getText());
		}

	}

}
