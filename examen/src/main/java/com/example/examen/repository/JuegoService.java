package com.example.examen.repository;
import com.example.examen.dto.InicioPartidaDTO;
import com.example.examen.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Service
public class JuegoService {
    @Autowired private JugadorRepository jugadorRepo;
    @Autowired private PartidaRepository partidaRepo;
    @Autowired private TiroRepository tiroRepo;
    @Transactional

    public Partida iniciarPartida(InicioPartidaDTO dto) {
        System.out.println("preparate inicia partidasaaa...");
        Jugador jugador = jugadorRepo.findById(dto.jugadorId()).orElseThrow(() -> new RuntimeException("jugador no esta"));

        if (jugador.isActivo() == true) {
            throw new RuntimeException("Jugador esta inactivo");
        } else {
            if (jugador.getSaldo() < dto.apuesta()) {
                throw new RuntimeException("no tienes saldito");
            } }
        double saldoNuevo = jugador.getSaldo() - dto.apuesta();
        jugador.setSaldo(saldoNuevo);
        jugadorRepo.save(jugador);
        Partida partida = new Partida();
        partida.setJugador(jugador);
        partida.setFecha(LocalDateTime.now());
        partida.setEstado(EstadoPartida.EN_JUEGO);
        partida.setApuesta(dto.apuesta());
        Partida res = partidaRepo.save(partida);
        return res;
    }
    @Transactional
    public Tiro realizarTiro(Long partidaId) {
        Partida partida = partidaRepo.findById(partidaId).orElseThrow(() -> new RuntimeException("no se encontro partida"));
        if (partida.getEstado() != EstadoPartida.EN_JUEGO) {
            throw new RuntimeException("no se inicio partida tio");
        }
        Random r = new Random();
        int d1 = r.nextInt(6) + 1;
        int d2 = r.nextInt(6) + 1;
        int suma = d1 + d2;

        Tiro t = new Tiro();
        t.setPartida(partida);
        t.setValorDado1(d1);
        t.setValorDado2(d2);
        t.setSuma(suma);

        if (suma == 7 || suma == 11) {
            t.setEsGanador(true);
            partida.setEstado(EstadoPartida.FINALIZADA);

            Jugador jugadorDeLaPartida = partida.getJugador();
            double premio = partida.getApuesta() * 2;
            double saldoFinal = jugadorDeLaPartida.getSaldo() + premio;

            jugadorDeLaPartida.setSaldo(saldoFinal);
            jugadorRepo.save(jugadorDeLaPartida);
        } else {
            if (suma == 2 || suma == 3 || suma == 12) {
                t.setEsGanador(false);
                partida.setEstado(EstadoPartida.FINALIZADA);
            } else {
                t.setEsGanador(false);
            }
        }
        partidaRepo.save(partida);
        Tiro tiroFinal = tiroRepo.save(t);
        return tiroFinal;

    } @Transactional
    public void finalizarManual(Long id) {
        Partida p = partidaRepo.findById(id).get();
        p.setEstado(EstadoPartida.FINALIZADA);
        partidaRepo.save(p);

    }@Transactional
    public void recargar(Long id, double monto) {
        Jugador j = jugadorRepo.findById(id).orElseThrow();
        double saldoAnterior = j.getSaldo();
        double saldoActualizado = saldoAnterior + monto;
        j.setSaldo(saldoActualizado);
        jugadorRepo.save(j);
    }
    public List<Partida> historial(Long id) {
        List<Partida> miLista = partidaRepo.findByJugadorId(id);
        return miLista;
    }
}