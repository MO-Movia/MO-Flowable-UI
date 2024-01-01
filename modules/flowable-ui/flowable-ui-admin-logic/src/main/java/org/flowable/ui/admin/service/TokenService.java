package org.flowable.ui.admin.service;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class TokenService {
    @Value("${token.url}")
    private String tokenUrl;
    @Value("${token.username}")
    private String userName;
    @Value("${token.password}")
    private String password;
    @Value("${token.host}")
    private String host;
    @Value("${token.clientId}")
    private String clientId;
    public String getToken() {
        try {
            byte[] bytesEncoded = Base64.encodeBase64(clientId.getBytes());
            String base64Str = new String(bytesEncoded);

            RestTemplate restTemplate = new RestTemplate();

            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add("Content-Type", "application/x-www-form-urlencoded");
            httpHeaders.add("Authorization", "Basic " + base64Str);//dWktY2xpZW50Og==
            httpHeaders.add("Host", this.host);

            MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
            multiValueMap.add("grant_type", "password");
            multiValueMap.add("username", this.userName);
            multiValueMap.add("password", this.password);

            HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(multiValueMap, httpHeaders);

            ResponseEntity<ResponseObject> response = restTemplate.postForEntity(this.tokenUrl,
                    httpEntity, ResponseObject.class);

            ResponseObject responseObject = response.getBody();

            return responseObject.getAccess_token();
        }
        catch(Exception e) {
            return "";
        }
    }
}

class ResponseObject {
    private String access_token;
    private String token_type;
    private String session_state;
    private String scope;

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getToken_type() {
        return token_type;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }

    public String getSession_state() {
        return session_state;
    }

    public void setSession_state(String session_state) {
        this.session_state = session_state;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }
}