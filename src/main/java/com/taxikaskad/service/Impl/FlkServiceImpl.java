package com.taxikaskad.service.Impl;

import com.taxikaskad.request.qiwi.QiwiOperationRequest;
import com.taxikaskad.response.qiwi.QiwiResult;
import com.taxikaskad.response.tm.TmDriverDataResponse;
import com.taxikaskad.service.FlkService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class FlkServiceImpl implements FlkService {

    @Override
    public QiwiResult flkPayOperation(QiwiOperationRequest request) {

        if (request.getSum() == null) {
            return QiwiResult.UNKNOWN_ERROR;
        }

        if (request.getSum() < request.MIN_SUM) {
              return QiwiResult.AMOUNT_SMALL;
        }

        if (request.getSum() > request.MAX_SUM) {
            return QiwiResult.AMOUNT_LARGE;
        }

        return QiwiResult.OK;
    }

    @Override
    public QiwiResult flkCheckOperation(TmDriverDataResponse response) {

        if (response.getHttpStatusCode() != HttpStatus.OK) {
            return QiwiResult.UNKNOWN_ERROR;
        }

        // найден и активен
        if (response.getCode() == 0 && !response.getData().getIsDismissed() && !response.getData().getIsLocked()) {
            return QiwiResult.OK;
        }

        // не найден
        if (response.getCode() == 100) {
            return QiwiResult.NOT_FOUND;
        }

        // найден и заблокирован или уволен
        if (response.getCode() == 0 && (response.getData().getIsLocked() || response.getData().getIsDismissed())) {
            return QiwiResult.NOT_ACTIVE;
        }

        return QiwiResult.UNKNOWN_ERROR;
    }
}
