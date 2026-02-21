package itb.sdrank.web.test;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import itb.sdrank.config.ApplicationConfig;

@Configuration
@ComponentScan("itb.sdrank.web")
@Import(ApplicationConfig.class)
public class TestConfig {

}
