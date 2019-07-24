package org.eclipse.cbi;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.net.URI ;
import java.util.Map;

import io.kubernetes.client.ApiException;

import com.offbytwo.jenkins.JenkinsServer;

@Path("/jenkins")
public class JenkinsResource {

    @Inject
    KubeService service;

    /* Gets the data from KubeService which stores all the info
      about the statefulsets retrieved from the Cluster */
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getStatefulSets() throws ApiException {
		return(service.getStatefulSetData().toString());
    }


    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/{name}")   // name of the statefulset
    public String getJenkinsDetails(@PathParam("name") String name) {
      String data = "";
      String serviceURL = "http://jenkins-ui." + name + ".svc.cluster.local/"+ name;
      try {
           JenkinsServer jenkins = new JenkinsServer(new URI(serviceURL)); // Jenkins Client not really used right now but it works
           data = service.getJenkinsVersion(name); // Getting the jenkins version using KubeService class
      } catch(Exception e) {
        System.out.println("Error : " + e );
      }
      return(data);
    }
}
