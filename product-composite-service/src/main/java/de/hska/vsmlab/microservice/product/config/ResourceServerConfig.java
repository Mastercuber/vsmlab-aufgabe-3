package de.hska.vsmlab.microservice.product.config;


import feign.RequestInterceptor;
import io.jmnarloch.spring.cloud.feign.OAuth2FeignRequestInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
    private String RESOURCE_ID = "product-composite-service";

    @Autowired
    private TokenStore tokenStore;

    @Override
    public void configure(ResourceServerSecurityConfigurer security) throws Exception {
        security
                .resourceId(RESOURCE_ID)
                .tokenStore(this.tokenStore);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        // @formatter:off
        http
            .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/product/find").access("#oauth2.hasScope('product-composite-service')")
                .antMatchers(HttpMethod.POST, "/product/add").access("#oauth2.hasScope('product-composite-service')")
                .antMatchers(HttpMethod.DELETE, "/category/delete/*").access("#oauth2.hasScope('product-composite-service')")
            .anyRequest().authenticated();
        // @formatter:on
    }

    @Bean
    public RequestInterceptor oauth2FeignRequestInterceptor(@Qualifier("oauth2ClientContext") OAuth2ClientContext oauth2ClientContext) {
        return new OAuth2FeignRequestInterceptor(oauth2ClientContext);
    }
}
