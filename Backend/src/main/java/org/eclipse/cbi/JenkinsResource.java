package org.eclipse.cbi;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import io.kubernetes.client.ApiException;


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

}