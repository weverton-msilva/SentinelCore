package com.dev.SentinelCore.service;

import com.dev.SentinelCore.dto.IndicatorRequest;
import com.dev.SentinelCore.dto.IndicatorResponse;
import com.dev.SentinelCore.entity.ThreatIndicator;
import com.dev.SentinelCore.enums.IndicatorType;
import com.dev.SentinelCore.exception.DuplicateIndicatorException;
import com.dev.SentinelCore.exception.IndicatorNotFoundException;
import com.dev.SentinelCore.repository.ThreatIndicatorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class IndicatorService {

    private final ThreatIndicatorRepository repository;
    private final IndicatorValidator validator;

    @Transactional
    public IndicatorResponse create(IndicatorRequest request) {
        String value = validator.normalizeAndValidate(request.value(), request.type());
        if (repository.existsByValueAndType(value, request.type())) {
            throw new DuplicateIndicatorException(value, request.type());
        }
        ThreatIndicator indicator = new ThreatIndicator();
        indicator.setValue(value);
        indicator.setType(request.type());
        return IndicatorResponse.from(repository.save(indicator));
    }

    @Transactional(readOnly = true)
    public IndicatorResponse findById(Long id) {
        return IndicatorResponse.from(getOrThrow(id));
    }

    @Transactional(readOnly = true)
    public Page<IndicatorResponse> list(IndicatorType type, Pageable pageable) {
        Page<ThreatIndicator> page = (type == null)
                ? repository.findAll(pageable)
                : repository.findByType(type, pageable);
        return page.map(IndicatorResponse::from);
    }

    @Transactional
    public IndicatorResponse update(Long id, IndicatorRequest request) {
        ThreatIndicator indicator = getOrThrow(id);
        String value = validator.normalizeAndValidate(request.value(), request.type());
        if (repository.existsByValueAndTypeAndIdNot(value, request.type(), id)) {
            throw new DuplicateIndicatorException(value, request.type());
        }
        indicator.setValue(value);
        indicator.setType(request.type());
        return IndicatorResponse.from(indicator); // o JPA salva sozinho ao fim da transação
    }

    @Transactional
    public void delete(Long id) {
        repository.delete(getOrThrow(id));
    }

    private ThreatIndicator getOrThrow(Long id) {
        return repository.findById(id).orElseThrow(() -> new IndicatorNotFoundException(id));
    }
}
