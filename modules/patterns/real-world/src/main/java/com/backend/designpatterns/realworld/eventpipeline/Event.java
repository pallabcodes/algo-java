package com.backend.designpatterns.realworld.eventpipeline;

import java.time.Instant;

public record Event(
    String id,
    String type,
    String source,
    byte[] payload,
    Instant timestamp,
    String partitionKey
) {}
