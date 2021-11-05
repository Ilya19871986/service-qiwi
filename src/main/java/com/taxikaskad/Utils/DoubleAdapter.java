package com.taxikaskad.Utils;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class DoubleAdapter extends XmlAdapter<String, Double> {

    @Override
    public Double unmarshal(String v) {
        if (v == null || v.isEmpty() || v.equals("null")) {
            return null;
        }
        return Double.parseDouble(v);
    }

    @Override
    public String marshal(Double v) {
        if (v == null) {
            return null;
        }
        return String.format("%.2f", v).replace(',', '.');
    }
}