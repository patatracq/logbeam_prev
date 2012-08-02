package crudbeam.cyberspace;

import jonas.annotations.JonasElement;


public class CyberspaceReply< T > {

	private T reply;

	public CyberspaceReply() {}
	
	public CyberspaceReply( T reply ) {
		
		this.reply = reply;
	}
	
	@JonasElement
	public T getReply() {
		
		return reply;
	}
	
	public void setReply( T reply ) {
		
		this.reply = reply;
	}
}
