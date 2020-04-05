package service;

import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import route.SimpleRouteBuilder;

public class FileLoaderService {
	
	public static void main(String[] args) {
		//routeBuilderUsingJavaDSL();
		routeBuilderUsingXMLConfig();

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
