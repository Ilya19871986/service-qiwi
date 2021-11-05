package com.taxikaskad.response.tm;

import com.taxikaskad.model.TmDriver;
import lombok.Data;

@Data
public class TmDriverDataResponse extends TmBaseResponse {

    private TmDriver data;

}
