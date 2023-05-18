package med.voll.api.domain.validations.cancelamento;

import med.voll.api.domain.consultas.ConsultaRepository;
import med.voll.api.domain.consultas.DadosCancelamentoConsulta;
import med.voll.api.infra.exceptions.ValidacaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class ValidaHorarioCancelamento implements ValidaCancelamentoConsulta {

    @Autowired
    private ConsultaRepository repository;

    public void validar(DadosCancelamentoConsulta dados) {
        var agora = LocalDateTime.now();
        var dataConsulta = repository.getReferenceById(dados.idConsulta()).getData();
        var intervaloAntecedencia = Duration.between(agora, dataConsulta).toHours();

        if (intervaloAntecedencia < 24 ) {
            throw new ValidacaoException("Cancelamento só é possível com antecedência mínima de 24hrs");
        }
    }
}
