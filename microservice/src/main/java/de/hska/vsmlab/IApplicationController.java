package de.hska.vsmlab;

import org.springframework.web.bind.annotation.RequestMapping;

public interface IApplicationController {

    @RequestMapping("/appname")
    String getApplicationName();

}
