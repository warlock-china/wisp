package cn.com.warlock.wisp.plugin.processor.mybatis.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.warlock.wisp.plugin.processor.mybatis.dao.ext.TestDao;
import cn.com.warlock.wisp.plugin.processor.mybatis.entity.Test;
import cn.com.warlock.wisp.plugin.processor.mybatis.service.MybatisMgr;

@Service
public class MybatisMgrImpl implements MybatisMgr {

    @Autowired
    private TestDao testDao;

    @Override
    public void doWork() {
        Test test = new Test();
        test.setName("kkk");
        testDao.insert(test);
    }
}
