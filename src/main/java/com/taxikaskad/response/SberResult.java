package com.taxikaskad.response;

public enum SberResult {

    OK(0),
    UNKNOWN_ERROR (300),
    NOT_FOUND(3),
    NOT_ACTIVE(5),
    AMOUNT_SMALL(10),
    AMOUNT_LARGE(11);

    private int value;

    public int getResult() {
        return this.value;
    }

    SberResult(int value) { this.value = value; }
}
