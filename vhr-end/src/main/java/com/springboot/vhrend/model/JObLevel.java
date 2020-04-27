package com.springboot.vhrend.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel(value = "职称实体类", description="职称信息描述")
public class JObLevel {
    @ApiModelProperty(value = "职称的id")
    private Integer id;
    @ApiModelProperty(value = "职称名称")
    private String name;
    @ApiModelProperty(value = "职称等级")
    private String titlelevel;
    @JsonFormat(pattern = "yyyy-MM-dd hh:hh", timezone = "Asia/Shanghai")
    @ApiModelProperty(value = "职称创建的时间")
    private Date createdate;
    @ApiModelProperty(value = "是否应用")
    private Boolean enabled;

}