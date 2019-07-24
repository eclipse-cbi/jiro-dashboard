package org.eclipse.cbi;

import com.google.gson.reflect.TypeToken;
import io.kubernetes.client.ApiClient;
import io.kubernetes.client.ApiException;
import io.kubernetes.client.Configuration;
import io.kubernetes.client.apis.AppsV1Api;
import io.kubernetes.client.apis.CoreV1Api;
import io.kubernetes.client.models.V1Service;
import io.kubernetes.client.models.V1StatefulSet;
import io.kubernetes.client.models.V1StatefulSetStatus;
import io.kubernetes.client.models.V1ObjectMeta;
import io.kubernetes.client.util.Watch;

import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
class KubeService {

    private ApiClient client = null ;
    private List<StatefulSetData> data = new ArrayList<>(); // Stores all the statefulset data retrieved from the Cluster
    private String resVersion = null ;
    public final String label = "org.eclipse.cbi.jiro/kind=master";
    private Watch<V1StatefulSet> watch ;

    public void setApiClient(ApiClient client) {
        this.client = client ;
    }


    public void watchStatefulSets() throws ApiException {
        System.out.println("WatchStatefulSets");
        client.getHttpClient().setReadTimeout(0,TimeUnit.SECONDS);
        Configuration.setDefaultApiClient(client);
        AppsV1Api apiInstance = new AppsV1Api();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                        System.out.println("Creating Watch ");
                        watch =  Watch.createWatch( client,
                        apiInstance.listStatefulSetForAllNamespacesCall(null,null,false,label,null,null,resVersion,null , true, null,null), // Tells the watch what to watch  
                        new TypeToken<Watch.Response<V1StatefulSet>>() {}.getType() ); // Response type we need the watch to return
                } catch(ApiException e) {
                     System.out.println(e.getResponseBody());
                }
                /*
                    The Response object returned by the watch contains items which have been Added,Deleted or Modified 
                    Basically the response object have -
                     type - Which Tells about the type of operation for the Object (StatefulSet) i.e.
                                1) Added
                                2) Deleted
                                3) Modified
                                4) Error
                    object - Which contains the object (StatefulSet)
                */
                for(Watch.Response<V1StatefulSet> item : watch) {
                   V1ObjectMeta itemMeta = item.object.getMetadata();
                   V1StatefulSetStatus itemStatus = item.object.getStatus();
                    switch(item.type) {
                        case "ADDED" : {
                            System.out.println("Added");
                            StatefulSetData obj = new StatefulSetData();
                            obj.setName(itemMeta.getAnnotations().get("org.eclipse.cbi.jiro/kubernetes.master.namespace"));
                            obj.setUid(itemMeta.getUid());
                            obj.setReplicas(itemStatus.getReplicas());
			    obj.setJenkinsVersion(itemMeta.getAnnotations().get("org.eclipse.cbi.jiro/jenkins.version"));
                            obj.setJenkinsActualVersion(itemMeta.getAnnotations().get("org.eclipse.cbi.jiro/jenkins.actualVersion"));
			    /* Whenever a new StatefulSet is Created it takes time for the pods
                               to be in ready  condition till that time this field remains null  */

                            try {
                                obj.setReadyReplicas(itemStatus.getReadyReplicas());
                            } catch (NullPointerException ex) {
                                obj.setReadyReplicas(0);
                            }
                            data.add(obj);
                        }
                        break;
                        case "MODIFIED" : {
                            System.out.println("Modified");
                            String uid = itemMeta.getUid();
                            Iterator<StatefulSetData> itr =  data.iterator();
                            StatefulSetData obj = null ;
                            while(itr.hasNext()) {
                                obj = itr.next();
                                if(obj.getUid() == uid)
                                break;
                            }
                            obj.setName(itemMeta.getAnnotations().get("org.eclipse.cbi.jiro/kubernetes.master.namespace"));
                            obj.setReplicas(itemStatus.getReplicas());
			    obj.setJenkinsVersion(itemMeta.getAnnotations().get(" org.eclipse.cbi.jiro/jenkins.version"));
			    obj.setJenkinsActualVersion(itemMeta.getAnnotations().get("org.eclipse.cbi.jiro/jenkins.actualVersion"));
                            try {
                                obj.setReadyReplicas(itemStatus.getReadyReplicas());
                            } catch (NullPointerException ex) {
                                obj.setReadyReplicas(0);
                            }
                        }
                        break;
                        case "DELETED" : {
                            System.out.println("Deleted");
                            String uid = itemMeta.getUid();
                            Iterator<StatefulSetData> itr =  data.iterator();
                            while(itr.hasNext()) {
                                if(itr.next().getUid().equals(uid))
                                    itr.remove();
                            }
                        }
                        break;
                    }
                }
            }
        }).start();
    }

    public List<StatefulSetData> getStatefulSetData() throws ApiException {
        return(data);
    }

    /* Searching the statefulset by name and returning its version*/

    public String getJenkinsVersion(String name) {
	StatefulSetData obj = null ;
	Iterator<StatefulSetData> itr =  data.iterator();
        while(itr.hasNext()) {
             obj = itr.next();
             if(obj.getName().equals(name))
		  break;
	}
	return(obj.getJenkinsVersion());
    }

}
