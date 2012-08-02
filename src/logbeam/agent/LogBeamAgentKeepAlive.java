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

import org.springframework.beans.factory.annotation.Required;

import logbeam.business.Agent;
import logbeam.business.agent.AgentContainer;

/**
 * Calls a keep-alive service with a configurable
 * interval.
 * 
 * @author Jonas Stråle
 */
public class LogBeamAgentKeepAlive implements Runnable {

	/**
	 * Agent POJO to send.
	 */
	private Agent agent;
	
	/**
	 * Time to sleep between keep-alive calls.
	 */
	private Short intervalSeconds;
	
	/**
	 * Service to call.
	 */
	private AgentContainer agentContainer;

	/**
	 * Run the keep-alive loop (which is infinite).
	 */
	@Override
	public void run() {
		
		/*
		 * Loop forever.
		 */
		while ( true ) {
			
			/*
			 * Call keep-alive service.
			 */
			agentContainer.keepAlive( agent );
			
			/*
			 * Sleep for a while.
			 */
			try {
				Thread.sleep( intervalSeconds * 1000L );
			} catch ( InterruptedException ie ) {
				throw new RuntimeException( ie );
			}
		}
	}
	
	public Agent getAgent() {
		
		return agent;
	}

	public void setAgent( Agent agent ) {
		
		this.agent = agent;
	}

	public Short getIntervalSeconds() {
		
		return intervalSeconds;
	}

	@Required
	public void setIntervalSeconds( Short intervalSeconds ) {
		
		this.intervalSeconds = intervalSeconds;
	}

	
	@Required
	public void setAgentContainer( AgentContainer agentContainer ) {
		
		this.agentContainer = agentContainer;
	}
}
