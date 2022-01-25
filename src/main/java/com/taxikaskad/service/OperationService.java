package com.taxikaskad.service;

import com.taxikaskad.request.OperationRequest;

public interface OperationService {

    String processingQiwiOperationRequest(OperationRequest request);

    String processingSberOperationRequest(OperationRequest request);

}
