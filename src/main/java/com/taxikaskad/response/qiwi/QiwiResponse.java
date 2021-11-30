package com.taxikaskad.response.qiwi;

import com.taxikaskad.Utils.DoubleAdapter;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@Data
@Accessors(chain = true)
@XmlRootElement(name = "response")
@XmlAccessorType(XmlAccessType.FIELD)
public class QiwiResponse extends QiwiBaseResponse{

    @XmlElement(name = "prv_txn")
    private String prvTxn;

    @XmlElement
    @XmlJavaTypeAdapter(DoubleAdapter.class)
    private Double sum;
}
