package com.example.electrolux_test_moskalenko;

import com.example.electrolux_test_moskalenko.model.machine.ActiveStatus;
import com.example.electrolux_test_moskalenko.model.machine.WashingMachine;
import com.example.electrolux_test_moskalenko.model.program.ProgramType;
import com.example.electrolux_test_moskalenko.service.WashingProgramsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ControllerTests {

    @Autowired
    WashingMachine washingMachine;
    @Autowired
    WashingProgramsService washingProgramsService;
    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setOnStatus() {
        washingMachine.setMachineStatus(ActiveStatus.ON);
    }

    @Test
    void getInfoTest() throws Exception {
        mockMvc.perform(
                        get("/machine-info"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.model").value("Electrolux EW6S4R 06 W"));
    }

    @Test
    void getProgramsTest() throws Exception {
        mockMvc.perform(
                        get("/programs"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is("QUICK")))
                .andExpect(jsonPath("$[1].drying.timeDuration", is(10)));

    }

    @Test
    void setProgramValid() throws Exception {
        mockMvc.perform(
                        get("/choose-program").param("number", String.valueOf(2)))
                .andExpect(status().isOk());
    }

    @Test
    void setProgramInvalid() throws Exception {
        mockMvc.perform(
                        get("/choose-program").param("number", "fl"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void setDelayedTime() throws Exception {
        washingMachine.setChosenProgram(washingProgramsService.getProgramBuilder(ProgramType.QUICK));
        mockMvc.perform(
                        post("/delay-start").param("localTime", "12:56"))
                .andExpect(status().isOk());
        assertEquals("12:56", washingMachine.getTimeStart().toString());
    }

    @Test
    void setDelayedTimeInvalid() throws Exception {
        washingMachine.setChosenProgram(washingProgramsService.getProgramBuilder(ProgramType.QUICK));
        mockMvc.perform(
                        post("/delay-start").param("localTime", "12:566"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void startTest() throws Exception {
        washingMachine.setChosenProgram(washingProgramsService.getProgramBuilder(ProgramType.QUICK));
        mockMvc.perform(
                        get("/start"))
                .andExpect(status().isOk());
    }

}
