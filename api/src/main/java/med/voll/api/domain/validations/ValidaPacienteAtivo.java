package med.voll.api.domain.validations;

import med.voll.api.infra.exceptions.ValidacaoException;
import med.voll.api.domain.consultas.DadosAgendamentoConsulta;
import med.voll.api.domain.paciente.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidaPacienteAtivo implements ValidadorAgendamentoConsultas{

    @Autowired
    private PacienteRepository repository;

    public void validar(DadosAgendamentoConsulta dados){
        var pacienteAtivo = repository.findAtivoByID(dados.idPaciente());

        if(!pacienteAtivo){
            throw new ValidacaoException("Paciente inativo. Consulta não pode sera agendada");
        }
    }

}
