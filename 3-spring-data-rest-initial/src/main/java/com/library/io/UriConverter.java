package com.library.io;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.data.mapping.PersistentEntity;
import org.springframework.data.mapping.context.PersistentEntities;
import org.springframework.data.rest.core.UriToEntityConverter;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.Identifiable;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import java.io.IOException;
import java.net.URI;

/**
 * @author Jason Aliyetti <jason.aliyetti@semanticbits.com>
 */
public class UriConverter extends AbstractHttpMessageConverter<Object> {
    private static final Logger LOGGER = Logger.getLogger(UriConverter.class);
    private static final TypeDescriptor URI_DESCRIPTOR = TypeDescriptor.valueOf(URI.class);

    @Autowired
    UriToEntityConverter uriToEntityConverter;

    @Autowired
    PersistentEntities persistentEntities;

    @Autowired
    EntityLinks entityLinks;

    public UriConverter() {
        super(new MediaType("text", "uri-list"));
    }

    @Override
    protected boolean supports(Class<?> clazz) {
        LOGGER.info("Can convert " + clazz.getName() + " : " + (persistentEntities.getPersistentEntity(clazz) != null));
        return persistentEntities.getPersistentEntity(clazz) != null;
    }

    @Override
    protected Object readInternal(Class<?> clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        String uriString = IOUtils.toString(inputMessage.getBody());

        Object result = uriToEntityConverter.convert(URI.create(uriString), URI_DESCRIPTOR, TypeDescriptor.valueOf(clazz));

        return result;
    }

    @Override
    protected void writeInternal(Object o, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
        throw new UnsupportedOperationException("not implemented");
    }
}
