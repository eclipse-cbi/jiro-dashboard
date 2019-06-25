package org.eclipse.cbi;

import com.google.gson.reflect.TypeToken;
import io.kubernetes.client.ApiClient;
import io.kubernetes.client.ApiException;
import io.kubernetes.client.Configuration;
import io.kubernetes.client.apis.AppsV1Api;
import io.kubernetes.client.models.V1StatefulSet;
import io.kubernetes.client.models.V1StatefulSetStatus;
import io.kubernetes.client.models.V1ObjectMeta;
import io.kubernetes.client.util.Watch;

import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

class KubeService {
   
    private ApiClient client = null ;
    private static List<StatefulSetData> data = new ArrayList<>(); 
    private static String resVersion = null ; 
    private Boolean watching = false ;
    public static final String label = "org.eclipse.cbi.jiro/kind=master";
    Watch<V1StatefulSet> watch ;

    public void setApiClient(ApiClient client) {
        this.client = client ;
    }

    public void watchStatefulSets() throws ApiException {
        System.out.println("WatchStatefulSets");
        watching = true ;
        client.getHttpClient().setReadTimeout(0,TimeUnit.SECONDS);
        Configuration.setDefaultApiClient(client);
        AppsV1Api apiInstance = new AppsV1Api(); 

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                        System.out.println("Creating Watch ");
                        watch =  Watch.createWatch( client,
                        apiInstance.listStatefulSetForAllNamespacesCall(null,null,false,label,null,null,resVersion,null , true, null,null),
                        new TypeToken<Watch.Response<V1StatefulSet>>() {}.getType());
                } catch(ApiException e) {
                     System.out.println(e); 
                }
                for(Watch.Response<V1StatefulSet> item : watch) {
                   V1ObjectMeta itemMeta = item.object.getMetadata();
                   V1StatefulSetStatus itemStatus = item.object.getStatus();
                    switch(item.type) {
                        case "ADDED" : {
                            System.out.println("Added");
                            StatefulSetData obj = new StatefulSetData();
                            obj.setName(itemMeta.getName());
                            obj.setUid(itemMeta.getUid());
                            obj.setReplicas(itemStatus.getReplicas());
                            obj.setReadyReplicas(itemStatus.getReadyReplicas());
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
                            obj.setName(itemMeta.getName());
                            obj.setReplicas(itemStatus.getReplicas());
                            obj.setReadyReplicas(itemStatus.getReadyReplicas());
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
}