package com.taxikaskad.response;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class BaseResponse {

    private HttpStatus httpStatusCode;

    private int code;

    @JsonAlias("descr")
    private String description;
}
