package cn.com.warlock.wisp.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import cn.com.warlock.wisp.test.log.logback.LogbackCapturingAppenderTest;
import cn.com.warlock.wisp.test.mockito.DemoServiceTest;
import cn.com.warlock.wisp.test.support.utils.DomTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({DomTest.class, LogbackCapturingAppenderTest.class, DemoServiceTest.class})
public class AllTest {

}


