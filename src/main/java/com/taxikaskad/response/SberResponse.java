package com.taxikaskad.response;

import com.taxikaskad.Utils.DoubleAdapter;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@Data
@Accessors(chain = true)
@XmlRootElement(name = "response")
@XmlAccessorType(XmlAccessType.FIELD)
public class SberResponse extends SberBaseResponse {

    @XmlElement(name = "ext_id")
    private String extId;

    @XmlElement
    @XmlJavaTypeAdapter(DoubleAdapter.class)
    private Double sum;
}
