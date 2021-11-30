package com.taxikaskad.response.tm;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

@Data
public class TmDataResponse {

    @JsonAlias("oper_id")
    String operId;
}
