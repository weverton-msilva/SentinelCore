CREATE TABLE threat_indicator (
    id  BIGSERIAL PRIMARY KEY,
    value VARCHAR(512)NOT NULL,
    type VARCHAR(20) NOT NULL CHECK (type IN ('IP', 'DOMAIN', 'HASH')),
    created_at TIMESTAMP NOT NULL DEFAULT now(),
    CONSTRAINT uk_threat_indicator_value_type UNIQUE (value, type)
);