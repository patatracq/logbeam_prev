package crudbeam.cyberspace;

import java.util.ArrayList;
import java.util.List;

import jonas.annotations.JonasElement;


public class CyberspaceRequest {

	private String beanId;
	private String method;
	private List< Object > parameters;
	
	public static List< Object > parameters( Object[] parameters ) {
		
		ArrayList< Object > list = new ArrayList< Object >();
		for ( int i = 0; i < parameters.length; i++ ) {
			list.add( parameters[ i ] );
		}
		
		return list;
	}
	public CyberspaceRequest() {}
	
	public CyberspaceRequest( String beanId, String method, List< Object > parameters ) {
	
		this.beanId = beanId;
		this.method = method;
		this.parameters = parameters;
	}
	
	@JonasElement
	public String getBeanId() {
		
		return beanId;
	}
	
	public void setBeanId( String beanId ) {
		
		this.beanId = beanId;
	}
	
	@JonasElement
	public String getMethod() {
		
		return method;
	}
	
	public void setMethod( String method ) {
		
		this.method = method;
	}
	
	@JonasElement
	public List< Object > getParameters() {
		
		return parameters;
	}
	
	public void setParameters( List< Object > parameters ) {
		
		this.parameters = parameters;
	}
}
