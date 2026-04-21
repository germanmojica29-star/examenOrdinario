package com.example.examen.repository;

import com.example.examen.entities.Tiro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TiroRepository extends JpaRepository<Tiro, Long> {


}