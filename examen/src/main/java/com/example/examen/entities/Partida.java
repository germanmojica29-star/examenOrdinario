package com.example.examen.entities;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
public class Partida { @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime fecha;
    @Enumerated(EnumType.STRING)
    private EstadoPartida estado;

    private double apuesta;
    @ManyToOne
    @JoinColumn(name = "jugador_id")
    private Jugador jugador;
}