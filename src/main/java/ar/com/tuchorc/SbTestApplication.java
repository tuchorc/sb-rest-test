package ar.com.tuchorc;

import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Created by fmq on 3/22/17.
 */
@SpringBootApplication
@EnableAutoConfiguration
@MapperScan("ar.com.tuchorc.persistence.mapper")
@ComponentScan({"ar.com.tuchorc"})
public class SbTestApplication {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(SbTestApplication.class, args);
    }

    @Bean
    public JacksonJaxbJsonProvider jacksonJaxbJsonProvider() {
        return new JacksonJaxbJsonProvider();
    }

}
