package com.example.electrolux_test_moskalenko;

import com.example.electrolux_test_moskalenko.model.program.ProgramType;
import com.example.electrolux_test_moskalenko.model.program.WashingProgram;
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
class ProgramBuilderTest {

    @Autowired
    WashingProgramsService washingProgramsService;

    @Test
    void getProgram() {
        WashingProgram washingProgram = washingProgramsService.getProgramBuilder(ProgramType.DELICATE);
        Assert.notNull(washingProgram.getWashing(), "Washing can't be null");
        assertEquals(ProgramType.DELICATE, washingProgram.getId());
        assertEquals(ProgramType.DELICATE, washingProgram.getDrying().getId());
    }
}
