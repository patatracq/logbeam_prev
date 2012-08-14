package springclient.simple;

import java.util.Date;

import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import javax.swing.plaf.basic.BasicBorders;

import crudbeam.bind.PropertyBinding;
import crudbeam.bind.PropertyChangedListener;

import springclient.BoundComponent;
import springclient.DefaultSwingComponent;

public class DateTimeSpinner extends DefaultSwingComponent< DateTimeSpinner, JSpinner > implements BoundComponent, PropertyChangedListener< Long > {

	private JSpinner.DateEditor editor;
	private PropertyBinding< Long > millisecondsBinding;

	public DateTimeSpinner( PropertyBinding< Long > millisecondsBinding ) {
		
		super( new JSpinner( new SpinnerDateModel() ) );
		toSwing().setBorder( BasicBorders.getTextFieldBorder() );
		editor = new JSpinner.DateEditor( toSwing(), "yyyy-MM-dd HH:mm:ss" );
		toSwing().setEditor( editor );
		toSwing().setValue( new Date() );
		
		this.millisecondsBinding = millisecondsBinding;
		this.millisecondsBinding.setValue( editor.getModel().getDate().getTime() );
		this.millisecondsBinding.addPropertyChangedListener( this );
	}

	@Override
	public void save() {

		millisecondsBinding.setValue( this, editor.getModel().getDate().getTime() );
		millisecondsBinding.save();
	}

	@Override
	public void propertyChanged( Long newValue ) {

		toSwing().setValue( new Date( millisecondsBinding.getValue() ) );
	}
}
