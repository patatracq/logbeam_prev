package crudbeam.cyberspace;


public interface CyberspaceServer {

	public CyberspaceReply< ? > executeMethod( CyberspaceRequest request );
}
