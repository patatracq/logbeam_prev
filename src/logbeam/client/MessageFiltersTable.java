package logbeam.client;

import java.util.Comparator;
import java.util.SortedSet;
import java.util.TreeSet;

import logbeam.business.LogMessageFilter;
import logbeam.business.logmessagefilter.LogMessageFilterContainer;

import org.springframework.context.ApplicationListener;

import springclient.Loadable;
import springclient.table.DomainSpecificTableModel;
import crudbeam.event.BusinessObjectCreated;
import crudbeam.event.BusinessObjectDeleted;
import crudbeam.event.BusinessObjectEvent;

public class MessageFiltersTable extends DomainSpecificTableModel implements Loadable, ApplicationListener< BusinessObjectEvent > {

	public static final int FILTER_COLUMN_INDEX = 0;
	public static final int MESSAGE_COLUMN_INDEX = 1;
	
	private SortedSet< LogMessageFilter > rows = new TreeSet< LogMessageFilter >( new FilterComparator() );
	private LogMessageFilterContainer logMessageFilterSo;
	
	@Override
	public int getRowCount() {

		return rows.size();
	}

	public LogMessageFilter getValueAt( int rowIndex ) {

		if ( rowIndex < rows.size() ) {
			return (LogMessageFilter) rows.toArray()[ rowIndex ];
		} else {
			return null;
		}
	}
	
	@Override
	public Object getValueAt( int rowIndex, int columnIndex ) {

		if ( rowIndex < rows.size() ) {
			if ( columnIndex == FILTER_COLUMN_INDEX ) {
				return ( (LogMessageFilter) rows.toArray()[ rowIndex ] ).getRegex();
			} else if ( columnIndex == MESSAGE_COLUMN_INDEX ) {
				return ( (LogMessageFilter) rows.toArray()[ rowIndex ] ).getOriginalMessage();
			}
		}
		return null;
	}
	
	public void add( LogMessageFilter filter ) {
		
		rows.add( filter );
		super.tableRowsChanged();
	}
	
	public void delete( LogMessageFilter filter ) {
		
		rows.remove( filter );
		super.tableRowsChanged();
	}
	
	public void update( LogMessageFilter filter ) {
		
		if ( rows.contains( filter ) ) {
			rows.remove( filter );
		}
		
		add( filter );
	}

	@Override
	public void load() {
		
		rows.addAll( logMessageFilterSo.getAll() );
		super.tableRowsChanged();
	}
	
	@Override
	public void onApplicationEvent( BusinessObjectEvent event ) {
		
		if ( event.getBusinessObject() instanceof LogMessageFilter ) {
			if ( event.getType() instanceof BusinessObjectCreated ) {
				add( (LogMessageFilter) event.getBusinessObject() );
			} else if ( event.getType() instanceof BusinessObjectDeleted ) {
				delete( (LogMessageFilter) event.getBusinessObject() );
			} else {
				update( (LogMessageFilter) event.getBusinessObject() );
			}
		}
	}

	public void setLogMessageFilterContainer( LogMessageFilterContainer logMessageFilterSo ) {
		
		this.logMessageFilterSo = logMessageFilterSo;
	}
	
	private class FilterComparator implements Comparator< LogMessageFilter > {

		@Override
		public int compare( LogMessageFilter arg0, LogMessageFilter arg1 ) {

			return arg0.getRegex().compareTo( arg1.getRegex() );
		}
	}
}
