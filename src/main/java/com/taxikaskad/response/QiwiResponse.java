package com.taxikaskad.response;

import lombok.Data;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Data
@XmlRootElement(name = "response")
public class QiwiResponse {

    @XmlElement(name = "ert")
    private Integer osmp_txn_id;

    @XmlElement
    private Integer prvTxn;

    @XmlElement
    private Double sum;

    @XmlElement
    private Integer result;

    @XmlElement
    private String comment;
}
