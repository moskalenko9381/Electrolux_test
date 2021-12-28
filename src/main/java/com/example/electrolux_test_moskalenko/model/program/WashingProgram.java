package com.example.electrolux_test_moskalenko.model.program;

import com.example.electrolux_test_moskalenko.exception.MachineException;
import com.example.electrolux_test_moskalenko.model.process.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Data
@Entity
@Table(name = "PROGRAM")
@NoArgsConstructor
public class WashingProgram {

    @Id
    private ProgramType id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "drying_id", referencedColumnName = "id")
    private Drying drying;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "washing_id", referencedColumnName = "id")
    private Washing washing;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "rinsing_id", referencedColumnName = "id")
    private Rinsing rinsing;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "spinning_id", referencedColumnName = "id")
    private Spinning spinning;

    public WashingProgram(ProgramType type, Washing washing, Rinsing rinsing, Spinning spinning, Drying drying) {
        this.id = type;
        this.washing = washing;
        this.rinsing = rinsing;
        this.spinning = spinning;
        this.drying = drying;
    }

    public String processProgram() throws MachineException {
        List<IProcess> processes = new ArrayList<>();
        if (washing != null) processes.add(washing);
        if (rinsing != null) processes.add(rinsing);
        if (spinning != null) processes.add(spinning);
        if (drying != null) processes.add(drying);
        StringBuilder result = new StringBuilder();
        result.append("Started.\n");

        for (IProcess entry : processes) {
            result.append(entry.doProcess() + '\n');
        }

        result.append("Finished.");
        return result.toString();
    }

}
