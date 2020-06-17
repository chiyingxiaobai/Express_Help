package com.kc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
/*SpringBoot在项目启动后会遍历所有实现CommandLineRunner
 * 的类并执行run方法。*/
public class ExpressHelpApplication implements CommandLineRunner{
	@Autowired
	private ApplicationContext ctx;
	public static void main(String[] args) {
		SpringApplication.run(ExpressHelpApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println(ctx.getBean("defaultCache"));
	}

}
