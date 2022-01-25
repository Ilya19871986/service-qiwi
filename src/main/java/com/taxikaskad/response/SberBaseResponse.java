package com.taxikaskad.response;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

@Data
@Accessors(chain = true)
@XmlAccessorType(XmlAccessType.FIELD)
@XmlTransient
public class SberBaseResponse {

    @XmlElement
    private Integer result;

    @XmlElement
    private SberResult comment;
}
