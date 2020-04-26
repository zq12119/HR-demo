package com.springboot.vhrend.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class Position {
    private Integer id;
    private String name;
//js没有线程format函数调用需要自己写，后端可以用黄油刀直接调用
    @JsonFormat(pattern = "yyyy-MM-dd hh:hh", timezone = "Asia/Shanghai")
    private Date createDate;
    private Boolean enabled;
}