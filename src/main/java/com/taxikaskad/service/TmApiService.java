package com.taxikaskad.service;

import com.taxikaskad.response.BaseResponse;
import com.taxikaskad.response.TmDriverDataResponse;
import org.springframework.http.ResponseEntity;

public interface TmApiService {

    ResponseEntity<BaseResponse> ping();

    ResponseEntity<TmDriverDataResponse> checkDriverById(Integer driverId);
}
