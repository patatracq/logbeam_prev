package springclient.list;

import javax.swing.ListModel;
import javax.swing.event.ListDataListener;


public class GenericListModel implements ListModel {

	private DomainSpecificListModel domainList;
	
	public GenericListModel( DomainSpecificListModel domainSpecificListModel ) {

		this.domainList = domainSpecificListModel;
	}
	
	@Override
	public Object getElementAt( int index ) {

		return domainList.getElementAt( index );
	}

	@Override
	public int getSize() {

		return domainList.getSize();
	}

	@Override
	public void addListDataListener( ListDataListener listener ) {

		domainList.addListDataListener( listener );
	}

	@Override
	public void removeListDataListener( ListDataListener listener ) {

		domainList.removeListDataListener( listener );
	}
}
