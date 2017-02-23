package cn.com.warlock.wisp.test.mockito.service;

import org.springframework.stereotype.Service;

/**
 * @author zhugongrui
 * @date 2016年6月29日下午6:00:51
 */
@Service
public class UsedService {
	
	public String echo(String str) {
		return str;
	}
}
