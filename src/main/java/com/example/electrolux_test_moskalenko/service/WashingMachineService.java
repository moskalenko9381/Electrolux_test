package com.example.electrolux_test_moskalenko.service;

import com.example.electrolux_test_moskalenko.exception.MachineException;
import com.example.electrolux_test_moskalenko.model.machine.ActiveStatus;
import com.example.electrolux_test_moskalenko.model.machine.WashingMachine;
import com.example.electrolux_test_moskalenko.model.program.ProgramType;
import com.example.electrolux_test_moskalenko.repository.WashingMachineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Service
public class WashingMachineService {

    @Autowired
    private WashingMachineRepository washingMachineRepository;

    @Autowired
    private WashingProgramsService washingProgramsService;

    @Autowired
    @Qualifier("machine")
    private WashingMachine washingMachine;

    public String setProgram(int number) throws MachineException {
        if (washingMachine.getMachineStatus() == ActiveStatus.OFF)
            return "First turn on washing machine!";
        else if (washingMachine.getMachineStatus() != ActiveStatus.ON)
            return "Machine is washing, you can't choose program now";

        switch (number) {
            case 0:
                washingMachine.setChosenProgram(washingProgramsService.getProgramFromRepo(ProgramType.QUICK));
                break;
            case 1:
                washingMachine.setChosenProgram(washingProgramsService.getProgramFromRepo(ProgramType.DELICATE));
                break;
            case 2:
                washingMachine.setChosenProgram(washingProgramsService.getProgramFromRepo(ProgramType.COTTON));
                break;
            case 3:
                washingMachine.setChosenProgram(washingProgramsService.getProgramFromRepo(ProgramType.ONLYSPIN));
                break;
            default:
                throw new MachineException("No such program!");
        }
        washingMachineRepository.save(washingMachine);
        return "Set program: " + washingMachine.getChosenProgram().getId();
    }

    public String pause() throws MachineException {
        if (washingMachine.getMachineStatus() == null ||
                washingMachine.getMachineStatus() == ActiveStatus.OFF)
            throw new MachineException("Turn on machine before!");

        if (washingMachine.getMachineStatus() == ActiveStatus.WORKING) {
            washingMachine.setMachineStatus(ActiveStatus.PAUSE);
            washingMachineRepository.save(washingMachine);
            return "Washing at pause.";
        }
        if (washingMachine.getMachineStatus() == ActiveStatus.PAUSE) {
            washingMachine.setMachineStatus(ActiveStatus.WORKING);
            washingMachineRepository.save(washingMachine);
            return "Washing machine is working.";
        }

        return "Can't be in pause because doesn't working.";
    }

    public String turn() {
        if (washingMachine.getMachineStatus() == ActiveStatus.OFF ||
                washingMachine.getMachineStatus() == null) {

            washingProgramsService.setProgramsToBase();

            washingMachine.setMachineStatus(ActiveStatus.ON);
            washingMachineRepository.save(washingMachine);
            return "Washing machine was turned on.";
        }
        return turnOff();
    }

    private String turnOff() {
        washingMachine.setTimeStart(null);
        washingMachine.setChosenProgram(null);
        washingMachine.setMachineStatus(ActiveStatus.OFF);
        washingMachineRepository.save(washingMachine);
        return "Washing machine was turned off.";
    }

    public String start() throws MachineException {
        if (washingMachine.getMachineStatus() == ActiveStatus.ON) {
            if (washingMachine.getTimeStart() == null || washingMachine.getTimeStart().format(DateTimeFormatter.ofPattern("HH:mm"))
                    .equals(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm")))) {
                if (washingMachine.getChosenProgram() == null)
                    throw new MachineException("Set program first");
                String result = washingMachine.getChosenProgram().processProgram();
                turnOff();
                return result;
            }

            return "Washing program will start at " + washingMachine.getTimeStart();
        }

        throw new MachineException("Program has already started or turn on your machine!");
    }


    public void setDelayStart(LocalTime localTime) throws MachineException {
        if (washingMachine.getChosenProgram() == null)
            throw new MachineException("Please choose program before start");
        try {
            washingMachine.setTimeStart(localTime);
        } catch (Exception e) {
            throw new MachineException("Mistake of setting delayed time start");
        }
    }
}
