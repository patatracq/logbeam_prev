package logbeam.client;

import springclient.SpringClient;


public class LogBeamClient {

	public static void main( String[] args ) {
		
		System.setProperty( "log4j.configuration", "log4j.properties" );
		SpringClient.newClient( new String[] { "client-config.xml" } ).run();
	}
}
