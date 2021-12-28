package com.example.electrolux_test_moskalenko.service;

import com.example.electrolux_test_moskalenko.model.process.Drying;
import com.example.electrolux_test_moskalenko.model.process.Rinsing;
import com.example.electrolux_test_moskalenko.model.process.Spinning;
import com.example.electrolux_test_moskalenko.model.process.Washing;
import com.example.electrolux_test_moskalenko.model.program.ProgramType;
import com.example.electrolux_test_moskalenko.model.program.WashingProgram;
import com.example.electrolux_test_moskalenko.repository.WashingProgramRepository;
import com.example.electrolux_test_moskalenko.repository.processrepo.DryingRepository;
import com.example.electrolux_test_moskalenko.repository.processrepo.RinsingRepository;
import com.example.electrolux_test_moskalenko.repository.processrepo.SpinningRepository;
import com.example.electrolux_test_moskalenko.repository.processrepo.WashingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WashingProgramsService {
    @Autowired
    WashingRepository washingRepository;

    @Autowired
    RinsingRepository rinsingRepository;

    @Autowired
    SpinningRepository spinningRepository;

    @Autowired
    DryingRepository dryingRepository;

    @Autowired
    WashingProgramRepository washingProgramRepository;

    public List<WashingProgram> getPrograms() {
        List<WashingProgram> programs = new ArrayList<>();
        programs.add(getProgramBuilder(ProgramType.QUICK));
        programs.add(getProgramBuilder(ProgramType.DELICATE));
        programs.add(getProgramBuilder(ProgramType.COTTON));
        programs.add(getProgramBuilder(ProgramType.ONLYSPIN));
        return programs;
    }

    public void setProgramsToBase() {
        List<WashingProgram> programs = getPrograms();
        washingProgramRepository.saveAll(programs);
    }

    public WashingProgram getProgramFromRepo(ProgramType type) {
        return getProgramBuilder(type);
    }

    public WashingProgram getProgramBuilder(ProgramType type) {
        switch (type) {
            case DELICATE: {
                if (washingProgramRepository.findById(type).isEmpty())
                    return settingsOfProgram(type, 15, 50, 30, 30, 15, 30, 10, 400);
                else
                    return washingProgramRepository.findById(type).get();
            }

            case QUICK: {
                if (washingProgramRepository.findById(type).isEmpty())
                    return settingsOfProgram(type, 15, 40, 10, 30, 5, 1200, 0, 0);
                else
                    return washingProgramRepository.findById(type).get();
            }
            case COTTON: {

                if (washingProgramRepository.findById(type).isEmpty())
                    return settingsOfProgram(type, 30, 50, 15, 40, 10, 1000, 20, 60);
                else
                    return washingProgramRepository.findById(type).get();
            }
            case ONLYSPIN: {
                if (washingProgramRepository.findById(type).isEmpty())
                    return settingsOfProgram(type, 0, 0, 0, 0, 10, 800, 0, 0);
                else
                    return washingProgramRepository.findById(type).get();
            }
            default:
                return null;
        }
    }

    private WashingProgram settingsOfProgram(ProgramType type, int wash1, int wash2,
                                             int rins1, int rins2, int s1, int s2, int d1, int d2) {
        Washing washing = new Washing();
        if (washingRepository.findById(type).isEmpty()) {
            washing = new Washing(type, wash1, wash2);
            if (wash1 == 0 && wash2 == 0)
                washing = null;
            else washingRepository.save(washing);
        }

        Rinsing rinsing = new Rinsing();
        if (rinsingRepository.findById(type).isEmpty()) {
            rinsing = new Rinsing(type, rins1, rins2);
            if (rins1 == 0 && rins2 == 0)
                rinsing = null;
            else rinsingRepository.save(rinsing);
        }

        Spinning spinning = new Spinning();
        if (spinningRepository.findById(type).isEmpty()) {
            spinning = new Spinning(type, s1, s2);
            if (s1 == 0 && s2 == 0)
                spinning = null;
            else spinningRepository.save(spinning);
        }

        Drying drying = new Drying();
        if (dryingRepository.findById(type).isEmpty()) {
            drying = new Drying(type, d1, d2);
            if (d1 == 0 && d2 == 0)
                drying = null;
            else dryingRepository.save(drying);
        }

        WashingProgram newProgram = new WashingProgram(type, washing, rinsing, spinning, drying);
        washingProgramRepository.save(newProgram);
        return newProgram;
    }

}
