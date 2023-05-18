package med.voll.api.controllers;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.domain.consultas.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("consultas")
@SecurityRequirement(name = "bearer-key")
public class ConsultaController {

    @Autowired
    private AgendaDeConsultas agenda;

    @Autowired
    private ConsultaRepository repository;


    @GetMapping
    public ResponseEntity<List<DadosListagemConsulta>> listar() {
        return ResponseEntity.status(HttpStatus.OK).body(repository.findAll()
                .stream()
                .map(DadosListagemConsulta::new)
                .toList());

    }
    @PostMapping
    @Transactional
    public ResponseEntity agendar(@RequestBody @Valid DadosAgendamentoConsulta dados){
        var dadosAgenda = agenda.agendar(dados);
        return ResponseEntity.ok(dadosAgenda);
    }
    @DeleteMapping
    @Transactional
    public ResponseEntity cancelarConsulta(@RequestBody @Valid DadosCancelamentoConsulta dados){
        agenda.cancelarConsulta(dados);
        return ResponseEntity.noContent().build();
    }
}
