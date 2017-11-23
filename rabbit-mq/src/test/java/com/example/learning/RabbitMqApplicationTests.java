package com.example.learning;

import com.example.learning.send.Send;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RabbitMqApplicationTests {

	@Test
	public void contextLoads() {
	}

	@Autowired
	private Send send;

	/**
	 * 简单的消息队列测试
	 */
	@Test
	public void send() {
		send.sendMsg("这是一个测试的队列！！！！");
	}
}
