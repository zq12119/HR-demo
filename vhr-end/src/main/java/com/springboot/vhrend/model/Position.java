package com.springboot.vhrend.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel(value = "职位管理实体类", description = "职位信息描述")
public class Position {
    @ApiModelProperty(value = "职位id")
    private Integer id;
    @ApiModelProperty(value = "职位名称")
    private String name;
//js没有线程format函数调用需要自己写，后端可以用黄油刀直接调用
    @JsonFormat(pattern = "yyyy-MM-dd hh:hh", timezone = "Asia/Shanghai")
    @ApiModelProperty(value = "职位创建时间")
    private Date createDate;
    @ApiModelProperty(value = "是否启用")
    private Boolean enabled;


}