package com.taxikaskad.service.Impl;

import com.taxikaskad.request.qiwi.QiwiOperationRequest;
import com.taxikaskad.request.tm.TmOperationRequest;
import com.taxikaskad.request.tm.TmOperationType;
import com.taxikaskad.response.qiwi.QiwiBaseResponse;
import com.taxikaskad.response.qiwi.QiwiResponse;
import com.taxikaskad.response.qiwi.QiwiResult;
import com.taxikaskad.response.tm.TmBaseResponse;
import com.taxikaskad.response.tm.TmDriverDataResponse;
import com.taxikaskad.response.tm.TmPayResponse;
import com.taxikaskad.service.FlkService;
import com.taxikaskad.service.QiwiService;
import com.taxikaskad.service.TmApiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import static com.taxikaskad.Utils.toXml;

@Service
@Slf4j
public class QiwiServiceImpl implements QiwiService {

    @Autowired
    TmApiService tmApiService;

    @Autowired
    FlkService flkService;

    @Value("${commission}")
    private Double commission;

    private String checkOperation(QiwiOperationRequest request) {

        try {

            QiwiResponse response = new QiwiResponse();
            response.setOsmpTxnId(request.getTxnId());

            ResponseEntity<TmDriverDataResponse> tmDriverDataResponseResponseEntity = tmApiService.checkDriverById(request.getAccount());
            TmDriverDataResponse tmDriverDataResponse = tmDriverDataResponseResponseEntity.getBody();
            tmDriverDataResponse.setHttpStatusCode(tmDriverDataResponseResponseEntity.getStatusCode());

            QiwiResult checkResult = flkService.flkCheckOperation(tmDriverDataResponse);

            response.setResult(checkResult.getResult()).setComment(checkResult);

            return toXml(response, true);

        } catch (Exception ex) {
            log.error("checkOperation: " + ex.getMessage());
            return QiwiResult.UNKNOWN_ERROR.toString();
        }
    }

    private String payOperation(QiwiOperationRequest request) {

        try {

            QiwiResponse response = new QiwiResponse();
            response.setSum(request.getSum()).setOsmpTxnId(request.getTxnId());

            QiwiResult checkResult = flkService.flkPayOperation(request);

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
                        .setPrvTxn(0)
                        .setResult(checkResult.getResult())
                        .setComment(checkResult);
            }

            return toXml(response, true);

        } catch (Exception ex) {
            log.error("checkOperation: " + ex.getMessage());
            return QiwiResult.UNKNOWN_ERROR.toString();
        }
    }

    @Override
    public String processingQiwiOperationRequest(QiwiOperationRequest request) {
        switch (request.getCommand()) {

            case pay: {
                return payOperation(request);
            }
            case check: {
                return checkOperation(request);
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
