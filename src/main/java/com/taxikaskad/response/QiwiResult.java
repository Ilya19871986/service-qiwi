package com.taxikaskad.response;

public enum QiwiResult {

    OK(0),
    UNKNOWN_ERROR (300),
    NOT_FOUND(5),
    NOT_ACTIVE(79),
    AMOUNT_SMALL(241),
    AMOUNT_LARGE(242);

    private int value;

    public int getResult() {
        return this.value;
    }

    QiwiResult(int value) { this.value = value; }

}
