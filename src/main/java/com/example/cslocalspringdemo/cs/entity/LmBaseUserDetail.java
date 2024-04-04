package com.example.cslocalspringdemo.cs.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;

import java.time.LocalDateTime;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 *
 * </p>
 *
 * @author Song
 * @since 2024-04-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "LmBaseUserDetail对象", description = "")
public class LmBaseUserDetail extends Model<LmBaseUserDetail> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "是否删除(0未删1删除)")
    private Integer isDelete;

    @ApiModelProperty(value = "是否启用(0启用1未启用)")
    private Integer isEnabled;

    @ApiModelProperty(value = "创建人id")
    private Integer createId;

    @ApiModelProperty(value = "创建人姓名")
    private String createName;

    @ApiModelProperty(value = "创建人时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "修改人id")
    private Integer modifyId;

    @ApiModelProperty(value = "修改人姓名")
    private String modifyName;

    @ApiModelProperty(value = "修改人时间")
    private LocalDateTime modifyTime;

    @ApiModelProperty(value = "主表Id")
    private Integer baseUserId;

}
