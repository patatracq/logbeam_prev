package integration;

import java.lang.reflect.Method;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.integration.annotation.ServiceActivator;

import crudbeam.cyberspace.CyberspaceReply;
import crudbeam.cyberspace.CyberspaceRequest;
import crudbeam.cyberspace.CyberspaceServer;


public class IntegrationServer implements CyberspaceServer, ApplicationContextAware {

	private static Logger logger = Logger.getLogger( IntegrationServer.class );
	
	private ApplicationContext applicationContext;
	
	@SuppressWarnings( { "rawtypes", "unchecked" } )
	@Override
	@ServiceActivator
	public CyberspaceReply< ? > executeMethod( CyberspaceRequest request ) {
		
		logger.debug( "executeMethod " + request.getBeanId() + "." + request.getMethod() );
		Object executor = applicationContext.getBean( request.getBeanId() );
		
		String methodName = request.getMethod();

		Method[] methods = executor.getClass().getMethods();
		Method method = null;
		for ( int i = 0; i < methods.length; i++ ) {
			if ( methods[ i ].getName().equals( methodName ) ) {
				method = methods[ i ];
			}
		}
		
		Class< ? > returnType = method.getReturnType();
		
		CyberspaceReply< ? > reply = null;
		try {
			
			if ( request.getParameters() != null ) {
			/*	List< Class< ? > > parameterTypes = new ArrayList< Class< ? > >();
				Iterator< Object > iterator = request.getParameters().iterator();
				while ( iterator.hasNext() ) {
					Class< ? > parameterType = iterator.next().getClass();
					parameterTypes.add( parameterType );
				}
	
				Method method = executor.getClass().getMethod( methodName, (Class< ? >[]) parameterTypes.toArray() );*/
				
				reply = new CyberspaceReply( returnType.cast( method.invoke( executor, request.getParameters().toArray() ) ) );
			} else  {
//				Method method = executor.getClass().getMethod( request.getMethod() );
				reply = new CyberspaceReply( returnType.cast( method.invoke( executor ) ) );
			}
		} catch ( Exception e ) {
			throw new RuntimeException( e );
		}
		
		return reply;
	}

	@Override
	public void setApplicationContext( ApplicationContext applicationContext ) throws BeansException {
		
		this.applicationContext = applicationContext;
	}

}
