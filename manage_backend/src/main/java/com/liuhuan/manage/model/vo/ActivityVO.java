package com.liuhuan.manage.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.liuhuan.manage.model.entity.Activity;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.Date;

/**
 * 活动表视图
 *
 * @author <a href="https://github.com/liuhuan66666666">程序员刘欢</a>
 */
@Data
public class ActivityVO implements Serializable {

    /**
     *
     */
    private Integer id;

    /**
     *
     */
    private String name;

    /**
     *
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")  // 格式化日期为 YYYY-MM-DD
    private Date date;

    /**
     *
     */
    private String location;

    /**
     *
     */
    private Date created_at;


    /**
     * 封装类转对象
     *
     * @param activityVO
     * @return
     */
    public static Activity voToObj(ActivityVO activityVO) {
        if (activityVO == null) {
            return null;
        }
        Activity activity = new Activity();
        BeanUtils.copyProperties(activityVO, activity);
        return activity;
    }

    /**
     * 对象转封装类
     *
     * @param activity
     * @return
     */
    public static ActivityVO objToVo(Activity activity) {
        if (activity == null) {
            return null;
        }
        ActivityVO activityVO = new ActivityVO();
        BeanUtils.copyProperties(activity, activityVO);
        return activityVO;
    }
}
