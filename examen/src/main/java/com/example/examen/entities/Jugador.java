package com.example.examen.entities;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Jugador { @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private double saldo;
    private boolean activo;
}