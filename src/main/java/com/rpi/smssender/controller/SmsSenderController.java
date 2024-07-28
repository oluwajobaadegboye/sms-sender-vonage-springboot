package com.rpi.smssender.controller;

import com.rpi.smssender.model.SMSRequest;
import com.rpi.smssender.model.SMSResponse;
import com.rpi.smssender.service.SmsSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SmsSenderController {

    private final SmsSenderService smsService;

    @Autowired
    public SmsSenderController(SmsSenderService smsService) {
        this.smsService = smsService;
    }

    @PostMapping("/send")
    public SMSResponse sendSms(@RequestBody SMSRequest request) {
        return smsService.sendSms(request);
    }
}

