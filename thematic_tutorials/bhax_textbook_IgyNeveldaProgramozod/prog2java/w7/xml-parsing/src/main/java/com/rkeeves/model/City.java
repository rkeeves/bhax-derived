package com.rkeeves.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class City {

    private String x;

    private String y;

    private String stateCode;
}
