package com.example.cslocalspringdemo.cs.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.example.cslocalspringdemo.common.page.PageInput;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

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
@ApiModel(value = "LmBaseUser对象", description = "")
public class LmBaseUserDto extends PageInput {

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

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "修改人id")
    private Integer modifyId;

    @ApiModelProperty(value = "修改人姓名")
    private String modifyName;

    @ApiModelProperty(value = "修改时间")
    private LocalDateTime modifyTime;

    @ApiModelProperty(value = "用户名")
    private String userName;

    @ApiModelProperty(value = "年龄")
    private Integer userAge;

    @ApiModelProperty(value = "级别(会员member/普通ordinary)")
    private String userLevel;

    @ApiModelProperty(value = "用户密码")
    private String userPwd;

    @ApiModelProperty(value = "用户令牌")
    private String userToken;

    @ApiModelProperty(value = "授权类型")
    private String grantType;
}
