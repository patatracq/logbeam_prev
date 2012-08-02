package logbeam.client;

import java.util.ArrayList;
import java.util.List;

import crudbeam.bind.PropertyBinding;
import crudbeam.bind.PropertyChangedListener;

import logbeam.business.LogFile;
import springclient.list.DomainSpecificListModel;


public class LogFileList extends DomainSpecificListModel implements PropertyChangedListener< List< LogFile > > {

	private List< LogFile > logFiles = new ArrayList< LogFile >();
	private PropertyBinding< List< LogFile > > binding;
	
	@Override
	public Object getElementAt( int index ) {
		
		return logFiles.get( index );
	}

	@Override
	public int getSize() {

		return logFiles.size();
	}

	public void setBinding( PropertyBinding< List< LogFile > > binding ) {
		
		this.binding = binding;
		this.binding.addPropertyChangedListener( this );
		
		propertyChanged( binding.getValue() );
	}

	@Override
	public void propertyChanged( List< LogFile > newValue ) {

		if ( logFiles.size() > 0 ) {
			super.itemsDeleted( 0, logFiles.size() - 1 );
		}
		
		logFiles = newValue;

		if ( logFiles.size() > 0 ) {
			super.itemsInserted( 0, logFiles.size() - 1 );
		}
	}
}
