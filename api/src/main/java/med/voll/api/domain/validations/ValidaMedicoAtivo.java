package med.voll.api.domain.validations;

import med.voll.api.infra.exceptions.ValidacaoException;
import med.voll.api.domain.consultas.DadosAgendamentoConsulta;
import med.voll.api.domain.medico.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidaMedicoAtivo implements ValidadorAgendamentoConsultas{
    @Autowired
    private MedicoRepository repository;

    public void validar(DadosAgendamentoConsulta dados){
        if (dados.idMedico() == null ) {
            return;
        }
        var medicoAtivo = repository.findAtivoByID(dados.idMedico());

        if(!medicoAtivo){
            throw new ValidacaoException("Médico inativo. Consulta não pode ser agendada");
        }
    }
}
