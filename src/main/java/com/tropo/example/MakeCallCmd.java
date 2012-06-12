package com.tropo.example;

import java.net.URI;
import java.net.URISyntaxException;

import com.rayo.client.RayoClient;
import com.rayo.core.DialCommand;
import com.rayo.core.verb.VerbRef;

public class MakeCallCmd {
	
	protected RayoClient client;
	
	public void connect(String xmppServer, String username, String password, String rayoServer) throws Exception {
		
		client = new RayoClient(xmppServer, rayoServer);
		login(username,password,"voxeo");
	}
	
	public void shutdown() throws Exception {
		
		client.disconnect();
	}
		
	void login(String username, String password, String resource) throws Exception {
		
		client.connect(username, password, resource);
	}
       


    
   
   
   	public void makeCall(String from, String to) throws Exception {
		
		DialCommand dial = new DialCommand();
		dial.setTo(new URI("sip:" + to +"@127.0.0.1"));
		//dial.setTo(new URI("sip:usera@127.0.0.1"));
		try {
			dial.setFrom(new URI("sip:" + from+ "@127.0.0.1"));		
		} catch (URISyntaxException e) {
			System.err.println("Error generate From dial " + e);
			e.printStackTrace();
		}
		
		VerbRef dialRef = client.dial(dial);
		/*Thread.sleep(6000);
		client.hangup(dialRef.getCallId());*/
	}
    
   	public MakeCallCmd() {
   		
   	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
 	MakeCallCmd mc = new MakeCallCmd();
		try {
			mc.connect("localhost", "userb", "1", "localhost");
			System.out.println("From " + args[0] + " To " +args [1]);
			mc.makeCall(args[0],args[1]);
			mc.shutdown();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
