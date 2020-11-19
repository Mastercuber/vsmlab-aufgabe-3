Das Projekt als Maven Projekt importieren und einmal bauen (z. B. `mvn package`) [Testfehler ignorieren!]. Die einzelnen Applikationen als Ausführungskonfiguration angeben.

Am besten erst den [Edge Server](edge-server/src/main/java/de/hska/vsmlab/EdgeServerApplication.java) und die [Service Registry](service-registry/src/main/java/de/hska/vsmlab/ServiceRegistryApplication.java) ausführen und anschließend den [Microservice](microservice/src/main/java/de/hska/vsmlab/MicroServiceApplication.java). 

* [Startseite](http://localhost:8088/index.html)  
* [Eureka](http://localhost:8088/eureka)  
* [Eureka Dashboard](http://localhost:8088/eureka-ui/)  
* [Zuul](http://localhost:8089/api)
* [Micro Service Endpunkt](http://localhost:8089/api/appservice)
* [Micro Service Funktionsendpunkt](http://localhost:8089/api/appservice/appname)
