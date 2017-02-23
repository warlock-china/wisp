package cn.com.warlock.wisp.core.test.support.reflection;

import org.junit.Test;

import cn.com.warlock.wisp.core.support.reflection.ReflectionUtil;

public class ReflectionUtilTestCase {

    /**
     * 通过扫描，获取反射对象
     */
    @Test
    public void test() {

        ReflectionUtil.getReflection("cn.com.warlock.wisp");
    }
}
