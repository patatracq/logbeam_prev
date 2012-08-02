package crudbeam.cyberspace;


public interface CyberspaceClient {

	public CyberspaceReply< ? > callServer( CyberspaceRequest request );
}
