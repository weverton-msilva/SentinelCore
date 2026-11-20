package com.dev.SentinelCore.controller;

import com.dev.SentinelCore.dto.IndicatorRequest;
import com.dev.SentinelCore.dto.IndicatorResponse;
import com.dev.SentinelCore.enums.IndicatorType;
import com.dev.SentinelCore.service.IndicatorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/indicators")
@RequiredArgsConstructor
public class IndicatorController {

    private final IndicatorService service;

    @PostMapping
    public ResponseEntity<IndicatorResponse> create(@Valid @RequestBody IndicatorRequest request) {
        IndicatorResponse created = service.create(request);
        return ResponseEntity.created(URI.create("/api/indicators/" + created.id())).body(created);
    }

    @GetMapping("/{id}")
    public IndicatorResponse findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @GetMapping
    public Page<IndicatorResponse> list(
            @RequestParam(required = false) IndicatorType type,
            @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return service.list(type, pageable);
    }

    @PutMapping("/{id}")
    public IndicatorResponse update(@PathVariable Long id, @Valid @RequestBody IndicatorRequest request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
