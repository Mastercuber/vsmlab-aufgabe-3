package de.hska.vsmlab;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient("application-service")
public interface IApplicationController {

    @RequestMapping("/appname")
    String getApplicationName();

}
