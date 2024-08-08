package com.geeke.accumulation.dto;

import com.geeke.accumulation.entity.DefaultPayConfig;

public class DefaultPayConfigDto extends DefaultPayConfig {
    private String cityName;

    private String socialAvgAmount;

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getSocialAvgAmount() {
        return socialAvgAmount;
    }

    public void setSocialAvgAmount(String socialAvgAmount) {
        this.socialAvgAmount = socialAvgAmount;
    }
}
