package cn.com.warlock.wisp.test.mockito;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import cn.com.warlock.wisp.test.mockito.service.DemoService;
import cn.com.warlock.wisp.test.mockito.service.IUsedService;
import cn.com.warlock.wisp.test.mockito.service.impl.UsedServiceImpl;

public class DemoServiceTest {

    @InjectMocks
    private DemoService demoService = new DemoService();

    @Spy
    private IUsedService usedService = new UsedServiceImpl();

    @Before
    public void setUp() {

        MockitoAnnotations.initMocks(this);
        Mockito.when(usedService.echo("hello")).thenReturn("world");

    }

    @Test
    public void testEcho() {

        String result = demoService.echo2("hello");
        Assert.assertEquals("world", result);

        result = demoService.echo2("hello world");
        Assert.assertEquals("hello world", result);
    }
}
