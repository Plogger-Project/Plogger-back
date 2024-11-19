package com.project.plogger.controller;

import java.util.concurrent.ConcurrentHashMap;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.project.plogger.dto.request.alert.AlertRequestDto;
import com.project.plogger.dto.response.ResponseDto;
import com.project.plogger.dto.response.alert.GetAlertListResponseDto;
import com.project.plogger.service.AlertService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/alert")
public class AlertController {

    private final AlertService alertService;
    private static final ConcurrentHashMap<String, SseEmitter> emitters = new ConcurrentHashMap<>();

    @PostMapping(value = {"", "/"})
    public ResponseEntity<ResponseDto> createAlert(
        @RequestBody @Valid AlertRequestDto request) {
        ResponseEntity<ResponseDto> response = alertService.createAlert(request);
        
        // sendAlertToClient(response.getBody());

        return response;
    }

    // @PatchMapping("/{id}/read")
    // public ResponseEntity<ResponseDto> markAsRead(@PathVariable Long id) {
    //     return alertService.markAlertAsRead(id);
    // }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto> deleteAlert(@PathVariable Long id) {
        return alertService.deleteAlert(id);
    }


    @GetMapping(value = {"", "/"})
    public ResponseEntity<? super GetAlertListResponseDto> getAlerts(@AuthenticationPrincipal String userId) {
        ResponseEntity<? super GetAlertListResponseDto> response = alertService.getAlertList(userId);
        return response;
    }

    // @GetMapping("/stream")
    // public SseEmitter streamAlerts(@AuthenticationPrincipal String userId) {
    //     SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);
    //     emitters.put(userId, emitter);

    //     // SSE 연결 종료시 제거
    //     emitter.onCompletion(() -> emitters.remove(userId));
    //     emitter.onTimeout(() -> emitters.remove(userId));

    //     return emitter;
    // }

    //     private void sendAlertToClient(String userId, ResponseDto alertData) {
    //     SseEmitter emitter = emitters.get(userId);
    //     if (emitter != null) {
    //         try {
    //             emitter.send(SseEmitter.event()
    //                     .name("alert")
    //                     .data(alertData));
    //         } catch (Exception Exception) {
    //             emitters.remove(userId);
    //         }
    //     }
    // }
}
