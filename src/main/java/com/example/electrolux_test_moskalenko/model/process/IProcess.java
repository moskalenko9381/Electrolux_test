package com.example.electrolux_test_moskalenko.model.process;


import com.example.electrolux_test_moskalenko.exception.MachineException;

public interface IProcess {
    String doProcess() throws MachineException;
}

