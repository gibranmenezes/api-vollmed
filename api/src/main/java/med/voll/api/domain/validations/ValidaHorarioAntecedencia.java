package med.voll.api.domain.validations;

import med.voll.api.infra.exceptions.ValidacaoException;
import med.voll.api.domain.consultas.DadosAgendamentoConsulta;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class ValidaHorarioAntecedencia implements ValidadorAgendamentoConsultas{
    public  void validar(DadosAgendamentoConsulta dados) {
        var dataConsulta = dados.data();
        var agora = LocalDateTime.now();
        var intervaloMinutos = Duration.between(dataConsulta, agora).toMinutes();

        if (intervaloMinutos > 30) {
            throw new ValidacaoException("Consulta deve ser marcada com antecedência mínima de 30 minutos");
        }
    }
}
