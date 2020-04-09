package service;

import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import jettyHandler.HelloHandler;
import route.SimpleRouteBuilder;

public class FileLoaderService {
	
	public static void main(String[] args) {
		// TODO: 
		//setJetty();
		
		//routeBuilderUsingJavaDSL();
		routeBuilderUsingXMLConfig();

    }

	private static Boolean setJetty() {
		   Server server = createServer(8080);

	        /*
	         * To produce a response to a request, Jetty requires that 
	         * you set a Handler on the server.
	         * 
	         */
	        
	      
	        try {
	        	server.join();
				server.start();
				return true;
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
	        return false;
		}

	private static Server createServer(int port) {
		Server server = new Server(port);
		ServletHandler handler = new ServletHandler();
		handler.addServletWithMapping(HelloHandler.class, "/ping");
		server.setHandler(handler);
		
		return server;
	}

	private static void routeBuilderUsingXMLConfig() {
		AbstractApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext-camel.xml");
		ctx.start();
        System.out.println("Application context started");
        try {
            Thread.sleep(5 * 60 * 1000);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        ctx.stop();
        ctx.close();
		
	}

	private static void routeBuilderUsingJavaDSL() {
		SimpleRouteBuilder routeBuilder = new SimpleRouteBuilder();
        CamelContext ctx = new DefaultCamelContext();
        try {
            ctx.addRoutes(routeBuilder);
            ctx.start();
            Thread.sleep(5 * 60 * 1000);
            ctx.stop();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
		
	}
}
