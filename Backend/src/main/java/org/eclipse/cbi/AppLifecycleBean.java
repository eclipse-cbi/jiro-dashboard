package org.eclipse.cbi;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject ;
import java.io.IOException;

import io.kubernetes.client.util.Config;
import io.kubernetes.client.ApiClient;
import io.kubernetes.client.ApiException;
import io.kubernetes.client.util.ClientBuilder;

import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class AppLifecycleBean {

    private static final Logger LOGGER = LoggerFactory.getLogger("ListenerBean");

    @Inject
    KubeService service ;

    // The watch is automatically started when the server is started
    void onStart(@Observes StartupEvent ev) {
        LOGGER.info("The application is starting...");
        try {
                ApiClient client = ClientBuilder.cluster().build(); // Using client inside the cluster
                service.setApiClient(client);
                service.watchStatefulSets();
        } catch(ApiException e) {
            System.out.println(e);
        } catch(IOException ex ) {
	    System.out.println(ex);
	}
    }

    void onStop(@Observes ShutdownEvent ev) {               
        LOGGER.info("The application is stopping...");
    }

}
