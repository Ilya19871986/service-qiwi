package com.taxikaskad.service;

import com.taxikaskad.request.OperationRequest;
import com.taxikaskad.response.QiwiResult;
import com.taxikaskad.response.SberResult;
import com.taxikaskad.response.tm.TmDriverDataResponse;

public interface FlkService {

   QiwiResult flkPayOperationQiwi(OperationRequest request);

   QiwiResult flkCheckOperationQiwi(TmDriverDataResponse response);

   SberResult flkPayOperationSber(OperationRequest request);

   SberResult flkCheckOperationSber(TmDriverDataResponse response);

}
