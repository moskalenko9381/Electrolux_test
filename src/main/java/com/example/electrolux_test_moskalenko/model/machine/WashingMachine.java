package com.example.electrolux_test_moskalenko.model.machine;

import com.example.electrolux_test_moskalenko.model.program.WashingProgram;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "MACHINE")
public class WashingMachine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "selected_program", referencedColumnName = "id")
    private WashingProgram chosenProgram;

    @Basic
    private java.time.LocalTime timeStart;

    @Column(name = "status")
    private ActiveStatus machineStatus;

    @Column(name = "washing_class")
    private String model;

    public WashingMachine(WashingProgram p, ActiveStatus s, String m) {
        chosenProgram = p;
        machineStatus = s;
        model = m;
    }
}
