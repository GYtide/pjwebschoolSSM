package com.gk.study.entity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

@Data
@TableName("b_resource")
public class Resou implements Serializable {
    @TableId(value = "id",type = IdType.AUTO)
    public Long id;
    @TableField
    public String link;
    @TableField
    public String createTime;
    @TableField
    public  String name;
    @TableField
    public  String tid;
    @TableField(exist = false)
    public MultipartFile file;

}
