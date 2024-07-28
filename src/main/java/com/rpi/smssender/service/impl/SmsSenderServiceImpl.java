package com.rpi.smssender.service.impl;

import com.rpi.smssender.model.SMSRequest;
import com.rpi.smssender.model.SMSResponse;
import com.rpi.smssender.service.SmsSenderService;
import com.vonage.client.VonageClient;
import com.vonage.client.sms.MessageStatus;
import com.vonage.client.sms.SmsSubmissionResponse;
import com.vonage.client.sms.messages.TextMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SmsSenderServiceImpl implements SmsSenderService {

    private final VonageClient vonageClient;
    private final String fromNumber;

    public SmsSenderServiceImpl(@Value("${vonage.apiKey}") String apiKey,
                                @Value("${vonage.apiSecret}") String apiSecret,
                                @Value("${vonage.fromNumber}") String fromNumber) {
        this.vonageClient = VonageClient.builder().apiKey(apiKey).apiSecret(apiSecret).build();
        this.fromNumber = fromNumber;
    }

    public SMSResponse sendSms(SMSRequest request) {
//        request.getRecipients().forEach(recipient -> {
        TextMessage textMessage = new TextMessage(fromNumber, request.getRecipients().get(0), request.getMessage());
        SmsSubmissionResponse smsSubmissionResponse = vonageClient.getSmsClient().submitMessage(textMessage);
        SMSResponse smsResponse;
        if (smsSubmissionResponse.getMessages().get(0).getStatus() == MessageStatus.OK) {
            smsResponse = SMSResponse.builder()
                    .responseCode(MessageStatus.OK.toString())
                    .responseMessage("Message sent successfully.").build();
        } else {
            smsResponse = SMSResponse.builder()
                    .responseCode(smsSubmissionResponse.getMessages().get(0).getStatus().toString())
                    .responseMessage("Message failed with error: " + smsSubmissionResponse.getMessages().get(0).getErrorText())
                    .build();
        }
//        });
        return smsResponse;
    }
}
