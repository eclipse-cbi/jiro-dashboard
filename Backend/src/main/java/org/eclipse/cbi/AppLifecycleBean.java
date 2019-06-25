package org.eclipse.cbi;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;

import io.kubernetes.client.ApiClient;
import io.kubernetes.client.ApiException;

import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class AppLifecycleBean {

    private static final Logger LOGGER = LoggerFactory.getLogger("ListenerBean");
	private static final String URL = "http://localhost:8090";

    void onStart(@Observes StartupEvent ev) {               
        LOGGER.info("The application is starting...");
        try {
                ApiClient client = new ApiClient().setBasePath(URL);
                KubeService service = new KubeService();
                service.setApiClient(client);
                service.watchStatefulSets();

        } catch(ApiException e) {
            System.out.println(e);
        }
    }

    void onStop(@Observes ShutdownEvent ev) {               
        LOGGER.info("The application is stopping...");
    }

}
