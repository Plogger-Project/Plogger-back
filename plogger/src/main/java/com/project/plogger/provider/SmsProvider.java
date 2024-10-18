package com.project.plogger.provider;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import net.nurigo.sdk.message.service.DefaultMessageService;

@Component
public class SmsProvider {

    private final DefaultMessageService messageService;
    private final String from;

    public SmsProvider (
        @Value("${coolsms.api.key}") String apiKey,
        @Value("${coolsms.api.secret}") String secretKey,
        @Value("${coolsms.api.domain}") String domain,
        @Value("${coolsms.api.number}") String phoneNumber) {

            this.messageService = NurigoApp.INSTANCE.initialize(apiKey, secretKey, domain);
            this.from = phoneNumber;

        }


    public boolean sendMessage(String telNumber, String authNumber) {

        Message message = new Message();
        message.setFrom(from);
        message.setTo(telNumber);
        message.setText("[Plogger] 인증번호 [" + authNumber + "]를 입력해주세요.");

        SingleMessageSentResponse response = this.messageService.sendOne(new SingleMessageSendingRequest(message));

        boolean resultStatus = response.getStatusCode().equals("2000");

        return resultStatus;

    }

}
