package de.hska.vsmlab.microservice.product.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.stereotype.Component;

/**
 * Der OAuth2 Interceptor extrahiert das Bearer Token aus dem SecurityContext und setzt es
 * als Wert im Authorization Header in jeder Anfrage vom aktuellen Dienst zu weiteren Diensten
 * über die Feign Client Bibliothek
 */
@Component
public class OAuth2FeignRequestInterceptor implements RequestInterceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(OAuth2FeignRequestInterceptor.class);

    /**
     * Name des Authorization Headers.
     */
    private static final String AUTHORIZATION_HEADER = "Authorization";

    /**
     * Typ des Tokens
     */
    private static final String BEARER_TOKEN_TYPE = "Bearer";


    /**
     * {@inheritDoc}
     */
    @Override
    public void apply(RequestTemplate template) {

        if (template.headers().containsKey(AUTHORIZATION_HEADER)) {
            LOGGER.warn("The Authorization token has been already set");
        } else if (SecurityContextHolder.getContext().getAuthentication() == null) {
            LOGGER.warn("Can not obtain existing token for request, if it is a non secured request, ignore.");
        } else {
            LOGGER.debug("Constructing Header {} for Token Type {}", AUTHORIZATION_HEADER, BEARER_TOKEN_TYPE);
            template.header(AUTHORIZATION_HEADER, String.format("%s %s",
                    ((OAuth2AuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getDetails()).getTokenType(),
                    ((OAuth2AuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getDetails()).getTokenValue())
            );
        }
    }
}

