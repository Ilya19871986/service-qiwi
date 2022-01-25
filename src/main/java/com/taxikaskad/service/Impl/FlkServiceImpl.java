package com.taxikaskad.service.Impl;

import com.taxikaskad.request.OperationRequest;
import com.taxikaskad.response.QiwiResult;
import com.taxikaskad.response.SberResult;
import com.taxikaskad.response.tm.TmDriverDataResponse;
import com.taxikaskad.service.FlkService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class FlkServiceImpl implements FlkService {

    @Override
    public QiwiResult flkPayOperationQiwi(OperationRequest request) {

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
    public QiwiResult flkCheckOperationQiwi(TmDriverDataResponse response) {

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

    @Override
    public SberResult flkPayOperationSber(OperationRequest request) {

        if (request.getSum() == null) {
            return SberResult.UNKNOWN_ERROR;
        }

        if (request.getSum() < request.MIN_SUM) {
            return SberResult.AMOUNT_SMALL;
        }

        if (request.getSum() > request.MAX_SUM) {
            return SberResult.AMOUNT_LARGE;
        }

        return SberResult.OK;
    }

    @Override
    public SberResult flkCheckOperationSber(TmDriverDataResponse response) {

        if (response.getHttpStatusCode() != HttpStatus.OK) {
            return SberResult.UNKNOWN_ERROR;
        }

        // найден и активен
        if (response.getCode() == 0 && !response.getData().getIsDismissed() && !response.getData().getIsLocked()) {
            return SberResult.OK;
        }

        // не найден
        if (response.getCode() == 100) {
            return SberResult.NOT_FOUND;
        }

        // найден и заблокирован или уволен
        if (response.getCode() == 0 && (response.getData().getIsLocked() || response.getData().getIsDismissed())) {
            return SberResult.NOT_ACTIVE;
        }

        return SberResult.UNKNOWN_ERROR;
    }
}
