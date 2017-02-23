package cn.com.warlock.wisp.injector.test.common;

import org.junit.Test;

import cn.com.warlock.wisp.test.support.utils.TestUtils;

public class TestCommonTestCase {

    @Test
    public void test() {

        TestUtils.testAllClassUnderPackage("cn.com.warlock.wisp.injector");
    }
}
