package com.akfly.hzz.service.impl;

import com.akfly.hzz.vo.ContactusVo;
import com.akfly.hzz.mapper.ContactusMapper;
import com.akfly.hzz.service.ContactusService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wangfei
 * @since 2021-02-10
 */
@Service
public class ContactusServiceImpl extends ServiceImpl<ContactusMapper, ContactusVo> implements ContactusService {

    @Override
    public ContactusVo getContactusVo() {

        List<ContactusVo> list = lambdaQuery().list();
        if (CollectionUtils.isEmpty(list)) {
            return null;
        } else {
            return list.get(0);
        }
    }
}
