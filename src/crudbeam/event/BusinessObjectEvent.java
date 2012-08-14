package crudbeam.event;

import jonas.annotations.JonasCreator;
import jonas.annotations.JonasElement;

import org.springframework.context.ApplicationEvent;

import crudbeam.business.BusinessPojo;

@JonasCreator
public class BusinessObjectEvent extends ApplicationEvent {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2586832248226694404L;

	private BusinessEventType type;
	private BusinessPojo businessObject;
	
	public BusinessObjectEvent( Object source ) {
		
		super( source );
	}
	
	public BusinessObjectEvent( Object source, BusinessObjectEvent event ) {
		
		super( source );
		
		this.type = event.type;
		this.businessObject = event.businessObject;
	}
	
	public BusinessObjectEvent( Object source, BusinessEventType type, BusinessPojo businessObject ) {
		
		super( source );
		
		this.type = type;
		this.businessObject = businessObject;
	}
	
	public boolean changed() {
		
		return ( type instanceof BusinessObjectChanged );
	}

	public boolean created() {
		
		return ( type instanceof BusinessObjectCreated );
	}

	public boolean deleted() {
		
		return ( type instanceof BusinessObjectDeleted );
	}

	@Override
	@JonasElement
	public Object getSource() {
		
		return super.getSource();
	}
	
	public void setSource( Object source ) {
		
		super.source = source;
	}
	
	@JonasElement
	public BusinessEventType getType() {
		
		return type;
	}
	
	public void setType( BusinessEventType type ) {
		
		this.type = type;
	}
	
	@JonasElement
	public BusinessPojo getBusinessObject() {
		
		return businessObject;
	}
	
	public void setBusinessObject( BusinessPojo businessObject ) {
		
		this.businessObject = businessObject;
	}
}
