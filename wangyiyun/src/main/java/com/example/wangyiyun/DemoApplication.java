package com.example.wangyiyun;

import com.example.wangyiyun.pipeline.NetEaseMusicPipeline;
import com.example.wangyiyun.processor.WangyiMusicPageProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
public class DemoApplication implements CommandLineRunner{

	@Autowired
	WangyiMusicPageProcessor wangyiMusicPageProcessor;

	@Autowired
	NetEaseMusicPipeline netEaseMusicPipeline;

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	/**
	 * spring boot 非web启动 实现CommandLineRunner接口，并覆盖run方法
	 * @param strings
	 * @throws Exception
	 */
	@Override
	public void run(String... strings) throws Exception {
		wangyiMusicPageProcessor.start(wangyiMusicPageProcessor,netEaseMusicPipeline);
	}
}
