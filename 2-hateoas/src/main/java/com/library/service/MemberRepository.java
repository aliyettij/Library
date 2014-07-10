package com.library.service;

import com.library.domain.Member;
import org.springframework.data.repository.CrudRepository;


/**
 * @author Jason Aliyetti <jason.aliyetti@semanticbits.com>
 */
public interface MemberRepository extends CrudRepository<Member, Long> {
}
