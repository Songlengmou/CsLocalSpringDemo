package com.example.cslocalspringdemo.cs.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author Song
 * @since 2023-11-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "CsLocalDemo对象", description = "")
public class CsLocalDemoVo extends Model<CsLocalDemoVo> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "逻辑删除")
    private Integer isDeleted;

    @ApiModelProperty(value = "启用标志")
    private Integer isEnabled;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "创建人")
    private String createName;

    @ApiModelProperty(value = "修改时间")
    private LocalDateTime modifyTime;

    @ApiModelProperty(value = "修改人")
    private String modifyName;

    @ApiModelProperty(value = "姓名")
    private String userName;

    @ApiModelProperty(value = "年龄")
    private Integer userAge;

    @ApiModelProperty(value = "开始时间")
    private LocalDateTime startDate;

    @ApiModelProperty(value = "结束时间")
    private LocalDateTime endDate;

    //Add
    @ApiModelProperty(value = "地址")
    private String address;

    @ApiModelProperty(value = "等级")
    private String levelName;

    private List<CsLocalDemoDetailVo> csLocalDemoDetailVos;
}
