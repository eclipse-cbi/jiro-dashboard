package org.eclipse.cbi;

public class StatefulSetData {
    private String name ;
    private String uid ;
    private int replicas ; // Number of pods as defined in the Spec
    private int readyReplicas ; // Number of pods in running condition
    private String jenkinsVersion ; // org.eclipse.cbi.jiro/jenkins.version:
    private String jenkinsActualVersion ; //org.eclipse.cbi.jiro/jenkins.actualVersion
    // Structure for storing the information about the statefulsets retrieved from Watch

    public String getUid() {
        return(uid);
    }

    public String getName() {
	return(name);
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
    public void setJenkinsVersion(String jenkinsVersion) {
        this.jenkinsVersion = jenkinsVersion ;
    }
    public void setJenkinsActualVersion(String jenkinsActualVersion) {
        this.jenkinsActualVersion = jenkinsActualVersion ;
    }

    public String getJenkinsVersion() {
        String version = jenkinsActualVersion + ":" + jenkinsVersion ;
        return(version);
    }

    public String toString() {
        return(name + "," + uid + "," + readyReplicas+ "/"+ replicas);
    }
}
