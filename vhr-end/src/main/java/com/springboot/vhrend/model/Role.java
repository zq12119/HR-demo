package com.springboot.vhrend.model;
import lombok.Data;
import java.io.Serializable;

@Data
public class Role implements Serializable {
    private Integer id;
    private String name;
    private String namezh;
    private static final long serialVersionUID = 1L;
}