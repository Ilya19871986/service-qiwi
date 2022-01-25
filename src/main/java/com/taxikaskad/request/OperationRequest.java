package com.taxikaskad.request;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class OperationRequest {

    public final Double MIN_SUM = 10.00;
    public final Double MAX_SUM = 1000000.00;

    private OperationType command;

    private String txnId;

    private String txnDate;

    private Integer account;

    private Double sum;

}
