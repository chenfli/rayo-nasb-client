package com.tropo.example;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rayo.client.RayoClient;
import com.rayo.core.DialCommand;
import com.rayo.core.verb.VerbRef;

/**
 * Servlet implementation class MakeCall
 */
public class MakeCall extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
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
		
/*		client.addStanzaListener(new StanzaListener() {
			
			@Override
			public void onPresence(Presence presence) {

				System.out.println(String.format("Message from server: [%s]",presence));
			}
			
			@Override
			public void onMessage(Message message) {

				System.out.println(String.format("Message from server: [%s]",message));
			}
			
			@Override
			public void onIQ(IQ iq) {

				System.out.println(String.format("Message from server: [%s]",iq));				
			}
			
			@Override
			public void onError(Error error) {

				System.out.println(String.format("Message from server: [%s]",error));
			}
		});*/
	}
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MakeCall() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    public static void main(String[] args) {
    	MakeCall mc = new MakeCall();
    	try {
    		System.out.println("From " + args[0] + " To " +args [1]);
			mc.makeCall(args[0],args[1]);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
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
		
		VerbRef dialRef = client.dial(dia   l);
		/*Thread.sleep(6000);
		client.hangup(dialRef.getCallId());*/
	}
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("MakeCall DoGet");
		try {
			connect("localhost", "userb", "1", "localhost");	
			makeCall(request.getAttribute("From").toString(), request.getAttribute("To").toString());		
			shutdown();
		} catch (Exception e) {
			System.err.println("Error generate From dial " + e);
			e.printStackTrace();
		}
		response.setContentType("application/html");
		java.io.OutputStream os = response.getOutputStream();		
		String outHtml = "<HTML><BODY>Call was made from" + request.getAttribute("From") + " to " + request.getAttribute("To") + "</BODY></HTML>";
		os.write(outHtml.getBytes());
		os.flush();
		os.close();
	}
}
