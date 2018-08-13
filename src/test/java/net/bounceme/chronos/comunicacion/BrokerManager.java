package net.bounceme.chronos.comunicacion;

import org.apache.qpid.server.Broker;
import org.apache.qpid.server.BrokerOptions;

public class BrokerManager {
	 
    private static final String INITIAL_CONFIG_PATH = "<your_path_to_the_above_json_file>";
    private static final String PORT = "<your_port>";
    private final Broker broker = new Broker();
     
    public void startBroker() throws Exception {
        final BrokerOptions brokerOptions = new BrokerOptions();
        brokerOptions.setConfigProperty("qpid.amqp_port", PORT);
        brokerOptions.setInitialConfigurationLocation(INITIAL_CONFIG_PATH);
         
        broker.startup(brokerOptions);
    }
     
    public void stopBroker() {
        broker.shutdown();
    }
}
