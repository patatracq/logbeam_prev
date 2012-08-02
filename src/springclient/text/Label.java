
package springclient.text;

import javax.swing.JLabel;

import crudbeam.bind.PropertyBinding;
import crudbeam.bind.PropertyChangedListener;

import springclient.DefaultSwingComponent;


public class Label extends DefaultSwingComponent< Label, JLabel > implements PropertyChangedListener< String > {

	private String caption;
	private PropertyBinding< String > captionBinding;
	
	public Label() {
	
		super( new JLabel() );
	}
	
	public Label( PropertyBinding< String > captionBinding ) {
		
		super( new JLabel() );
		
		this.captionBinding = captionBinding;
		if ( this.captionBinding.getValue() != null && this.captionBinding.getValue().length() > 0 ) {
			setCaption( captionBinding.getValue() );
		}
		
		this.captionBinding.addPropertyChangedListener( this );
	}
	
	@Override
	public void propertyChanged( String newValue ) {

		setCaption( newValue );
	}

	public void setCaption( String caption ) {
		
		this.caption = caption;
		toSwing().setText( this.caption );
	}
}
