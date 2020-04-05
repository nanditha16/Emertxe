package route;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import exception.CamelCustomException;
import process.MyProcessor;
import process.RedeliveryProcessor;

public class SimpleRouteBuilder extends RouteBuilder {
	Logger logger = LoggerFactory.getLogger(SimpleRouteBuilder.class);
	
	@Override
	public void configure() throws Exception {
		//simpleRouting();
		//simpleProcessRouting();
		//handleExceptionUsingDoTryProcessRouting();
		//handleExceptionusingOnExceptionProcessRouting();
		//handleRedeliveryPolicyProcessRouting();
		//handleCorrectionBeforeRedeliveryPolicyProcessRouting();
		handleRoutingOnSplitEachLineRouting();
		
	}
	
	private void handleRoutingOnSplitEachLineRouting() {
		//TODO: To kafka
		 from("file:/Users/nanditha/eclipse-workspace/CamelExample/src/main/resources/inputFolder?noop=true")
		 .split().tokenize("\n")
		 .to("file:/Users/nanditha/eclipse-workspace/CamelExample/src/main/resources/outputFolder");		   
			
		
	}

	private void handleCorrectionBeforeRedeliveryPolicyProcessRouting() {
		onException(CamelCustomException.class).process(new Processor() {
            public void process(Exchange exchange) throws Exception {
                System.out.println("handling ex");
            }
        })
		.onRedelivery(new RedeliveryProcessor())
		.redeliveryPolicyRef("testRedeliveryPolicyProfile").log("Received body ").handled(true);

		//Route 1
		from("file:/Users/nanditha/eclipse-workspace/CamelExample/src/main/resources/inputFolder?noop=true")
		.process(new MyProcessor())
		.to("file:/Users/nanditha/eclipse-workspace/CamelExample/src/main/resources/outputFolder");
	
		
	}

	private void handleRedeliveryPolicyProcessRouting() {
		onException(CamelCustomException.class).process(new Processor() {
            public void process(Exchange exchange) throws Exception {
                System.out.println("handling ex");
            }
        }).redeliveryPolicyRef("testRedeliveryPolicyProfile").log("Received body ").handled(true);

		//Route 1
		from("file:/Users/nanditha/eclipse-workspace/CamelExample/src/main/resources/inputFolder?noop=true")
		.process(new MyProcessor())
		.to("file:/Users/nanditha/eclipse-workspace/CamelExample/src/main/resources/outputFolder");
	}

	/*
	 * handles exception in all the routes in similar fashion
	 */
	private void handleExceptionusingOnExceptionProcessRouting() {
		onException(CamelCustomException.class).process(new Processor() {
            public void process(Exchange exchange) throws Exception {
                System.out.println("handling ex");
            }
        }).log("Received body ").handled(true);
		
		//Route 1
		from("file:/Users/nanditha/eclipse-workspace/CamelExample/src/main/resources/inputFolder?noop=true")
		.process(new MyProcessor())
		.to("file:/Users/nanditha/eclipse-workspace/CamelExample/src/main/resources/outputFolder");

		//Route 2
		from("file:/Users/nanditha/eclipse-workspace/CamelExample/src/main/resources/inputFolder2?noop=true")
		.process(new MyProcessor())
		.to("file:/Users/nanditha/eclipse-workspace/CamelExample/src/main/resources/outputFolder2");

	}

	/*
	 * Configured only for a single route i.e route specific
	 * thrown exception will be immediately caught and the message wont keep on retrying.
	 * i.e noop=true" cannot be handled
	 */
	private void handleExceptionUsingDoTryProcessRouting() {
		 from("file:/Users/nanditha/eclipse-workspace/CamelExample/src/main/resources/inputFolder?noop=true")
		 .doTry()
		 .process(new MyProcessor())
		 .to("file:/Users/nanditha/eclipse-workspace/CamelExample/src/main/resources/outputFolder")
		 .doCatch(CamelCustomException.class).process(new Processor() {

			public void process(Exchange exchange) throws Exception {
				System.out.println("Handling CamelCustomException...");
				
			}}).log("Received body ");		   
	
		
	}

	private void simpleProcessRouting() {
		 from("file:/Users/nanditha/eclipse-workspace/CamelExample/src/main/resources/inputFolder?noop=true")
		 .process(new MyProcessor())
		 .to("file:/Users/nanditha/eclipse-workspace/CamelExample/src/main/resources/outputFolder");		   
		
	}

	private void simpleRouting() {
	 from("file:/Users/nanditha/eclipse-workspace/CamelExample/src/main/resources/inputFolder?noop=true").to("file:/Users/nanditha/eclipse-workspace/CamelExample/src/main/resources/outputFolder");		   
		
		
	}

}
