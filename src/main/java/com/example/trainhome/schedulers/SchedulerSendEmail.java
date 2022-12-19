package com.example.trainhome.schedulers;

import com.example.trainhome.services.EmailServiceTMP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;

@Component

public class SchedulerSendEmail {

    @Autowired
    EmailServiceTMP emailServiceTMP;

    @Scheduled(fixedRate = 500000)
    public void reportCurrentTime() throws MessagingException {
        emailServiceTMP.toEmail();
    }
}
