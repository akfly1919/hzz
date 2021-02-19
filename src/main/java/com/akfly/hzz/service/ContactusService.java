package com.akfly.hzz.service;

import com.akfly.hzz.vo.ContactusVo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wangfei
 * @since 2021-02-10
 */
public interface ContactusService extends IService<ContactusVo> {

    ContactusVo getContactusVo();

}
