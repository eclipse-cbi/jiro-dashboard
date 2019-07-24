## Dashboard API

  The API can be accessed at http://35.232.118.38:8080
  
  __Enpoints__
   * /jenkins  - Displays the data in format [name , id , total replicas/ready replicas]
   * /jenkins/name/ - Display the jenkins version
   
## Cluster Overview 

 The cluster can be accessed at :
 
 https://console.cloud.google.com/kubernetes/workload?project=mystical-magnet-238515&workload_list_tablesize=50

  ![k8_arch.png](https://drive.google.com/uc?export=download&id=1jF9i8Bk5sCr5ZBTt0OOfnUzr9swcdvDv)
  
  __Jiro-dashboard__ is used for working and testing the api inside the cluster whereas __Jiro-dashboard-run__ is running the containerized app 


1. [__Jenkins API Client__](https://github.com/jenkinsci/java-client-api)
2. [__Kubernetes API CLient__](https://github.com/kubernetes-client/java)
  
