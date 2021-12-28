package com.example.electrolux_test_moskalenko.controller;

import com.example.electrolux_test_moskalenko.exception.MachineException;
import com.example.electrolux_test_moskalenko.model.machine.WashingMachine;
import com.example.electrolux_test_moskalenko.model.program.WashingProgram;
import com.example.electrolux_test_moskalenko.service.WashingMachineService;
import com.example.electrolux_test_moskalenko.service.WashingProgramsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.List;

@RestController
@Component
@Api("RestController for washing machine appliance")
public class Controller {

    @Autowired
    @Qualifier("machine")
    private WashingMachine washingMachine;

    @Autowired
    private WashingMachineService washingMachineService;

    @Autowired
    private WashingProgramsService washingProgramsService;

    @ApiOperation(value = "Turn on or turn off machine")
    @PutMapping(value = "/turn-on-off")
    public ResponseEntity<String> turnOn() {
        return ResponseEntity.ok(washingMachineService.turn());
    }

    @ApiOperation(value = "Get all programs")
    @GetMapping(value = "/programs", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<WashingProgram>> getAllPrograms() {
        try {
            List<WashingProgram> programs = washingProgramsService.getPrograms();
            return new ResponseEntity<>(programs, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @ApiOperation(value = "Get info about machine")
    @GetMapping(value = "/machine-info", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity info() {
        return ResponseEntity.ok(washingMachine);
    }

    @ApiOperation(value = "Pause")
    @PutMapping(value = "/pause")
    public ResponseEntity<String> pause() {
        try {
            return ResponseEntity.ok(washingMachineService.pause());
        } catch (MachineException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation(value = "Choosing program")
    @GetMapping(value = "/choose-program")
    public ResponseEntity<String> setProgram(@ApiParam(value = "0 - QUICK, 1 - DELICATE, 2 - COTTON, 3 - ONLYSPIN") @RequestParam int number) {
        try {
            return ResponseEntity.ok(washingMachineService.setProgram(number));
        } catch (MachineException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    @ApiOperation(value = "Starting program")
    @GetMapping(value = "/start")
    public ResponseEntity startingProgram() {
        try {
            return ResponseEntity.ok(washingMachineService.start());
        } catch (MachineException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation(value = "Set delayed time of start")
    @PostMapping(value = "/delay-start")
    public ResponseEntity delay(@ApiParam(value = "Enter time in HH:mm format") @RequestParam("localTime") LocalTime localTime) {
        try {
            washingMachineService.setDelayStart(localTime);
            return ResponseEntity.ok(washingMachine);
        } catch (MachineException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}