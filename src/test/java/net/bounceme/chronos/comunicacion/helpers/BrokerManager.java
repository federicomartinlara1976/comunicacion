package net.bounceme.chronos.comunicacion.helpers;

import org.apache.qpid.server.Broker;
import org.apache.qpid.server.BrokerOptions;

import com.google.common.io.Files;

public class BrokerManager {
	 
	public static final int BROKER_PORT = 5672;
	
    private final Broker broker = new Broker();
     
    public void startBroker() throws Exception {
        final String configFileName = "qpid-config.json";
        final String passwordFileName = "passwd.properties";

        // prepare options
        final BrokerOptions brokerOptions = new BrokerOptions();
        brokerOptions.setConfigProperty("qpid.amqp_port", String.valueOf(BROKER_PORT));
        brokerOptions.setConfigProperty("qpid.pass_file", passwordFileName);
        brokerOptions.setConfigProperty("qpid.work_dir", Files.createTempDir().getAbsolutePath());
        brokerOptions.setInitialConfigurationLocation(configFileName);
         
        broker.startup(brokerOptions);
    }
     
    public void stopBroker() {
        broker.shutdown();
    }
}
