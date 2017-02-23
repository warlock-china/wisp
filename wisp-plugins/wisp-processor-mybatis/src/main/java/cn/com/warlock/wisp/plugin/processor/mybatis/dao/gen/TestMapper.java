package cn.com.warlock.wisp.plugin.processor.mybatis.dao.gen;

import cn.com.warlock.wisp.plugin.processor.mybatis.entity.Test;

public interface TestMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Test record);

    int insertSelective(Test record);

    Test selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Test record);

    int updateByPrimaryKey(Test record);
}