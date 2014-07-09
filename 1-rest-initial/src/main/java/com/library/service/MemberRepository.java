package com.library.service;

import com.library.domain.Member;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;


/**
 * @author Jason Aliyetti <jason.aliyetti@semanticbits.com>
 */
public interface MemberRepository extends CrudRepository<Member, Long> {
}
