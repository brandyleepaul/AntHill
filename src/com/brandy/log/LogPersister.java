package com.brandy.log;

import java.io.FileWriter;
import java.io.IOException;

import org.apache.log4j.spi.LoggingEvent;
import org.apache.log4j.spi.ThrowableInformation;

public class LogPersister {
	
	public LogPersister(){
		
	}
	
	public void persist(LoggingEvent event){
	
		try
		{
			
		    String filename= "perlog.txt";
		    FileWriter fw = new FileWriter(filename,true);
		    String message = formatMessage(event);
		    fw.write(message + "\n");
		    fw.close();
		}
		catch(IOException ioe)
		{
		    System.err.println("IOException: " + ioe.getMessage());
		}

	}
	
	public String formatMessage(LoggingEvent event){
		
		
		ThrowableInformation throwableInfo = null;
		Throwable throwable = null;
		Throwable cause = null;
		StackTraceElement[] stackTrace = null;
		StringBuilder stringBuffer = new StringBuilder();
		
			stringBuffer.append((event.getMessage()));
			if ((throwableInfo = event.getThrowableInformation()) != null) {
				if ((throwable = throwableInfo.getThrowable()) != null) {
					cause = throwable;
					while (cause != null) {
						stringBuffer.append("");
						if (cause != throwable) {
							stringBuffer.append("Caused by: ");
						}

						stringBuffer.append(cause.toString());
						stackTrace = cause.getStackTrace();
						for (int i = 0; i < stackTrace.length; i++) {
							stringBuffer.append(" at ").append(stackTrace[i]);
						}

						cause = cause.getCause();
					}
				}
			}
			String logLevel = event.getLevel().toString();
			String logMessage = stringBuffer.toString();
			String loggerName = event.getLoggerName();
		
		
		return (logLevel + " : " + loggerName + " : " + logMessage);
	}

}
