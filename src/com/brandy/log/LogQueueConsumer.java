package com.brandy.log;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.log4j.spi.LoggingEvent;

public class LogQueueConsumer implements Runnable, MessageListener{

	//private static final String DEFAULT_URL = "vm://localhost";
	private static final String DEFAULT_URL = "tcp://localhost:61616";
	private static final String DEFAULT_QUEUE = "TEST.LOG";
    ConnectionFactory factory;
    Connection connection;
    Session session;
    MessageConsumer consumer;
    LogPersister fileWriter;
    
	
	public LogQueueConsumer() {
		try {
			System.out.println("Create Consumer");
			factory = new ActiveMQConnectionFactory(DEFAULT_URL);
			connection = factory.createConnection();
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			fileWriter = new LogPersister();

			Destination destination = session.createQueue(DEFAULT_QUEUE);
			consumer = session.createConsumer(destination);

			consumer.setMessageListener(this);
			connection.start();

		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	@Override
	public void onMessage(Message message) {
	       try {
	    	   LoggingEvent event = (LoggingEvent) (((ObjectMessage) message).getObject());
	          // System.out.println("Message received: " + event.getMessage());
	           fileWriter.persist(event);
	        } catch (Exception e) {
	            e.printStackTrace();
	        } 
	}

    public void run() {
        try {
          
            Thread.sleep(10000);
        } catch (Exception e) {
            e.printStackTrace();  
        }
    }
    
    public void shutDown(){
    	
   	 try {
   		 System.out.println("Shut down consumer");
			consumer.close();
		    session.close();
            connection.close();
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

}
