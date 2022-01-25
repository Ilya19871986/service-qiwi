package com.taxikaskad.service.Impl;

import com.taxikaskad.request.OperationRequest;
import com.taxikaskad.request.tm.TmOperationRequest;
import com.taxikaskad.request.tm.TmOperationType;
import com.taxikaskad.response.QiwiResponse;
import com.taxikaskad.response.QiwiResult;
import com.taxikaskad.response.SberResponse;
import com.taxikaskad.response.SberResult;
import com.taxikaskad.response.tm.TmDriverDataResponse;
import com.taxikaskad.response.tm.TmPayResponse;
import com.taxikaskad.service.FlkService;
import com.taxikaskad.service.OperationService;
import com.taxikaskad.service.TmApiService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import static com.taxikaskad.Utils.MainUtils.toXml;

@Service
@Slf4j
public class OperationServiceImpl implements OperationService {

    Logger logger = LoggerFactory.getLogger(OperationServiceImpl.class);

    @Autowired
    TmApiService tmApiService;

    @Autowired
    FlkService flkService;

    @Value("${commission}")
    private Double commission;

    private String checkOperationQiwi(OperationRequest request) {

        try {
            QiwiResponse response = new QiwiResponse();
            response.setOsmpTxnId(request.getTxnId());

            ResponseEntity<TmDriverDataResponse> tmDriverDataResponseResponseEntity = tmApiService.checkDriverById(request.getAccount());
            TmDriverDataResponse tmDriverDataResponse = tmDriverDataResponseResponseEntity.getBody();
            tmDriverDataResponse.setHttpStatusCode(tmDriverDataResponseResponseEntity.getStatusCode());

            QiwiResult checkResult = flkService.flkCheckOperationQiwi(tmDriverDataResponse);

            response.setResult(checkResult.getResult()).setComment(checkResult);

            return toXml(response, true);

        } catch (Exception ex) {
            logger.error(ex.getMessage());
            return QiwiResult.UNKNOWN_ERROR.toString();
        }
    }

    private String checkOperationSber(OperationRequest request) {

        try {
            SberResponse response = new SberResponse();

            ResponseEntity<TmDriverDataResponse> tmDriverDataResponseResponseEntity = tmApiService.checkDriverById(request.getAccount());
            TmDriverDataResponse tmDriverDataResponse = tmDriverDataResponseResponseEntity.getBody();
            tmDriverDataResponse.setHttpStatusCode(tmDriverDataResponseResponseEntity.getStatusCode());

            SberResult checkResult = flkService.flkCheckOperationSber(tmDriverDataResponse);

            response.setResult(checkResult.getResult()).setComment(checkResult);

            return toXml(response, true);

        } catch (Exception ex) {
            logger.error(ex.getMessage());
            return SberResult.UNKNOWN_ERROR.toString();
        }
    }

    private String payOperationQiwi(OperationRequest request) {

        try {
            QiwiResponse response = new QiwiResponse();
            response.setSum(request.getSum()).setOsmpTxnId(request.getTxnId());

            QiwiResult checkResult = flkService.flkPayOperationQiwi(request);

            if (checkResult == QiwiResult.OK) {
                TmOperationRequest tmOperationRequest = new TmOperationRequest();
                tmOperationRequest
                        .setDriverId(request.getAccount())
                        .setSum(commission(request.getSum()))
                        .setType(TmOperationType.receipt)
                        .setName("service-qiwi");

                ResponseEntity<TmPayResponse> tmPayResponseResponseEntity = tmApiService.createDriverOperation(tmOperationRequest);
                TmPayResponse tmPayResponse = tmPayResponseResponseEntity.getBody();
                tmPayResponse.setHttpStatusCode(tmPayResponseResponseEntity.getStatusCode());

                response
                        .setPrvTxn(tmPayResponse.getData().getOperId())
                        .setResult(checkResult.getResult())
                        .setComment(checkResult);
            } else {
                response
                        .setPrvTxn("0")
                        .setResult(checkResult.getResult())
                        .setComment(checkResult);
            }

            return toXml(response, true);

        } catch (Exception ex) {
            logger.error(ex.getMessage());
            return QiwiResult.UNKNOWN_ERROR.toString();
        }
    }

    private String payOperationSber(OperationRequest request) {

        try {
            SberResponse response = new SberResponse();
            response.setSum(request.getSum());

            SberResult checkResult = flkService.flkPayOperationSber(request);

            if (checkResult == SberResult.OK) {
                TmOperationRequest tmOperationRequest = new TmOperationRequest();
                tmOperationRequest
                        .setDriverId(request.getAccount())
                        .setSum(commission(request.getSum()))
                        .setType(TmOperationType.receipt)
                        .setName("service-sber");

                ResponseEntity<TmPayResponse> tmPayResponseResponseEntity = tmApiService.createDriverOperation(tmOperationRequest);
                TmPayResponse tmPayResponse = tmPayResponseResponseEntity.getBody();
                tmPayResponse.setHttpStatusCode(tmPayResponseResponseEntity.getStatusCode());

                response
                        .setExtId(tmPayResponse.getData().getOperId())
                        .setResult(checkResult.getResult())
                        .setComment(checkResult);
            } else {
                response
                        .setExtId("0")
                        .setResult(checkResult.getResult())
                        .setComment(checkResult);
            }

            return toXml(response, true);

        } catch (Exception ex) {
            logger.error(ex.getMessage());
            return SberResult.UNKNOWN_ERROR.toString();
        }
    }

    @Override
    public String processingQiwiOperationRequest(OperationRequest request) {

        switch (request.getCommand()) {
            case pay: {
                return payOperationQiwi(request);
            }
            case check: {
                return checkOperationQiwi(request);
            }
            default: {
                return "not found";
            }
        }
    }

    @Override
    public String processingSberOperationRequest(OperationRequest request) {

        switch (request.getCommand()) {
            case pay: {
                return payOperationSber(request);
            }
            case check: {
                return checkOperationSber(request);
            }
            default: {
                return "not found";
            }
        }
    }

    private Double commission(Double sum) {
        return sum - (sum/100 * commission);
    }
}
