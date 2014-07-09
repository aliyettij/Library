package com.library.hateoas;

import com.library.domain.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.hateoas.ResourceProcessor;
import org.springframework.hateoas.Resources;
import org.springframework.stereotype.Component;

/**
 * @author Jason Aliyetti <jason.aliyetti@semanticbits.com>
 */
@Component
public class BookResourceProcessor implements ResourceProcessor<Resource<Book>>{

    @Autowired
    EntityLinks entityLinks;

    @Override
    public Resource<Book> process(Resource<Book> resource) {

        if (resource.getContent().isCheckedOut()) {
            resource.add(entityLinks.linkForSingleResource(resource.getContent()).slash("reserve").withRel("reserve"));
        } else {
            resource.add(entityLinks.linkForSingleResource(resource.getContent()).slash("checkout").withRel("checkout"));
        }

        return resource;
    }
}
