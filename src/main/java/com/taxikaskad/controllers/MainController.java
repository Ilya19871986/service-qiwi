package com.taxikaskad.controllers;

import com.taxikaskad.response.BaseResponse;
import com.taxikaskad.response.QiwiResponse;
import com.taxikaskad.response.TmDriverDataResponse;
import com.taxikaskad.service.TmApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    @Autowired
    TmApiService tmApiService;

    @GetMapping("/ping")
    public BaseResponse ping()  {
        ResponseEntity<BaseResponse> baseResponseResponseEntity = tmApiService.ping();
        BaseResponse pingResponse = baseResponseResponseEntity.getBody();
        pingResponse.setHttpStatusCode(baseResponseResponseEntity.getStatusCode());
        return pingResponse;
    }

    @GetMapping("/checkDriverById")
    public TmDriverDataResponse checkDriverById(Integer id) {
        ResponseEntity<TmDriverDataResponse> tmDriverDataResponseResponseEntity = tmApiService.checkDriverById(id);
        TmDriverDataResponse tmDriverDataResponse = tmDriverDataResponseResponseEntity.getBody();
        tmDriverDataResponse.setHttpStatusCode(tmDriverDataResponseResponseEntity.getStatusCode());
        return tmDriverDataResponse;
    }

    @RequestMapping(value="/check", produces= MediaType.APPLICATION_XML_VALUE)
    public QiwiResponse check() {
        QiwiResponse response = new QiwiResponse();
        response.setResult(0);
        response.setOsmp_txn_id(134);
//        qiwiResponse.getComment("123");
//        qiwiResponse.getOsmp_txn_id(11);
        return response;
    }
}
