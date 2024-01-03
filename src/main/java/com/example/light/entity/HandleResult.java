package com.example.light.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "handle_result")
public class HandleResult {
    @TableField(value = "handle_result_id")
    @TableId(value = "handle_result_id",type = IdType.AUTO)
    private Integer handleResultId;

    @TableField(value = "handle_result_text")
    private String handleResultText;


}
