package service;

import org.apache.camel.CamelContext;
import org.apache.camel.Endpoint;
import org.apache.camel.builder.RouteBuilder;

public class MyRoute extends RouteBuilder {
	 private Endpoint in;
	    private Endpoint out;

	    
	    // This is the constructor your test can call, although it would be fine
	    // to use in production too
	    public MyRoute(Endpoint in, Endpoint out) {
	        this.in = in;
	        this.out = out;
	    }
	    
	@Override
	public void configure() throws Exception {
		from(this.in)
        .to(this.out);

	}

}
