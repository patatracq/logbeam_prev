package logbeam.client.bind.logmessagefilter;

import org.springframework.beans.factory.annotation.Required;

import crudbeam.bind.PropertyBinding;
import logbeam.business.LogMessageFilter;
import logbeam.business.logmessagefilter.LogMessageFilterContainer;


public class LogMessageFilterOriginalMessage extends PropertyBinding< String > {

	private LogMessageFilter filter;
	private LogMessageFilterContainer container;

	@Override
	public void reset() {
		
		filter = null;
		super.notifyListeners( getValue() );
	}
	
	@Override
	public String getValue() {

		if ( filter != null ) {
			return filter.getOriginalMessage();
		} else {
			return "";
		}
	}

	@Override
	public void setValue( String value ) {

		if ( filter == null ) {
			filter = (LogMessageFilter) container.get( 0L );
		}
		
		filter.setOriginalMessage( value );
		super.notifyListeners( filter.getOriginalMessage() );
	}

	@Override
	public void setValue( PropertyBinding< ? > value ) {
		
		if ( value.getValue().getClass().equals( String.class ) ) {
			setValue( (String) value.getValue() );
		}
	}

	@Override
	public void save() {

		container.save( filter );
	}

	@Override
	public void delete() {
		
		container.delete( filter );
		filter = null;
	}

	public void setFilter( LogMessageFilter filter ) {
		
		this.filter = filter;
		super.notifyListeners( filter.getOriginalMessage() );
	}

	@Required
	public void setContainer( LogMessageFilterContainer container ) {
		
		this.container = container;
	}
}
