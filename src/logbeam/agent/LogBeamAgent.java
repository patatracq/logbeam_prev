/*
 * This file is part of log-beam.
 * 
 * log-beam is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * log-beam is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with log-beam.  If not, see <http://www.gnu.org/licenses/>.
 */

package logbeam.agent;

import java.util.Iterator;

import logbeam.business.Agent;
import logbeam.business.LogFile;
import logbeam.business.agent.AgentContainer;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import util.tail.Tailer;

/**
 * Main class for the agent sub-system.
 * 
 * @author Jonas Stråle
 */
public class LogBeamAgent {

	/**
	 * Spring context.
	 */
	private static ClassPathXmlApplicationContext context;
	
	/**
	 * Agent name. Application domain user or host name, for example.
	 */
	private String agentName;
	
	/**
	 * It would be very expensive to check log files for new
	 * content all the time. This is the number of seconds
	 * that the log listeners will sleep between each check.
	 */
	private Long logCheckInterval;
	
	/**
	 * Runs in a separate thread to regularly report to the
	 * server.
	 */
	private LogBeamAgentKeepAlive keepAlive;
	
	/**
	 * Service object.
	 */
	private AgentContainer agentContainer;

	/**
	 * Agent main metod.
	 * 
	 * @param [spring-config ...]
	 */
	public static void main( String[] args ) {

		/*
		 * Create and configure Spring context.
		 */
		context = new ClassPathXmlApplicationContext( args );
		
		/*
		 * Load the agent and start it.
		 */
		LogBeamAgent agent = context.getBean( LogBeamAgent.class );
		agent.run();
	}

	/**
	 * Starts the agent.
	 */
	public void run() {
		
		Logger logger = Logger.getLogger( LogBeamAgent.class );
		
		/*
		 * Fetch the agent pojo from the server.
		 */
		Agent agent = agentContainer.getByName( agentName );
		
		/*
		 * Process the agent log files.
		 */
		Iterator< LogFile > iterator = agent.getLogFiles().iterator();
		while ( iterator.hasNext() ) {
			
			/*
			 * Next log file.
			 */
			LogFile logFile = iterator.next();
			
			/*
			 * Create log listener.
			 */
			LogListener listener = context.getBean( LogListener.class );
			listener.setLogFile( logFile );
			
			String logListenerName = "LogListener-" + agent.getName() + "-" + logFile.getFilename();
			/*
			 * Create and start a tailer for the log file.
			 */
			Tailer tailer = new Tailer( logFile.getFilename(), listener, logCheckInterval * 1000L );
			Thread listenerThread = new Thread( tailer, logListenerName );
			listenerThread.setDaemon( true );
			listenerThread.start();
			
			logger.info( "Log listener started: " + logListenerName );
		}

		/*
		 * Agent status is OK. We're up and running.
		 */
		agent.setStatus( true );
		
		/*
		 * Initialize and start the "keep-aliver" in the main thread.
		 */
		keepAlive.setAgent( agent );
		keepAlive.run();
	}
	
	public String getAgentName() {

		return agentName;
	}
	
	@Required
	public void setAgentName( String agentName ) {

		this.agentName = agentName;
	}
	
	public Long getLogCheckInterval() {

		return logCheckInterval;
	}
	
	@Required
	public void setLogCheckInterval( Long logCheckInterval ) {

		this.logCheckInterval = logCheckInterval;
	}
	
	public LogBeamAgentKeepAlive getKeepAlive() {

		return keepAlive;
	}
	
	@Required
	public void setKeepAlive( LogBeamAgentKeepAlive keepAlive ) {
	
		this.keepAlive = keepAlive;
	}

	@Required
	public void setAgentContainer( AgentContainer agentContainer ) {
	
		this.agentContainer = agentContainer;
	}
}
