package com.rama.backend_spring_boot.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class LookupUser {
    private String name;
    private String blog;
    private String type;
    private String url;
}
