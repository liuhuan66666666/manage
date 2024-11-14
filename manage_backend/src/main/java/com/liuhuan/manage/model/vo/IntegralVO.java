package com.liuhuan.manage.model.vo;

import com.liuhuan.manage.model.entity.Integral;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;

/**
 * 积分表视图
 *
 * @author <a href="https://github.com/liuhuan66666666">程序员刘欢</a>
 */
@Data
public class IntegralVO implements Serializable {

    /**
     * 成员姓名
     */
    private Integer id;


    /**
     * 成员姓名
     */
    private String name;


    /**
     * 成员积分
     */
    private Integer score;

    /**
     * 封装类转对象
     *
     * @param integralVO
     * @return
     */
    public static Integral voToObj(IntegralVO integralVO) {
        if (integralVO == null) {
            return null;
        }
        Integral integral = new Integral();
        BeanUtils.copyProperties(integralVO, integral);
        return integral;
    }

    /**
     * 对象转封装类
     *
     * @param integral
     * @return
     */
    public static IntegralVO objToVo(Integral integral) {
        if (integral == null) {
            return null;
        }
        IntegralVO integralVO = new IntegralVO();
        BeanUtils.copyProperties(integral, integralVO);
        return integralVO;
    }
}
