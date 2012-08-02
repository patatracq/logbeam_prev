package springclient.combo;

import javax.swing.ComboBoxModel;
import javax.swing.event.ListDataListener;


public class GenericComboModel implements ComboBoxModel {

	private DomainSpecificComboModel domainModel;
	
	public GenericComboModel( DomainSpecificComboModel domainSpecificComboModel ) {
		
		domainModel = domainSpecificComboModel;
	}
	
	@Override
	public Object getElementAt( int index ) {

		return domainModel.getElementAt( index );
	}

	@Override
	public int getSize() {

		return domainModel.getSize();
	}

	@Override
	public Object getSelectedItem() {

		return domainModel.getSelectedItem();
	}

	@Override
	public void setSelectedItem( Object item ) {

		domainModel.setSelectedItem( item );
	}

	@Override
	public void addListDataListener( ListDataListener listener ) {

		domainModel.addListDataListener( listener );
	}

	@Override
	public void removeListDataListener( ListDataListener listener ) {
		
		domainModel.removeListDataListener( listener );
	}
}
