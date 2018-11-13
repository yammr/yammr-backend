package com.gibgab.service.security.verification;

import java.util.UUID;

public class VerificationTokenGenerator {

    public static String generateVerificationToken(){
        return UUID.randomUUID().toString();
    }
}
