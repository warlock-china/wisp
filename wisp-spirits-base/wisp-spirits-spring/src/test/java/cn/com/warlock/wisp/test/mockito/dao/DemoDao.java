package cn.com.warlock.wisp.test.mockito.dao;

import cn.com.warlock.wisp.test.mockito.entity.Demo;

public interface DemoDao {

    Demo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Demo demo);
}
