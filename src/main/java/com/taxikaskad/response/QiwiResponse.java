package com.taxikaskad.response;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@Data
@XmlRootElement(name = "response")
@XmlType(propOrder = { "osmp_txn_id", "prvTxn", "sum", "result", "comment" })
public class QiwiResponse {

    private Integer osmp_txn_id;

    private Integer prvTxn;

    @XmlElement(name = "prv_txn")
    public Integer getPrvTxn() {
        return prvTxn;
    }

    private Double sum;

    private Integer result;

    private String comment;
}
