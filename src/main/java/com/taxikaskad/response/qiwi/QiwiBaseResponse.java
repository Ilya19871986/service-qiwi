package com.taxikaskad.response.qiwi;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.xml.bind.annotation.*;

@Data
@Accessors(chain = true)
@XmlAccessorType(XmlAccessType.FIELD)
@XmlTransient
public class QiwiBaseResponse {

    @XmlElement(name = "osmp_txn_id")
    private Integer osmpTxnId;

    @XmlElement
    private Integer result;

    @XmlElement
    private QiwiResult comment;

}
