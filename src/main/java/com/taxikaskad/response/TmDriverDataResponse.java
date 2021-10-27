package com.taxikaskad.response;

import com.taxikaskad.model.TmDriver;
import lombok.Data;

@Data
public class TmDriverDataResponse extends BaseResponse {

    private TmDriver data;

}
