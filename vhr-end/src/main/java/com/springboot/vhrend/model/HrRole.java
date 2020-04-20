package com.springboot.vhrend.model;


import lombok.Data;

import java.io.Serializable;

@Data
public class HrRole implements Serializable {
    private Integer id;

    private Integer hrid;

    private Integer rid;
}
