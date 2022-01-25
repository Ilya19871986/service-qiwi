package com.taxikaskad.controllers;

import com.taxikaskad.request.tm.TmOperationRequest;
import com.taxikaskad.response.tm.TmBaseResponse;
import com.taxikaskad.response.QiwiResponse;
import com.taxikaskad.response.tm.TmDriverDataResponse;
import com.taxikaskad.response.tm.TmPayResponse;
import com.taxikaskad.service.TmApiService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.bind.JAXBException;

import static com.taxikaskad.Utils.MainUtils.toXml;

@RestController
@Slf4j
public class MainController {

    Logger logger = LoggerFactory.getLogger(MainController.class);

    @Autowired
    TmApiService tmApiService;

    @GetMapping("/ping")
    public TmBaseResponse ping()  {
        ResponseEntity<TmBaseResponse> baseResponseResponseEntity = tmApiService.ping();
        TmBaseResponse pingResponse = baseResponseResponseEntity.getBody();
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
    public String check() throws JAXBException {
        QiwiResponse response = new QiwiResponse();
        response.setResult(0);
        response.setOsmpTxnId("1");
        response.setComment(null);
        response.setSum(12.13);
        response.setPrvTxn("12");
        return toXml(response, true);
    }

    @PostMapping(value = "/driverOperation", produces = MediaType.APPLICATION_JSON_VALUE)
    public TmPayResponse driverOperation(@RequestBody TmOperationRequest tmOperationRequest) {
        logger.debug("Операция зачисления:" + tmOperationRequest.toString());
        ResponseEntity<TmPayResponse> baseResponseResponseEntity = tmApiService.createDriverOperation(tmOperationRequest);
        TmPayResponse tmPayResponse = baseResponseResponseEntity.getBody();
        tmPayResponse.setHttpStatusCode(baseResponseResponseEntity.getStatusCode());
        logger.debug("Результат операции:" + tmPayResponse);
        return tmPayResponse;
    }
}
