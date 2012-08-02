package logbeam.client;

import java.util.ArrayList;
import java.util.List;

import logbeam.business.AgentTemplate;
import logbeam.business.agenttemplate.AgentTemplateContainer;
import springclient.Loadable;
import springclient.combo.DomainSpecificComboModel;


public class AgentTemplateCombo extends DomainSpecificComboModel implements
		Loadable {

	private List< AgentTemplate > agentTemplates;
	private AgentTemplate selectedItem;
	
	private AgentTemplateContainer agentTemplateContainer;
	
	@Override
	public void load() {

		agentTemplates = new ArrayList< AgentTemplate > ( agentTemplateContainer.getAll() );
	}

	@Override
	public Object getElementAt( int index ) {
		
		if ( index < agentTemplates.size() ) {
			return agentTemplates.get( index );
		} else {
			return null;
		}
	}

	@Override
	public int getSize() {

		return agentTemplates.size();
	}

	@Override
	public Object getSelectedItem() {
		
		return selectedItem;
	}

	@Override
	public void setSelectedItem( Object anItem ) {
		
		selectedItem = (AgentTemplate) anItem;
	}

	public void setAgentTemplateContainer( AgentTemplateContainer agentTemplateContainer ) {
		
		this.agentTemplateContainer = agentTemplateContainer;
	}
}
