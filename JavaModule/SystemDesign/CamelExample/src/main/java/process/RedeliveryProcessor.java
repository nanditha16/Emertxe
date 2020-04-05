package process;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class RedeliveryProcessor implements Processor  {

	/*
	 * When you need to make some changes before attempting to re-deliver the file
	 * The file here is not renamed, but we are modifying on stream to pass to process
	 */
	public void process(Exchange exchange) throws Exception {
		String fileName = (String) exchange.getIn().getHeader("CamelFileNameConsumed");	
		String reNameFile = fileName.replace("1", "_RedeliverModified");
		exchange.getIn().setHeader("CamelFileNameConsumed", reNameFile);		
	}

}
