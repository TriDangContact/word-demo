package com.tridang.assignment;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

@Log4j2
@SpringBootApplication
public class AssignmentApplication {

	public static void main(String[] args) {
		new SpringApplicationBuilder()
				.profiles("prod")
				.sources(AssignmentApplication.class)
				.run(args);
	}
}
