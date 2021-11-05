package com.taxikaskad.controllers;

import com.taxikaskad.request.qiwi.QiwiOperationRequest;
import com.taxikaskad.request.qiwi.QiwiOperationType;
import com.taxikaskad.service.QiwiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.xml.bind.JAXBException;

@RestController
@RequestMapping("qiwi-service/v1")
@Slf4j
public class QiwiController {

    @Autowired
    QiwiService qiwiService;

    @GetMapping(value = "/operation", produces = MediaType.APPLICATION_XML_VALUE)
    public String qiwiOperation(@RequestParam(value = "command", required = true) QiwiOperationType command,
                                @RequestParam(value = "txn_id", required = true) Integer txnId,
                                @RequestParam(value = "txn_date", required = false) String txnDate,
                                @RequestParam(value = "account", required = true) Integer account,
                                @RequestParam(value = "sum", required = true) Double sum) throws JAXBException {

        QiwiOperationRequest request = new QiwiOperationRequest();
        request
                .setCommand(command)
                .setTxnId(txnId)
                .setTxnDate(txnDate)
                .setAccount(account)
                .setSum(sum);

        return qiwiService.processingQiwiOperationRequest(request);
    }
}
