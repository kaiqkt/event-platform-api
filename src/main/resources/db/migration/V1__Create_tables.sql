CREATE TABLE IF NOT EXISTS producer
(
    id         VARCHAR(255) PRIMARY KEY,
    service    VARCHAR(255) NOT NULL,
    action     VARCHAR(255) NOT NULL,
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);

CREATE TABLE IF NOT EXISTS version
(
    id          VARCHAR(255) PRIMARY KEY,
    value       INTEGER      NOT NULL,
    schema      VARCHAR(255) NOT NULL,
    producer_id VARCHAR(255),
    created_at  TIMESTAMP,
    FOREIGN KEY (producer_id) REFERENCES producer (id)
);

CREATE TABLE IF NOT EXISTS consumer
(
    id           VARCHAR(255) PRIMARY KEY,
    service      VARCHAR(255) NOT NULL,
    url          VARCHAR(255) NOT NULL,
    content_type VARCHAR(255) NOT NULL,
    version_id   VARCHAR(255),
    created_at   TIMESTAMP,
    FOREIGN KEY (version_id) REFERENCES version (id)
);

CREATE INDEX idx_producer_service ON producer(service);
CREATE INDEX idx_producer_action ON producer(action);
CREATE INDEX idx_version_value ON version(value);
CREATE INDEX idx_consumer_version_id ON consumer(version_id);
