package springclient;

import java.awt.Dimension;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JComponent;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;

import crudbeam.event.EventFactory;


@SuppressWarnings( "unchecked" )
public abstract class DefaultSwingComponent< T, J >
		implements
			SwingComponent< T, J >, FocusListener, ApplicationEventPublisherAware {

	private SwingComponent< ?, ? > parent;
	private JComponent jComponent;
	
	private ApplicationEventPublisher eventPublisher;
	
	private EventFactory focusEventFactory;
	private EventFactory unfocusEventFactory;
	
	public DefaultSwingComponent( JComponent jComponent ) {
		
		this.jComponent = jComponent;
		this.jComponent.addFocusListener( this );
	}
	
	public DefaultSwingComponent( SwingComponent< ?, ? > parent, JComponent jComponent ) {
		
		this.parent = parent;
		this.jComponent = jComponent;
		this.jComponent.addFocusListener( this );
	}
	
	@Override
	public void setAlignX( float alignment ) {

		jComponent.setAlignmentX( alignment );
	}

	@Override
	public void setAlignY( float alignment ) {

		jComponent.setAlignmentY( alignment );
	}

	public boolean getEnabled() {
		
		return jComponent.isEnabled();
	}
	
	public void setEnabled( boolean enabled ) {

		jComponent.setEnabled( enabled );
	}

	@Override
	public void setPreferredSize( Dimension size ) {

		jComponent.setPreferredSize( size );
	}
	
	public void setApplicationEventPublisher( ApplicationEventPublisher eventPublisher ) {
		
		this.eventPublisher = eventPublisher;
	}
	
	public void setFocusEventFactory( EventFactory focusEventFactory ) {
		
		this.focusEventFactory = focusEventFactory;
	}
	
	public void setUnfocusEventFactory( EventFactory unfocusEventFactory ) {
		
		this.unfocusEventFactory = unfocusEventFactory;
	}

	@Override
	public void focusGained( FocusEvent event ) {
		
		if (
				eventPublisher != null &&
				focusEventFactory != null &&
				event.getSource() != null &&
				event.getSource() == jComponent ) {
			
			eventPublisher.publishEvent( focusEventFactory.createEvent( this ) );
		}
	}
	
	@Override
	public void focusLost( FocusEvent event ) {
		
		if (
				eventPublisher != null &&
				unfocusEventFactory != null &&
				event.getSource() != null &&
				event.getSource() == jComponent ) {
			
			eventPublisher.publishEvent( unfocusEventFactory.createEvent( this ) );
		}
	}
	
	public void changed() {
		
		if ( parent != null ) {
			parent.revalidate();
		}
	}
	
	@Override
	public void revalidate() {}
	
	@Override
	public J toSwing() {
		
		return (J) jComponent;
	}
	
	public JComponent getJComponent() {

		return jComponent;
	}
}
