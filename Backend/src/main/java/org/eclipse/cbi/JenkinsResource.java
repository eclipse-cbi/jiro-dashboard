package org.eclipse.cbi;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import io.kubernetes.client.ApiException;


@Path("/jenkins")
public class JenkinsResource {

	
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getStatefulSets() throws ApiException {
		KubeService service = new KubeService();
		return(service.getStatefulSetData().toString());
	}

}