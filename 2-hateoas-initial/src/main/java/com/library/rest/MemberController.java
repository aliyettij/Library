package com.library.rest;

import com.library.domain.Member;
import com.library.service.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Jason Aliyetti <jason.aliyetti@semanticbits.com>
 */
@Controller
@RequestMapping(value = "members")
public class MemberController {

    @Autowired
    MemberRepository memberRepository;

    //add member
    /**
     * TODO:  Wire this method (using annotations) to accept POST requests to http://localhost:8080/members
     * This method should create a Member from the RequestBody of a POST request.
     * @param member
     * @return
     */
    public ResponseEntity<Member> createBook( Member member) {
        member.setId(null);
        Member resultItem = memberRepository.save(member);

        return new ResponseEntity<>(resultItem, HttpStatus.CREATED);
    }

    //get members
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Iterable<Member>> getMembers() {
        return new ResponseEntity<>(memberRepository.findAll(), HttpStatus.OK);
    }

    //get member
    @RequestMapping(value ="/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Member> getBook(@PathVariable Long id) {
        return new ResponseEntity<>(memberRepository.findOne(id), HttpStatus.OK);
    }

}
