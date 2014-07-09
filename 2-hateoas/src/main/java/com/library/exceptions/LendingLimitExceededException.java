package com.library.exceptions;

import com.library.domain.Member;

/**
 * @author Jason Aliyetti <jason.aliyetti@semanticbits.com>
 */
public class LendingLimitExceededException extends RuntimeException{

    public LendingLimitExceededException() {
        super("Member has reached the lending limit.");
    }
}
