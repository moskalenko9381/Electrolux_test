package com.example.electrolux_test_moskalenko;

import com.example.electrolux_test_moskalenko.exception.MachineException;
import com.example.electrolux_test_moskalenko.model.machine.ActiveStatus;
import com.example.electrolux_test_moskalenko.model.machine.WashingMachine;
import com.example.electrolux_test_moskalenko.service.WashingMachineService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.Assert;

import static org.aspectj.bridge.MessageUtil.fail;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class WashingMachineServiceTest {

    @Autowired
    private WashingMachine washingMachine;

    @Autowired
    private WashingMachineService washingMachineService;

    @Test
    void turnOn() {
        String result = washingMachineService.turn();
        assertEquals("Washing machine was turned on.", result);
        assertEquals(ActiveStatus.ON, washingMachine.getMachineStatus());
    }

    @Test
    void turnOff() {
        washingMachine.setMachineStatus(ActiveStatus.ON);
        washingMachineService.turn();
        Assert.isNull(washingMachine.getTimeStart());
        Assert.isNull(washingMachine.getChosenProgram());
        assertEquals(ActiveStatus.OFF, washingMachine.getMachineStatus());
    }

    @Test
    void chooseProgramOutOfRange() {
        washingMachine.setMachineStatus(ActiveStatus.ON);
        try {
            washingMachineService.setProgram(5);
            fail("Expected MachineException");
        } catch (MachineException e) {
            assertThat(e.getMessage(), containsString("No such program!"));
        }
    }

    @Test
    void chooseProgram() throws MachineException {
        washingMachine.setMachineStatus(ActiveStatus.ON);
        washingMachine.setChosenProgram(null);
        String res = washingMachineService.setProgram(2);
        assertEquals("Set program: COTTON", res);
    }
}
