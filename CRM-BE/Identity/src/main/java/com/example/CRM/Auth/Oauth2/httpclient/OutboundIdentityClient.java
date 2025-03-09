package com.example.CRM.Auth.Oauth2.httpclient;

import com.example.CRM.Auth.Oauth2.Model.ExchangeTokenRequest;
import com.example.CRM.Auth.Oauth2.Model.ExchangeTokenResponese;
import com.example.CRM.common.config.FeignConfig;
import feign.QueryMap;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Component
@FeignClient(name = "outbound-identity", url = "https://oauth2.googleapis.com", configuration = FeignConfig.class)
public interface OutboundIdentityClient {
    @PostMapping(value = "/token", produces = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    ExchangeTokenResponese exchangeToken(@QueryMap ExchangeTokenRequest request);

    @PostMapping(value = "/token",
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    ExchangeTokenResponese exchangeToken2(
            @RequestParam("code") String code,
            @RequestParam("client_id") String clientId,
            @RequestParam("client_secret") String clientSecret,
            @RequestParam("redirect_uri") String redirectUri,
            @RequestParam("grant_type") String grantType,
            @RequestParam(value = "code_verifier", required = false) String codeVerifier
    );
}

