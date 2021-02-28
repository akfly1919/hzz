package com.akfly.hzz.dto;  /**
 * @title: MyTeamDto
 * @projectName hzz
 * @description 我的团队
 * @author MLL
 * @date 2021/2/28 11:56
 */

import lombok.Data;

/**
 * @ClassName: MyTeamDto
 * @Description: TODO
 * @Author mengliangliang
 * @Date 2021/2/28 11:56
 */
@Data
public class MyTeamDto {

    private int num;

    private int activeNum;

    private int finishedNum;

    private String level;
}
