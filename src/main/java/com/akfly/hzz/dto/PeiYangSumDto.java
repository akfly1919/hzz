package com.akfly.hzz.dto;  /**
 * @title: PeiYangSumDto
 * @projectName hzz
 * @description 首页培养汇总信息
 * @author MLL
 * @date 2021/2/28 11:17
 */

import lombok.Data;

/**
 * @ClassName: PeiYangSumDto
 * @Description: TODO
 * @Author
 * @Date 2021/2/28 11:17
 */
@Data
public class PeiYangSumDto {

    private int isFinished;

    private int oneLevelFinishedNum;

    private int towLevelFinishedNum;

    private String gbiName;

    private String gbiPicture;

}
