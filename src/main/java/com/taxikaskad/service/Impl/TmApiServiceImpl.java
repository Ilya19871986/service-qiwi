package com.taxikaskad.service.Impl;

import com.taxikaskad.request.tm.TmOperationRequest;
import com.taxikaskad.response.tm.TmBaseResponse;
import com.taxikaskad.response.tm.TmDriverDataResponse;
import com.taxikaskad.response.tm.TmPayResponse;
import com.taxikaskad.service.TmApiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import static com.taxikaskad.Utils.MainUtils.Md5;
import static com.taxikaskad.Utils.MainUtils.doTrustToCertificates;

@Service
@Slf4j
public class TmApiServiceImpl implements TmApiService {

    @Value("${baseUrl.serverUrl}")
    private String serverUrl;

    @Value("${baseUrl.pingUrl}")
    private String pingUrl;

    @Value("${baseUrl.checkDriverByIdUrl}")
    private String checkDriverByIdUrl;

    @Value("${baseUrl.createDriverOperationUrl}")
    private String createDriverOperationUrl;

    static  {
        try {
            doTrustToCertificates();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public ResponseEntity<TmBaseResponse> ping() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        HttpEntity entity = new HttpEntity(headers);
        ResponseEntity<TmBaseResponse> response = restTemplate.exchange(
                serverUrl + pingUrl, HttpMethod.GET, entity, TmBaseResponse.class);
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

    @Override
    public ResponseEntity<TmPayResponse> createDriverOperation(TmOperationRequest tmOperationRequest) {
        RestTemplate restTemplate = new RestTemplate();
        String signature = Md5(tmOperationRequest.toString() + "openapi221");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setContentLength(tmOperationRequest.toString().length());
        headers.set("Signature", signature);
        HttpEntity entity = new HttpEntity(tmOperationRequest.toString(), headers);
        ResponseEntity<TmPayResponse> response = restTemplate.exchange(
                serverUrl + createDriverOperationUrl,
                HttpMethod.POST,
                entity,
                TmPayResponse.class);
        return response;
    }
}
