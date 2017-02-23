package cn.com.warlock.wisp.test.mockito.service;

import cn.com.warlock.wisp.test.mockito.service.impl.UsedServiceImpl;

public class DemoService {

    private IUsedService iUsedService = new UsedServiceImpl();

    public String echo2(String str) {

        return iUsedService.echo(str);
    }

}
