package logbeam.client;

import java.util.Comparator;
import java.util.SortedSet;
import java.util.TreeSet;

import logbeam.business.AgentTemplate;
import logbeam.business.agenttemplate.AgentTemplateContainer;

import org.springframework.context.ApplicationListener;

import springclient.Loadable;
import springclient.table.DomainSpecificTableModel;
import crudbeam.event.BusinessObjectCreated;
import crudbeam.event.BusinessObjectDeleted;
import crudbeam.event.BusinessObjectEvent;

public class AgentTemplateTable extends DomainSpecificTableModel implements Loadable, ApplicationListener< BusinessObjectEvent > {

	public static final int AGENTTEMPLATE_COLUMN_INDEX = 0;
	
	private SortedSet< AgentTemplate > rows = new TreeSet< AgentTemplate >( new AgentTemplateComparator() );
	private AgentTemplateContainer agentTemplateContainer;
	
	@Override
	public int getRowCount() {

		return rows.size();
	}

	@Override
	public AgentTemplate getValueAt( int rowIndex ) {

		if ( rowIndex < rows.size() ) {
			return (AgentTemplate) rows.toArray()[ rowIndex ];
		} else {
			return null;
		}
	}
	
	@Override
	public Object getValueAt( int rowIndex, int columnIndex ) {

		if ( rowIndex < rows.size() ) {
			if ( columnIndex == AGENTTEMPLATE_COLUMN_INDEX ) {
				return ( (AgentTemplate) rows.toArray()[ rowIndex ] ).getName();
			}
		}
		return null;
	}
	
	public void add( AgentTemplate agentTemplate ) {
		
		rows.add( agentTemplate );
		super.tableRowsChanged();
	}
	
	public void delete( AgentTemplate agentTemplate ) {
		
		rows.remove( agentTemplate );
		super.tableRowsChanged();
	}

	public void update( AgentTemplate agentTemplate ) {
		
		if ( rows.contains( agentTemplate ) ) {
			rows.remove( agentTemplate );
		}
		
		add( agentTemplate );
	}
	
	@Override
	public void load() {
		
		rows.addAll( agentTemplateContainer.getAll() );
		super.tableRowsChanged();
	}
	
	@Override
	public void onApplicationEvent( BusinessObjectEvent event ) {
		
		if ( event.getBusinessObject() instanceof AgentTemplate ) {
			if ( event.getType() instanceof BusinessObjectCreated ) {
				add( (AgentTemplate) event.getBusinessObject() );
			} else if ( event.getType() instanceof BusinessObjectDeleted ) {
				delete( (AgentTemplate) event.getBusinessObject() );
			} else {
				update( (AgentTemplate) event.getBusinessObject() );
			}
		}
	}

	public void setAgentTemplateContainer( AgentTemplateContainer agentTemplateContainer ) {
		
		this.agentTemplateContainer = agentTemplateContainer;
	}
	
	private class AgentTemplateComparator implements Comparator< AgentTemplate > {

		@Override
		public int compare( AgentTemplate arg0, AgentTemplate arg1 ) {

			return arg0.getName().compareTo( arg1.getName() );
		}
	}
}
