package com.taxikaskad.response;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.xml.bind.annotation.*;

@Data
@Accessors(chain = true)
@XmlAccessorType(XmlAccessType.FIELD)
@XmlTransient
public class QiwiBaseResponse {

    @XmlElement(name = "osmp_txn_id")
    private String osmpTxnId;

    @XmlElement
    private Integer result;

    @XmlElement
    private QiwiResult comment;

}
