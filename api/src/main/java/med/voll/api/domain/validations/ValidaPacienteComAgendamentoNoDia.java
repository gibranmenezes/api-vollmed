package med.voll.api.domain.validations;

import med.voll.api.infra.exceptions.ValidacaoException;
import med.voll.api.domain.consultas.ConsultaRepository;
import med.voll.api.domain.consultas.DadosAgendamentoConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidaPacienteComAgendamentoNoDia implements ValidadorAgendamentoConsultas{

    private final Integer HORARIO_INICIO = 7;
    private final Integer HORARIO_ENCERRAMENTO = 18;

    @Autowired
    private ConsultaRepository repository;

    public void validar(DadosAgendamentoConsulta dados){
        var horarioInicio = dados.data().withHour(this.HORARIO_INICIO);
        var horarioEncerramento = dados.data().withHour(this.HORARIO_ENCERRAMENTO);
        var pacienteAgendadoNoDia = repository.existsByPacienteIdAndDataBetween(dados.idPaciente(), horarioInicio, horarioEncerramento);

        if (pacienteAgendadoNoDia) {
            throw new ValidacaoException("Paciente já possui consulta agendada para o dia");
        }

    }
}
