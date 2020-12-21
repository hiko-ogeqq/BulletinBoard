package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DivisionRepository extends JpaRepository<Division, Long> {

  public Division findById(int id);
}
