package com.example.electrolux_test_moskalenko.repository;

import com.example.electrolux_test_moskalenko.model.program.ProgramType;
import com.example.electrolux_test_moskalenko.model.program.WashingProgram;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WashingProgramRepository extends CrudRepository<WashingProgram, ProgramType> {
    List<WashingProgram> findAll();
}
