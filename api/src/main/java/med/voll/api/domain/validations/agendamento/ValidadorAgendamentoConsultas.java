package med.voll.api.domain.validations.agendamento;

import med.voll.api.domain.consultas.DadosAgendamentoConsulta;

public interface ValidadorAgendamentoConsultas {

    void validar(DadosAgendamentoConsulta dados);
}
