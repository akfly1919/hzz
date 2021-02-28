package com.akfly.hzz.dto;  /**
 * @title: PinGouInfoDtos
 * @projectName hzz
 * @description
 * @author MLL
 * @date 2021/2/28 15:10
 */

import com.akfly.hzz.vo.PingouinfoVo;
import lombok.Data;

/**
 * @ClassName: PinGouInfoDtos
 * @Description: TODO
 * @Author
 * @Date 2021/2/28 15:10
 */
@Data
public class PinGouInfoDtos {

    private PingouinfoVo pingouinfoVo;

    private String gbiName;

    private String gbiPicture;

    private String cbiUsername;

}
