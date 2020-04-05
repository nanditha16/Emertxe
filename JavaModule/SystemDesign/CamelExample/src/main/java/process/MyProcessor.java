package process;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import exception.CamelCustomException;


public class MyProcessor implements Processor {
	Logger logger = LoggerFactory.getLogger(MyProcessor.class);
	
	public void process(Exchange exchange) throws Exception {
		logger.info("Proccess the file. ");
		logger.info("We check if the filename has \"1\", then CamelCustomException is thrown after attempting to redeliver 5 times. ");
		String fileName = (String) exchange.getIn().getHeader("CamelFileNameConsumed");
		System.out.println("fileName: " + fileName);
		if (fileName.contains("1"))
            throw new CamelCustomException();
	}

}
