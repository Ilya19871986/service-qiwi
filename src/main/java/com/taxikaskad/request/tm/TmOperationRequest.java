package com.taxikaskad.request.tm;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class TmOperationRequest {

    @JsonAlias("driver_id")
    private Integer driverId;

    @JsonAlias("oper_sum")
    private Double sum;

    @JsonAlias("oper_type")
    private TmOperationType type;

    private String name;

    public String toString() {
        return "{" +
                "\"driver_id\":" + this.getDriverId() +
                ",\"oper_sum\":" + this.getSum() +
                ",\"oper_type\":" + "\"" + this.getType() + "\"" +
                ",\"name\":" + "\"" + this.getName() + "\""
                +"}";
    }
}
