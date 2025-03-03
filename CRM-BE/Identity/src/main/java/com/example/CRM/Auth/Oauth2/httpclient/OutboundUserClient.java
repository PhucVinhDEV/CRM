package com.example.CRM.Auth.Oauth2.httpclient;

import com.example.CRM.Auth.Oauth2.Model.OutboundUserWithTokenReponese;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Component
@FeignClient(name = "outbound-user-client", url = "https://www.googleapis.com")
public interface OutboundUserClient {
    @GetMapping(value = "/oauth2/v1/userinfo")
    OutboundUserWithTokenReponese GetUserInfo(@RequestParam("alt") String code,
                                              @RequestParam("access_token") String accessToken
    );
}
