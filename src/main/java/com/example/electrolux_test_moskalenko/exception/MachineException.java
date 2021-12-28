package com.example.electrolux_test_moskalenko.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class MachineException extends Exception {
    // Constructor that accepts a message
    public MachineException(String message) {
        super(message);
    }
}

