package service;
import org.apache.camel.CamelContext;
import org.apache.camel.Endpoint;
import org.apache.camel.EndpointInject;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.RoutesBuilder;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class FileLoaderServiceTest {
	private Endpoint in;
    private MockEndpoint out;
    private ProducerTemplate producer;

	@Before
    public void setup() {
        CamelContext context = new DefaultCamelContext();

        this.in = context.getEndpoint("file:src/main/resources/inputFolder?noop=true");
        this.out = (MockEndpoint) context.getEndpoint("file:src/main/resources/outputFolder2");
        this.producer = context.createProducerTemplate();
        this.producer.setDefaultEndpoint(this.in);

        RouteBuilder myRoute = new MyRoute(this.in, this.out);
        try {
			context.addRoutes(myRoute);
		} catch (Exception e) {
			e.printStackTrace();
		}

        context.start();
    }
	
	 @Test
	    public void test() throws Exception {
	        this.producer.start();
	        this.producer.sendBody(null);
	        this.out.expectedMessageCount(1);
	        this.out.assertIsSatisfied();
	    }
}
