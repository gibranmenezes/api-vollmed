package med.voll.api.domain.validations;

import med.voll.api.infra.exceptions.ValidacaoException;
import med.voll.api.domain.consultas.ConsultaRepository;
import med.voll.api.domain.consultas.DadosAgendamentoConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidaHorarioIndisponivelMedico implements ValidadorAgendamentoConsultas{

    @Autowired
    private ConsultaRepository repository;


    public void validar(DadosAgendamentoConsulta dados){
        var horarioIndiponivel = repository.existsByMedicoIdAndData(dados.idMedico(), dados.data());

        if ( horarioIndiponivel ) {
            throw new ValidacaoException("Horário indisponível para o médico.");
        }
    }
}
