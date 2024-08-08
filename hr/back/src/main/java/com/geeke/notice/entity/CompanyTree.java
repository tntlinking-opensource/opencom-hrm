package com.geeke.notice.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@ApiModel
@Data
public class CompanyTree {

  @ApiModelProperty("id")
  public String id;      //id

  @ApiModelProperty("名字")
  public String name;    //名字

  @ApiModelProperty("父id")
  public String parentId; //父id

  @ApiModelProperty(hidden = true)
  public List<CompanyTree>  children = new ArrayList<>();

}
