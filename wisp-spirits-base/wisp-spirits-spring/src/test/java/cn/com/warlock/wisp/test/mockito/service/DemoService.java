package cn.com.warlock.wisp.test.mockito.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.warlock.wisp.test.mockito.dao.DemoDao;
import cn.com.warlock.wisp.test.mockito.entity.Demo;

@Service
public class DemoService {

    @Autowired
    UsedService usedService;

    @Autowired
    DemoDao demoDao;

    public String echo(String str) {
        Demo demo = new Demo();
        demo.setId(1L);
        demo.setDemoValue("demo");
        demoDao.updateByPrimaryKeySelective(demo);
        return usedService.echo("hello");
    }

}
