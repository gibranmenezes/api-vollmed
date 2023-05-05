package med.voll.api.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.medico.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/medicos")
public class MedicoController {

    @Autowired
    private MedicoRepository medicoRepository;

    @PostMapping
    @Transactional
    public void cadastrar(@RequestBody @Valid DadosCadastroMedico dados){
        medicoRepository.save(new Medico(dados));

    }
   /* @GetMapping //sem paginacao
    public List<DadosListagemMedico> listar() {
        return medicoRepository.findAll()
                .stream()
                .map(DadosListagemMedico::new).toList();
    }*/

    @GetMapping //com paginacao o param Pageable é opcional pode ter o param @PageableDefault
    public Page<DadosListagemMedico> listar(@PageableDefault(size = 10, sort = {"crm"}) Pageable paginacao) {
        return medicoRepository.findAll(paginacao).map(DadosListagemMedico::new);
    }

    @GetMapping("/{ativo}")//com paginacao o param Pageable é opcional pode ter o param @PageableDefault
    public Page<DadosListagemMedico> listarAtivos(@PageableDefault(size = 10, sort = {"crm"}) Pageable paginacao) {
        return medicoRepository.findAllByAtivoTrue(paginacao).map(DadosListagemMedico::new);
    }

   @PutMapping
   @Transactional
    public void atualizar(@RequestBody @Valid DadosAtualizacaoMedico dados){
        var medico = medicoRepository.getReferenceById(dados.id());
        medico.atualizarInformacoes(dados);
    }
    /*@DeleteMapping("/{id}") //exclusao
    @Transactional
    public void excluir(@PathVariable Long id) {
        medicoRepository.deleteById(id);
    }*/

    @DeleteMapping("/{id}") //exclusao
    @Transactional
    public void excluir(@PathVariable Long id) {
        var medico = medicoRepository.getReferenceById(id);
        medico.inativar();
    }
}
