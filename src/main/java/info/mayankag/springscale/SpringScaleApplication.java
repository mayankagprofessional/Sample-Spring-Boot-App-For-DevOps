package info.mayankag.springscale;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication()
@EnableAsync
public class SpringScaleApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringScaleApplication.class, args);
	}

}
