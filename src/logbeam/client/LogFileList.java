package logbeam.client;

import java.util.List;

import logbeam.business.LogFile;
import springclient.BoundComponent;
import springclient.list.DomainSpecificListModel;
import crudbeam.bind.PropertyBinding;
import crudbeam.bind.PropertyChangedListener;


public class LogFileList extends DomainSpecificListModel implements PropertyChangedListener< List< LogFile > >, BoundComponent {

	private PropertyBinding< List< LogFile > > binding;
	
	@Override
	public Object getElementAt( int index ) {
		
		return binding.getValue().get( index );
	}

	@Override
	public int getSize() {

		return binding.getValue().size();
	}

	public void setBinding( PropertyBinding< List< LogFile > > binding ) {
		
		this.binding = binding;
		this.binding.addPropertyChangedListener( this );
		
		propertyChanged( binding.getValue() );
	}

	@Override
	public void propertyChanged( List< LogFile > newValue ) {

		if ( binding.getValue().size() > 0 ) {
			super.itemsDeleted( 0, binding.getValue().size() - 1 );
		}
		
		if ( binding.getValue().size() > 0 ) {
			super.itemsInserted( 0, binding.getValue().size() - 1 );
		}
	}

	@Override
	public void save() {
		
		binding.save();
	}
}
