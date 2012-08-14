package springclient.text;

import java.awt.Color;

import javax.swing.plaf.basic.BasicBorders;
import javax.swing.text.JTextComponent;

import crudbeam.bind.PropertyBinding;
import crudbeam.bind.PropertyChangedListener;

import springclient.BoundComponent;
import springclient.DefaultSwingComponent;


public abstract class DefaultTextComponent< T, J > extends DefaultSwingComponent< T, J > implements BoundComponent, PropertyChangedListener< String > {

	private static final Color disabledBackground = new Color( -2039837 );
	private static final Color disabledForeground = new Color( -16777216 );

	private JTextComponent jTextComponent;
	private PropertyBinding< String > textBinding;
	
	public DefaultTextComponent( JTextComponent jTextComponent, PropertyBinding< String > textBinding ) {
		super( jTextComponent );
		
		this.jTextComponent = jTextComponent;
		this.jTextComponent.setBorder( BasicBorders.getTextFieldBorder() );
		this.jTextComponent.setMaximumSize( this.jTextComponent.getPreferredSize() );
		
		this.textBinding = textBinding;
		if ( this.textBinding.getValue() != null ) {
			this.jTextComponent.setText( textBinding.getValue() );
		}
		
		this.textBinding.addPropertyChangedListener( this );
	}

	@Override
	public void setEnabled( boolean enabled ) {

		super.setEnabled( enabled );
		
		if ( enabled ) {
			super.getJComponent().setBackground( Color.WHITE );
			super.getJComponent().setForeground( Color.BLACK );
		} else {
			super.getJComponent().setBackground( disabledBackground );
			super.getJComponent().setForeground( disabledForeground );
		}
	}
	
	public String getText() {
		
		return jTextComponent.getText();
	}
	
	public void setText( String text ) {
		
		textBinding.setValue( text );
	}
	
	@Override
	public void propertyChanged( String newValue ) {

		jTextComponent.setText( newValue );
	}

	@Override
	public void save() {

		textBinding.setValue( this, jTextComponent.getText() );
		textBinding.save();
	}
}
