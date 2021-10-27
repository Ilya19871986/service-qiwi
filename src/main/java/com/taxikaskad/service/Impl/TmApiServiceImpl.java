package com.taxikaskad.service.Impl;

import com.taxikaskad.response.BaseResponse;
import com.taxikaskad.response.TmDriverDataResponse;
import com.taxikaskad.service.TmApiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.logging.Logger;

import static com.taxikaskad.Utils.Md5;
import static com.taxikaskad.Utils.doTrustToCertificates;

@Service
@Slf4j
public class TmApiServiceImpl implements TmApiService {

    @Value("${baseUrl.serverUrl}")
    private String serverUrl;

    @Value("${baseUrl.pingUrl}")
    private String pingUrl;

    @Value("${baseUrl.checkDriverByIdUrl}")
    private String checkDriverByIdUrl;

    static  {
        try {
            doTrustToCertificates();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public ResponseEntity<BaseResponse> ping() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        HttpEntity entity = new HttpEntity(headers);
        ResponseEntity<BaseResponse> response = restTemplate.exchange(
                serverUrl + pingUrl, HttpMethod.GET, entity, BaseResponse.class);
        return response;
    }

    @Override
    public ResponseEntity<TmDriverDataResponse> checkDriverById(Integer driverId) {
        RestTemplate restTemplate = new RestTemplate();
        String params = "?driver_id=" + driverId;
        String signature = Md5(params.replace("?", "") + "openapi221");
        HttpHeaders headers = new HttpHeaders();
        headers.set("Signature", signature);
        HttpEntity entity = new HttpEntity(headers);
        ResponseEntity<TmDriverDataResponse> response = restTemplate.exchange(
                serverUrl + checkDriverByIdUrl + params, HttpMethod.GET, entity, TmDriverDataResponse.class);
        return response;
    }
}
