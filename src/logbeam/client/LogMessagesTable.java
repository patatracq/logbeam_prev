package logbeam.client;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import logbeam.business.Agent;
import logbeam.business.LogMessage;

import org.springframework.context.ApplicationListener;

import springclient.table.DomainSpecificTableModel;
import crudbeam.event.BusinessObjectCreated;
import crudbeam.event.BusinessObjectEvent;

public class LogMessagesTable extends DomainSpecificTableModel implements ApplicationListener< BusinessObjectEvent > {

	public static int AGENT_COLUMN_INDEX = 0;
	public static int LOGFILE_COLUMN_INDEX = 1;
	public static int LOGMESSAGE_COLUMN_INDEX = 2;
	
	private boolean paused = false;

	private Vector< Agent > activeAgents = new Vector< Agent >();
	
	private Vector< LogMessage > rows = new Vector< LogMessage >();
	private Vector< LogMessage > visibleRows = new Vector< LogMessage >();
	
	public void clear() {
		
		rows.clear();
		visibleRows.clear();
		super.tableRowsChanged();
	}
	
	public void show( List< LogMessage > logMessages ) {
		
		rows.clear();
		rows.addAll( logMessages );
		updateView();
	}
	
	public void pause() {
		
		if ( !paused ) {
			
			paused = true;
		}
	}
	
	public void resume() {
		
		if ( paused ) {
			
			paused = false;
			updateView();
		}
	}
	
	public void setActiveAgents( Vector< Agent > activeAgents ) {
		
		this.activeAgents = activeAgents;
		this.updateView();
	}
	
	public LogMessage getRow( int rowIndex ) {

		if ( rowIndex >= 0 && rowIndex < visibleRows.size() ) {
			return visibleRows.get( rowIndex );
		} else {
			return null;
		}
	}

	@Override
	public LogMessage getValueAt( int rowIndex ) {

		if ( rowIndex < visibleRows.size() ) {
			return (LogMessage) visibleRows.toArray()[ rowIndex ];
		} else {
			return null;
		}
	}

	@Override
	public int getRowCount() {

		return visibleRows.size();
	}

	@Override
	public Object getValueAt( int rowIndex, int columnIndex ) {

		if ( rowIndex < visibleRows.size() ) {
			LogMessage logMessage = visibleRows.elementAt( rowIndex );
			if ( columnIndex == AGENT_COLUMN_INDEX ) {
				return logMessage.getLogFile().getAgent();
			} else if ( columnIndex == LOGFILE_COLUMN_INDEX ) {
				return logMessage.getLogFile();
			} else if ( columnIndex == LOGMESSAGE_COLUMN_INDEX ) {
				return logMessage;
			}
		}
		return null;
	}

	@Override
	public void onApplicationEvent( BusinessObjectEvent event ) {

		if ( event.getBusinessObject() instanceof LogMessage && event.getType() instanceof BusinessObjectCreated ) {
			
			LogMessage logMessage = (LogMessage) event.getBusinessObject();
			rows.insertElementAt( logMessage, 0 );
			
			if ( !paused && ( activeAgents.isEmpty() || activeAgents.contains( logMessage.getLogFile().getAgent() ) ) ) {
				visibleRows.insertElementAt( logMessage, 0 );
				super.tableRowsChanged();
			}
		}
	}
	
	private void updateView() {

		visibleRows.clear();

		if ( activeAgents.isEmpty() ) {
			visibleRows.addAll( rows );
		} else {
			Iterator< LogMessage > iterator = rows.iterator();
			while ( iterator.hasNext() ) {
				LogMessage logMessage = iterator.next();
				
				if ( activeAgents.contains( logMessage.getLogFile().getAgent() ) ) {
					visibleRows.add( logMessage );
				}
			}
		}
		
		super.tableRowsChanged();
	}
}
