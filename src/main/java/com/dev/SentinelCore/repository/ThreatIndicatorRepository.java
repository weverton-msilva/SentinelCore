package com.dev.SentinelCore.repository;

import com.dev.SentinelCore.entity.ThreatIndicator;
import com.dev.SentinelCore.enums.IndicatorType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ThreatIndicatorRepository extends JpaRepository<ThreatIndicator, Long> {

    boolean existsByValueAndType(String value, IndicatorType type);

    boolean existsByValueAndTypeAndIdNot(String value, IndicatorType type, Long id);

    Page<ThreatIndicator> findByType(IndicatorType type, Pageable pageable);
}