package med.voll.api.domain.validations.cancelamento;

import med.voll.api.domain.consultas.DadosCancelamentoConsulta;

public interface ValidaCancelamentoConsulta {

    void validar(DadosCancelamentoConsulta dados);
}
