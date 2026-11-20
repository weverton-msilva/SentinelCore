CREATE TABLE users (
    id         BIGSERIAL PRIMARY KEY,
    name       VARCHAR(100) NOT NULL,
    email      VARCHAR(255) NOT NULL,
    password   VARCHAR(100) NOT NULL,
    role       VARCHAR(20)  NOT NULL CHECK (role IN ('ADMIN', 'ANALYST')),
    created_at TIMESTAMPTZ  NOT NULL DEFAULT now(),
    CONSTRAINT uk_users_email UNIQUE (email)
);