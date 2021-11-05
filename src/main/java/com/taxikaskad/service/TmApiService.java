package com.taxikaskad.service;

import com.taxikaskad.request.tm.TmOperationRequest;
import com.taxikaskad.response.tm.TmBaseResponse;
import com.taxikaskad.response.tm.TmDriverDataResponse;
import com.taxikaskad.response.tm.TmPayResponse;
import org.springframework.http.ResponseEntity;

public interface TmApiService {

    ResponseEntity<TmBaseResponse> ping();

    ResponseEntity<TmDriverDataResponse> checkDriverById(Integer driverId);

    ResponseEntity<TmPayResponse> createDriverOperation(TmOperationRequest tmOperationRequest);
}
