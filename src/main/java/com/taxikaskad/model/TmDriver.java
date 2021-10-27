package com.taxikaskad.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

@Data
public class TmDriver {

    @JsonAlias("driver_id")
    private int driverId;

    private String name;

    private double balance;

    private String birthday;

    @JsonAlias("car_id")
    private Integer carId;

    private String license;

    @JsonAlias("home_phone")
    private String homePhone;

    @JsonAlias("mobile_phone")
    private String mobilePhone;

    @JsonAlias("is_locked")
    private Boolean isLocked;

    @JsonAlias("is_dismissed")
    private Boolean isDismissed;
}
