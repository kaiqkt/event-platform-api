package com.kaiqkt.eventplatform.application.config;

import io.micrometer.core.instrument.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;

@Component
public class Metrics {
    private final MeterRegistry meterRegistry;

    @Autowired
    public Metrics(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
    }

    // This function creates and increments a Counter metric.
    // Counter is a simple incrementing and decrementing metric.
    public void increment(String name, String... tags) {
        Counter counter = Counter.builder(name)
                .tags(tags)
                .register(meterRegistry);
        counter.increment();
    }

    // This function creates a Gauge metric.
    // Gauge is a metric that represents a single numerical value that can arbitrarily go up and down.
    public Number gauge(String name, Supplier<Number> supplier, String... tags) {
        return Gauge.builder(name, supplier)
                .tags(tags)
                .register(meterRegistry)
                .value();
    }

    // This function creates a Timer metric and records the time it takes to execute the provided Supplier.
    // Timer is a metric that measures short durations of time.
    public <T> T timer(String name, Supplier<T> supplier, String... tags) {
        Timer timer = Timer.builder(name).tags(tags).register(meterRegistry);
        Timer.Sample sample = Timer.start(meterRegistry);
        try {
            return supplier.get();
        } finally {
            sample.stop(timer);
        }
    }
    public void timer(String name, Runnable runnable, String... tags) {
        Timer timer = Timer.builder(name).tags(tags).register(meterRegistry);
        Timer.Sample sample = Timer.start(meterRegistry);
        try {
            runnable.run();
        } finally {
            sample.stop(timer);
        }
    }

    // This function creates a Histogram metric and records a value.
    // Histogram is a metric that measures the statistical distribution of values in a stream of data.
    public void histogram(String name, double amount, String... tags) {
        DistributionSummary summary = DistributionSummary.builder(name)
                .tags(tags)
                .register(meterRegistry);
        summary.record(amount);
    }
}
