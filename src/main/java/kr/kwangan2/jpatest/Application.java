package kr.kwangan2.jpatest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {
	
	public static void main(String[] args) {
		SpringApplication application = 
				new SpringApplication(Application.class);
		application.setWebApplicationType(WebApplicationType.NONE); //none으로 설정했기때문에 내장 톰캣 구동을 하지 않고 실행된다
		application.run(args);
	}

}//class
