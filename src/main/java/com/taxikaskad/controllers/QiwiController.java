package com.taxikaskad.controllers;

import com.taxikaskad.request.OperationRequest;
import com.taxikaskad.request.OperationType;
import com.taxikaskad.service.OperationService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("qiwi-service/v1")
@Slf4j
public class QiwiController {

    Logger logger = LoggerFactory.getLogger(QiwiController.class);

    @Autowired
    OperationService operationService;

    @GetMapping(value = "/operation", produces = MediaType.APPLICATION_XML_VALUE)
    public String qiwiOperation(@RequestParam(value = "command", required = true) OperationType command,
                                @RequestParam(value = "txn_id", required = true) String txnId,
                                @RequestParam(value = "txn_date", required = false) String txnDate,
                                @RequestParam(value = "account", required = true) Integer account,
                                @RequestParam(value = "sum", required = true) Double sum) {

        OperationRequest request = new OperationRequest();
        request
                .setCommand(command)
                .setTxnId(txnId)
                .setTxnDate(txnDate)
                .setAccount(account)
                .setSum(sum);

        logger.debug(request.toString());

        String response = operationService.processingQiwiOperationRequest(request);

        logger.debug(response);

        return response;
    }
}
