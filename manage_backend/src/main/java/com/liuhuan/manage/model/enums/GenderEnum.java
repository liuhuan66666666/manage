package com.liuhuan.manage.model.enums;

import org.apache.commons.lang3.ObjectUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @version 1.0
 * @Author liuhuan
 * @Date 2024/10/5 18:59
 * @注释
 */

public enum GenderEnum {

    MALE("男", 1),
    FEMALE("女", 2);

    private final String text;
    private final Integer value;

    GenderEnum(String text, Integer value) {
        this.text = text;
        this.value = value;
    }

    public String getText() {
        return text;
    }

    public Integer getValue() {
        return value;
    }


    /**
     * 获取值列表
     *
     * @return
     */
    public static List<Integer> getValues() {
        return Arrays.stream(values()).map(item -> item.value).collect(Collectors.toList());
    }


    /**
     * 根据 value 获取枚举
     *
     * @param value
     * @return
     */
    public static GenderEnum getEnumByValue(Integer value) {
        if (ObjectUtils.isEmpty(value)) {
            return null;
        }
        for (GenderEnum genderEnum : GenderEnum.values()) {
             if(genderEnum.value==value){
                 return genderEnum;
             }
        }
        return null;
    }


}
