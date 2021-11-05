package com.taxikaskad.request.qiwi;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class QiwiOperationRequest {

    public final Double MIN_SUM = 1.00;
    public final Double MAX_SUM = 1000000.00;

    private QiwiOperationType command;

    private Integer txnId;

    private String txnDate;

    private Integer account;

    private Double sum;

}
