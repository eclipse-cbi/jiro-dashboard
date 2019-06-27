## Using the API

  Create a Minikube Cluster or start an exisiting one using 
  ~~~
  Minikube Start
  ~~~
  Setup a proxy at port 8090 using kubectl
  ~~~
  Kubectl proxy --port=8090
  ~~~
  Start the backend server 
  ~~~
 ./mvnw compile quarkus:dev
  ~~~
  The jenkins endpoint can be accessed at http://localhost:8080/jenkins

## Setting up Minikube
  Everything below has been taken from this setup guide https://kubernetes.io/docs/tasks/tools/install-minikube/
  ### Install Kubectl
  Download the latest release 
  ~~~~
  curl -LO https://storage.googleapis.com/kubernetes-release/release/$(curl -s https://storage.googleapis.com/kubernetes-release/release/stable.txt)/bin/linux/amd64/kubectl
  ~~~~
  Make the kubectl binary executable
  ~~~~
  chmod +x ./kubectl
  ~~~~
  Move the binary in to your PATH
  ~~~~
  sudo mv ./kubectl /usr/local/bin/kubectl
  ~~~~ 
  ### Install Hypervisor
  If you do not already have a hypervisor installed, install one of these now
  - [KVM](https://www.linux-kvm.org/page/Main_Page), which also uses QEMU
  - [Virtualbox](https://www.virtualbox.org/wiki/Downloads)
  ### Install Minikube
  - Install Minikube using a package
    https://github.com/kubernetes/minikube/releases
    
  - Install Minikube via direct download
    ~~~~
    curl -Lo minikube https://storage.googleapis.com/minikube/releases/latest/minikube-linux-amd64 \
    && chmod +x minikube
    ~~~~
    
  
 
