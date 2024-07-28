package com.rpi.smssender.service;

import com.rpi.smssender.model.SMSRequest;
import com.rpi.smssender.model.SMSResponse;

public interface SmsSenderService {
    public SMSResponse sendSms(SMSRequest request);
}
