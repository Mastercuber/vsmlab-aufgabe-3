package de.hska.vsmlab.microservice.product.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;

import java.util.Arrays;

@Configuration
@EnableOAuth2Client
public class Oauth2Config  {

    @Bean
    public OAuth2RestTemplate oAuth2RestTemplate (OAuth2ClientContext context){
        return new OAuth2RestTemplate(resourceDetails(), context);
    }

    @Bean
    public OAuth2ProtectedResourceDetails resourceDetails(){
        AuthorizationCodeResourceDetails details =  new AuthorizationCodeResourceDetails();
        details.setClientId("my-trusted-client");
        details.setClientSecret("secret");
        details.setGrantType("authorization_code");
        details.setScope(Arrays.asList("read", "write"));
        details.setAccessTokenUri("http://localhost:8762/oauth/token");
        details.setUserAuthorizationUri("http://localhost:8762/oauth/authorize");
        return details;
    }

}
