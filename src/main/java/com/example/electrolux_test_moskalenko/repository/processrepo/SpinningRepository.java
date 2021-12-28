package com.example.electrolux_test_moskalenko.repository.processrepo;

import com.example.electrolux_test_moskalenko.model.process.Spinning;
import com.example.electrolux_test_moskalenko.model.program.ProgramType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpinningRepository extends CrudRepository<Spinning, ProgramType> {
}