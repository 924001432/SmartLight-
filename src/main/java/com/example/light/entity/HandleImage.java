package com.example.light.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "handle_image")
public class HandleImage {
    @TableField(value = "image_id")
    @TableId(value = "image_id",type = IdType.AUTO)
    private Integer imageId;

    @TableField(value = "image_path")
    private String imagePath;

    @TableField(value = "handle_result_id")
    private Integer handleResultId;


}
