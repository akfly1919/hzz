package com.akfly.hzz.dto;  /**
 * @title: TeamDetailsDto
 * @projectName hzz
 * @description 团队详情
 * @author MLL
 * @date 2021/2/28 16:02
 */

import com.akfly.hzz.vo.CustomerrelationinfoVo;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @ClassName: TeamDetailsDto
 * @Description: TODO
 * @Author
 * @Date 2021/2/28 16:02
 */
@Data
public class TeamDetailsDto {

    private CustomerrelationinfoVo customerrelationinfoVo;

    private String phoneNum;

    private String cbiUsername;

    private BigDecimal rechargeAmount;

}
