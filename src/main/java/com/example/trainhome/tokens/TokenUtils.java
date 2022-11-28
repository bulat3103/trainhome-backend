package com.example.trainhome.tokens;

import com.example.trainhome.entities.Person;
import lombok.extern.slf4j.Slf4j;
import org.postgresql.util.Base64;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.Date;

@Slf4j
@Component
public class TokenUtils {

    public static String generate(Person person) {
        String in = String.valueOf(Date.from(Instant.now())) +
                Math.random() + person.getEmail() + person.getId() + person;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] bytes = md.digest(in.getBytes(StandardCharsets.UTF_8));
            return Base64.encodeBytes(bytes, Base64.DONT_BREAK_LINES);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
