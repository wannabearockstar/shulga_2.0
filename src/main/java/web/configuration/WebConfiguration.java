package web.configuration;

import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * Created by wannabe on 06.11.15.
 */
@Configuration
@EnableWebMvc
@ComponentScan
public class WebConfiguration extends WebMvcAutoConfiguration {
}
