package de.hska.vsmlab.microservice;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient("application-service")
public interface IApplicationController {

    @RequestMapping("/appname")
    String getApplicationName();

}
