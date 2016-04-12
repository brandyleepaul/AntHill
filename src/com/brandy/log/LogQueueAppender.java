package com.brandy.log;


import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.spi.LoggingEvent;

public class LogQueueAppender extends AppenderSkeleton {
	private static final String DEFAULT_URL = "tcp://localhost:61616";
	private static final String DEFAULT_QUEUE = "TEST.LOG";

	public String url;

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUrl() {
		return this.url;
	}

	public void close() {
		// TODO Auto-generated method stub
	}

	public boolean requiresLayout() {
		// TODO Auto-generated method stub
		return true;
	}

	protected void append(LoggingEvent event) {

		String loggerName = event.getLoggerName();
		sendLogInfo(loggerName, event);

	}

	protected void sendLogInfo(String logName, LoggingEvent event) {
		String logServiceURL = getUrl().toString();
		if (logServiceURL == null || "".equals(logServiceURL)) {
			logServiceURL = DEFAULT_URL;
		}
		
		Session session = null;
		Connection connection = null;

		try {

            ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(DEFAULT_URL);
            connection = connectionFactory.createConnection();
            connection.start();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Destination destination = session.createQueue(DEFAULT_QUEUE);
            MessageProducer producer = session.createProducer(destination);
            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

			ObjectMessage message = session.createObjectMessage();
			message.setObject(event);
            producer.send(message);
			
		} catch (Exception ex) {
			 System.out.println("Exception in logging message to the queue " + ex);
			 ex.printStackTrace();
		} finally {
      
            try {
				session.close();
				connection.close();
			} catch (JMSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
           
		}
	}

}
