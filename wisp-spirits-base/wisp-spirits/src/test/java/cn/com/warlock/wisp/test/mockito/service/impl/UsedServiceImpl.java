package cn.com.warlock.wisp.test.mockito.service.impl;

import cn.com.warlock.wisp.test.mockito.service.IUsedService;

public class UsedServiceImpl implements IUsedService {

    @Override
    public String echo(String str) {
        return str;
    }
}
