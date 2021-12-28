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

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Spinning implements IProcess {

    @Id
    private ProgramType id;

    @Unsigned
    private Integer timeDuration; // time in minutes

    @Min(400)
    @Max(1200)
    private Integer rotation; // rotations/minute

    public Spinning(Integer timeDuration, Integer rotation) {
        this.timeDuration = timeDuration;
        this.rotation = rotation;
    }

    @Override
    public String doProcess() throws MachineException {
        try {
            return "Spinning started...";
        } catch (Exception e) {
            throw new MachineException("Mistake while spinning!");
        }
    }
}
