package com.example.electrolux_test_moskalenko;

import com.example.electrolux_test_moskalenko.model.program.ProgramType;
import com.example.electrolux_test_moskalenko.model.program.WashingProgram;
import com.example.electrolux_test_moskalenko.repository.WashingProgramRepository;
import com.example.electrolux_test_moskalenko.service.WashingProgramsService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.Assert;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class WashingProgramsServiceTest {

    @Autowired
    private WashingProgramsService washingProgramsService;

    @Autowired
    private WashingProgramRepository washingProgramRepository;

    @Test
    void addProgramsToBase() {
        washingProgramsService.setProgramsToBase();
        Assert.notEmpty(washingProgramRepository.findAll(), "Programs repository can't be empty");
        assertEquals(4, washingProgramRepository.findAll().size());
    }

    @Test
    void getOneProgram() {
        washingProgramsService.setProgramsToBase();
        WashingProgram program = washingProgramsService.getProgramFromRepo(ProgramType.ONLYSPIN);
        Assert.notNull(program, "Mistake with programs repository");
    }
}
