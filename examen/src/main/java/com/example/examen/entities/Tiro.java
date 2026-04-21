package com.example.examen.entities;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Tiro { @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int valorDado1;
    private int valorDado2;
    private int suma;
    private boolean esGanador;
    @ManyToOne
    @JoinColumn(name = "partida_id")
    private Partida partida;
}