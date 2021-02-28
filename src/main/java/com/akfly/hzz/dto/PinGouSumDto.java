package com.akfly.hzz.dto;  /**
 * @title: PinGouSumDto
 * @projectName hzz
 * @description 首页拼购汇总信息
 * @author MLL
 * @date 2021/2/28 11:17
 */

import lombok.Data;

/**
 * @ClassName: PinGouSumDto
 * @Description: TODO
 * @Author
 * @Date 2021/2/28 11:17
 */
@Data
public class PinGouSumDto {

    private long gbiId;;

    private int isFinished;

    private int finishedNum;

    private int unFinishedNum;

    private int rewordNum;

    private String gbiName;

    private String gbiPicture;
}
