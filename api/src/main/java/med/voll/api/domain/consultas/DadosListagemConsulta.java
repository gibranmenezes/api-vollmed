package med.voll.api.domain.consultas;

import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.paciente.Paciente;

import java.time.LocalDateTime;

public record DadosListagemConsulta(Long id, String nomePaciente, String nomeMedico, LocalDateTime dataConsulta) {

    public DadosListagemConsulta(Consulta consulta) {
        this(consulta.getId(), consulta.getPaciente().getNome(), consulta.getMedico().getNome(), consulta.getData());
    }

}
