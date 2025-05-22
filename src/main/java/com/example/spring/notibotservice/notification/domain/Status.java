package com.example.spring.notibotservice.notification.domain;

public enum Status {
    PENDING,   // 예약 또는 즉시 등록됨 (아직 Kafka에 안 보냄)
    QUEUED,    // Kafka에 올라간 상태 (Consumer에서 처리 중이거나 예정)
    SENT,      // 전체 전송 완료
    FAILED     // 전체 실패 (예: 유저 조회 실패 등)
}
