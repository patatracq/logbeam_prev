package logbeam.server;

import org.springframework.integration.Message;

public class ClientRouter {

	public void routeMessage( Message< ? > message ) {
		// TODO
		System.out.println( message );
	}
}
