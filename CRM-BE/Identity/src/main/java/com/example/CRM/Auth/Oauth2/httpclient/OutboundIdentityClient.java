package com.example.CRM.Auth.Oauth2.httpclient;

import com.example.CRM.Auth.Oauth2.Model.ExchangeTokenRequest;
import com.example.CRM.Auth.Oauth2.Model.ExchangeTokenResponese;
import feign.QueryMap;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;

@Component
@FeignClient(name = "outbound-identity", url = "https://oauth2.googleapis.com")
public interface OutboundIdentityClient {
    @PostMapping(value = "/token", produces = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    ExchangeTokenResponese exchangeToken(@QueryMap ExchangeTokenRequest request);
}

