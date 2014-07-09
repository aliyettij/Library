package com.library;

import com.library.io.UriConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.webmvc.config.RepositoryRestMvcConfiguration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.filter.ShallowEtagHeaderFilter;

import java.util.List;

/**
 * @author Jason Aliyetti <jason.aliyetti@semanticbits.com>
 */
@Configuration
public class LibraryRestConfig extends RepositoryRestMvcConfiguration {

    @Override
    protected void configureHttpMessageConverters(List<HttpMessageConverter<?>> messageConverters) {
        super.configureHttpMessageConverters(messageConverters);    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Bean
    public HttpMessageConverter<?> customUriConverter() {
        return new UriConverter();
    }

    @Bean
    public ShallowEtagHeaderFilter etagFilter() {
        return new ShallowEtagHeaderFilter();
    }

}

