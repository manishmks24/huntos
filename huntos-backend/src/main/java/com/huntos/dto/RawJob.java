package com.huntos.dto;

import lombok.Data;

@Data
public class RawJob {
    private String title;
    private String company;
    private String location;
    private String salary;
    private String applyUrl;
    private String description;
    private String platform;
}
