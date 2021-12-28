package com.example.electrolux_test_moskalenko.repository;

import com.example.electrolux_test_moskalenko.model.machine.WashingMachine;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WashingMachineRepository extends CrudRepository<WashingMachine, Integer> {
}
