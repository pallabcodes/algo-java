package com.backend.designpatterns.realworld.eventpipeline;

import java.time.Instant;

/**
 * [1/8] Canonical event envelope — the uniform data type flowing through EventPipeline.
 * All EventSource adapters (PubSub, Kafka, CloudTasks) normalize to this record.
 * Without a canonical type: Chain handlers would need to handle source-specific
 * formats, defeating the purpose of the Adapter pattern.
 */
public record Event(
    String id,
    String type,
    String source,
    byte[] payload,
    Instant timestamp,
    String partitionKey
) {}
