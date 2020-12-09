Das Projekt als Maven Projekt importieren und einmal bauen (z. B. `mvn package`) [Testfehler ignorieren!]. Die einzelnen Applikationen als Ausführungskonfiguration angeben.

Am besten erst den [Edge Server](edge-server/src/main/java/de/hska/vsmlab/microservice/edge/EdgeServerApplication.java) und die [Service Registry](service-registry/src/main/java/de/hska/vsmlab/microservice/registry/ServiceRegistryApplication.java) ausführen und anschließend die Microservices. 

* [Startseite](http://localhost:8088/index.html)  
* [Eureka](http://localhost:8088/eureka)  
* [Eureka Dashboard](http://localhost:8088/eureka-ui/)  
* [Zuul](http://localhost:8089/api)
* [Micro Service Endpunkt](http://localhost:8089/api/appservice)
* [Micro Service Funktionsendpunkt](http://localhost:8089/api/appservice/appname)
* [Hystrix Dashboard](http://localhost:8097/hystrix)
* [Turbine Stream ("Default" Cluster)](http://localhost:8097/turbine.stream?cluster=default)

Hystrix Endpunkte:
* [User Service](http://localhost:8759/actuator/hystrix.stream)
* [Role Service](http://localhost:8760/actuator/hystrix.stream)
* [Product Service](http://localhost:8091/actuator/hystrix.stream)
* [Category Service](http://localhost:8761/actuator/hystrix.stream)
* [Product Composite Service](http://localhost:8763/actuator/hystrix.stream)
* [Login System Composite Service](http://localhost:8762/actuator/hystrix.stream)

