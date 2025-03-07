package com.example.CRM.common.util;

public class SnowflakeIdGenerator {
    private static final long EPOCH = 1700000000000L; // Epoch cố định
    private static final long WORKER_ID = 1; // Worker ID (cấu hình theo môi trường)
    private static final long SEQUENCE_BITS = 12;
    private static final long WORKER_ID_BITS = 10;
    private static final long MAX_WORKER_ID = (1L << WORKER_ID_BITS) - 1;
    private static final long MAX_SEQUENCE = (1L << SEQUENCE_BITS) - 1;

    private static final long WORKER_ID_SHIFT = SEQUENCE_BITS;
    private static final long TIMESTAMP_SHIFT = SEQUENCE_BITS + WORKER_ID_BITS;

    private long sequence = 0L;
    private long lastTimestamp = -1L;

    private static final SnowflakeIdGenerator instance = new SnowflakeIdGenerator(WORKER_ID);

    public static SnowflakeIdGenerator getInstance() {
        return instance;
    }

    private SnowflakeIdGenerator(long workerId) {
        if (workerId < 0 || workerId > MAX_WORKER_ID) {
            throw new IllegalArgumentException("Worker ID không hợp lệ: " + workerId);
        }
    }

    public synchronized long nextId() {
        long timestamp = System.currentTimeMillis();

        if (timestamp < lastTimestamp) {
            throw new RuntimeException("Clock moved backwards! Không thể tạo ID.");
        }

        if (timestamp == lastTimestamp) {
            sequence = (sequence + 1) & MAX_SEQUENCE;
            if (sequence == 0) {
                timestamp = waitNextMillis(lastTimestamp);
            }
        } else {
            sequence = 0L;
        }

        lastTimestamp = timestamp;

        return ((timestamp - EPOCH) << TIMESTAMP_SHIFT) | (WORKER_ID << WORKER_ID_SHIFT) | sequence;
    }

    private long waitNextMillis(long lastTimestamp) {
        long timestamp = System.currentTimeMillis();
        while (timestamp <= lastTimestamp) {
            timestamp = System.currentTimeMillis();
        }
        return timestamp;
    }
}
