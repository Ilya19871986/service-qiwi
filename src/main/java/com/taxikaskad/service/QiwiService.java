package com.taxikaskad.service;

import com.taxikaskad.request.qiwi.QiwiOperationRequest;

import javax.xml.bind.JAXBException;

public interface QiwiService {

    String processingQiwiOperationRequest(QiwiOperationRequest request);

}
