package com.library;

import com.library.io.UriConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.webmvc.PersistentEntityResource;
import org.springframework.data.rest.webmvc.config.RepositoryRestMvcConfiguration;
import org.springframework.hateoas.Link;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.xml.MarshallingHttpMessageConverter;
import org.springframework.oxm.xstream.XStreamMarshaller;
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
        messageConverters.add(xmlConverter());
    }

    @Bean
    public HttpMessageConverter<?> customUriConverter() {
        return new UriConverter();
    }

    @Bean
    public HttpMessageConverter<?> xmlConverter() {
        MarshallingHttpMessageConverter converter = new MarshallingHttpMessageConverter();

        XStreamMarshaller marshaller = new XStreamMarshaller();
        marshaller.getXStream().alias("Resource", PersistentEntityResource.class);
        marshaller.getXStream().alias("Link", Link.class);
        marshaller.getXStream().aliasAttribute(PersistentEntityResource.class, "links", "Links");
        marshaller.getXStream().omitField(PersistentEntityResource.class, "entity");
        converter.setMarshaller(marshaller);
        converter.setUnmarshaller(marshaller);

        return converter;
    }

    @Bean
    public ShallowEtagHeaderFilter etagFilter() {
        return new ShallowEtagHeaderFilter();
    }


}

