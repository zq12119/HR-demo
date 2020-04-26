package com.springboot.vhrend.model;

import lombok.Data;

@Data
public class Meta {
    private Boolean keepAlive;
    private Boolean requireAuth;
    private static final long serialVersionUID = 1L;
}