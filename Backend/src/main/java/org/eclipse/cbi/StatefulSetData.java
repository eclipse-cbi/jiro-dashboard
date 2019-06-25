package org.eclipse.cbi;

public class StatefulSetData {
    private String name ;
    private String uid ;
    private int replicas ;
    private int readyReplicas ;
    
    public String getName() {
        return(name);
    }
    public String getUid() {
        return(uid);    
    }
    public String getReplicas() {
        return(name);
    }
    public String getReadyReplicas() {
        return(uid);    
    }

    public void setName(String name) {
        this.name = name ;
    }
    public void setUid(String uid) {
        this.uid = uid ;
    }
    public void setReplicas(int replicas) {
        this.replicas = replicas ;
    }
    public void setReadyReplicas(int readyReplicas) {
        this.readyReplicas = readyReplicas ;
    }

    public String toString() {
        return(name + "," + uid + "," + readyReplicas+ "/"+ replicas);
    }
}