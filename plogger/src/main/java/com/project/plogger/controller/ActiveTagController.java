package com.project.plogger.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.plogger.dto.request.active.PostActiveTagRequestDto;
import com.project.plogger.dto.response.ResponseDto;
import com.project.plogger.service.ActiveTagService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/active")
@RequiredArgsConstructor
public class ActiveTagController {

    private final ActiveTagService tagService;
    
    @PostMapping("/tag/{activeId}/{recruitId}")
    public ResponseEntity<ResponseDto> postTag(@RequestBody @Valid PostActiveTagRequestDto dto, @PathVariable("activeId") Integer activeId, @PathVariable("recruitId") Integer recruitId) {
        ResponseEntity<ResponseDto> response = tagService.postTag(dto, activeId, recruitId);
        return response;
    }

    @DeleteMapping("/tag/{activeId}/{recruitId}/{tagId}")
    public ResponseEntity<ResponseDto> deleteTag(@PathVariable String tagId, @PathVariable("activeId") Integer activeId, @PathVariable("recruitId") Integer recruitId) {
        ResponseEntity<ResponseDto> response = tagService.deleteTag(tagId, activeId, recruitId);
        return response;
    }

}
