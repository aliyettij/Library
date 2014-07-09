package com.library;

import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.xml.MarshallingHttpMessageConverter;
import org.springframework.oxm.xstream.XStreamMarshaller;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * @author Jason Aliyetti <jason.aliyetti@semanticbits.com>
 */
@Configuration
@ComponentScan
@EnableAutoConfiguration
@EnableTransactionManagement
public class Library {
    private static Logger logger = Logger.getLogger(Library.class);

    public static void main(String[] args) {
        SpringApplication.run(Library.class, args);
    }

    @Bean
    protected ServletContextListener listener() {
        return new ServletContextListener() {
            @Override
            public void contextInitialized(ServletContextEvent sce) {
                logger.info("ServletContext initialized");
            }

            @Override
            public void contextDestroyed(ServletContextEvent sce) {
                logger.info("ServletContext destroyed");
            }
        };
    }



    @Bean
    public HttpMessageConverter<?> xmlConverter() {
        MarshallingHttpMessageConverter converter = new MarshallingHttpMessageConverter();

        XStreamMarshaller marshaller = new XStreamMarshaller();

        converter.setMarshaller(marshaller);
        converter.setUnmarshaller(marshaller);

        return converter;
    }


}
