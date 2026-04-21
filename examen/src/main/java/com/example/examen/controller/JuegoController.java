package com.example.examen.controller;
import com.example.examen.dto.*;
import com.example.examen.dto.InicioPartidaDTO;
import com.example.examen.entities.Partida;
import com.example.examen.entities.Tiro;
import com.example.examen.repository.JuegoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/juego")

public class JuegoController { @Autowired private JuegoService service;
    @PostMapping("/iniciar")
    public ResponseEntity<Partida> iniciar(@RequestBody InicioPartidaDTO dto) {
        return ResponseEntity.ok(service.iniciarPartida(dto));

    } @PostMapping("/tiro/{id}")
    public ResponseEntity<Tiro> tirar(@PathVariable Long id) {
        return ResponseEntity.ok(service.realizarTiro(id));

    } @PostMapping("/recargar")
    public ResponseEntity<String> recargar(@RequestBody RecargaSaldoDTO dto) {
        service.recargar(dto.jugadorId(), dto.monto());
        return ResponseEntity.ok("saldo nuevo");

    } @PatchMapping("/finalizar/{id}")
    public ResponseEntity<Void> finalizar(@PathVariable Long id) {
        service.finalizarManual(id);
        return ResponseEntity.ok().build();

    } @GetMapping("/historial/{id}")
    public ResponseEntity<List<Partida>> historial(@PathVariable Long id) {
        return ResponseEntity.ok(service.historial(id));
    }}
