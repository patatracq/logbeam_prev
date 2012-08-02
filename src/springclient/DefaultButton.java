package springclient;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractButton;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

import crudbeam.action.Action;



public abstract class DefaultButton< T, J > extends DefaultSwingComponent< T, J > implements ActionListener, ApplicationListener< ApplicationEvent > {

	private AbstractButton jButton;
	private Action action;
	private Class< ? > activationEvent;
	private Class< ? > deactivationEvent;
	
	public DefaultButton( AbstractButton jButton ) {
		
		super( jButton );
		
		this.jButton = jButton;
	}

	public String getCaption() {
		
		return jButton.getText();
	}
	
	public void setCaption( String caption ) {
		
		jButton.setText( caption );
	}
	
	public Class< ? > getActivationEvent() {
		
		return activationEvent;
	}

	public void setActivationEvent( Class< ? > activationEvent ) {
		
		this.activationEvent = activationEvent;
	}

	public Class< ? > getDeactivationEvent() {
		
		return deactivationEvent;
	}

	public void setDeactivationEvent( Class< ? > deactivationEvent ) {
		
		this.deactivationEvent = deactivationEvent;
	}

	public Action getAction() {
		
		return this.action;
	}
	
	public void setAction( Action action ) {
		
		this.action = action;
		jButton.addActionListener( this );
	}

	@Override
	public void actionPerformed( ActionEvent event ) {
		
		if ( action != null ) {
			action.execute( this );
		}
	}

	@Override
	public void onApplicationEvent( ApplicationEvent event ) {

		if ( event.getClass().equals( activationEvent ) ) {
			super.setEnabled( true );
		} else if ( event.getClass().equals( deactivationEvent ) ) {
			super.setEnabled( false );
		}
	}
}
