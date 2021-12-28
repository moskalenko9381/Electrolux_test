package com.example.electrolux_test_moskalenko.model.process;

import com.example.electrolux_test_moskalenko.exception.MachineException;
import com.example.electrolux_test_moskalenko.model.program.ProgramType;
import jdk.jfr.Unsigned;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;


@AllArgsConstructor
@Data
@Entity
@NoArgsConstructor
public class Rinsing implements IProcess {

    @Id
    private ProgramType id;
    @Unsigned
    private Integer timeDuration; // time in minutes
    @Min(30)
    @Max(60)
    private Integer temperature;  //  in celsius degrees

    public Rinsing(Integer timeDuration, Integer temperature) {
        this.timeDuration = timeDuration;
        this.temperature = temperature;
    }

    @Override
    public String doProcess() throws MachineException {
        try {
            return "Rinsing started...";
        } catch (Exception e) {
            throw new MachineException("Mistake while rinsing!");
        }
    }

}
