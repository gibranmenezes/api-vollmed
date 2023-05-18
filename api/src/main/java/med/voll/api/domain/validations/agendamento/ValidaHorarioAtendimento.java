package med.voll.api.domain.validations.agendamento;

import med.voll.api.infra.exceptions.ValidacaoException;
import med.voll.api.domain.consultas.DadosAgendamentoConsulta;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;

@Component
public class ValidaHorarioAtendimento implements ValidadorAgendamentoConsultas{

    private final Integer HORARIO_INICIO = 7;
    private final Integer HORARIO_ENCERRAMENTO = 18;

    public  void validar(DadosAgendamentoConsulta dados) {
        var dataConsulta = dados.data();
        var domingo = dataConsulta.getDayOfWeek().equals(DayOfWeek.SUNDAY);
        var antesDoExpediente = dataConsulta.getHour() < this.HORARIO_INICIO;
        var depoisDoExpediente = dataConsulta.getHour() > this.HORARIO_ENCERRAMENTO;

        if (domingo || antesDoExpediente || depoisDoExpediente) {
            throw new ValidacaoException("Consulta fora do horário de expediente");
        }
    }
}
