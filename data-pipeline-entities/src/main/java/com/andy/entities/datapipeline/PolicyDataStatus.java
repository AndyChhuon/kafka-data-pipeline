package com.andy.entities.datapipeline;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum PolicyDataStatus {
    READY_TO_PROCESS("READY_TO_PROCESS"),
    IN_PROGRESS("IN_PROGRESS"),
    READY_TO_SEND("READY_TO_SEND"),
    REJECTED("REJECTED"),
    SENT("SENT");

    private final String value;
}
