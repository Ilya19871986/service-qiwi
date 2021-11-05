package com.taxikaskad.service;

import com.taxikaskad.request.qiwi.QiwiOperationRequest;
import com.taxikaskad.response.qiwi.QiwiResponse;
import com.taxikaskad.response.qiwi.QiwiResult;
import com.taxikaskad.response.tm.TmDriverDataResponse;

public interface FlkService {

   QiwiResult flkPayOperation(QiwiOperationRequest request);

   QiwiResult flkCheckOperation(TmDriverDataResponse response);

}
