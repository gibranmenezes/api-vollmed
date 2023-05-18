package med.voll.api.domain.consultas;

import med.voll.api.infra.exceptions.ValidacaoException;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.medico.MedicoRepository;
import med.voll.api.domain.paciente.PacienteRepository;
import med.voll.api.domain.validations.ValidadorAgendamentoConsultas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgendaDeConsultas {

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private List<ValidadorAgendamentoConsultas> validadores;


    public DadosDetalhamentoConsulta agendar(DadosAgendamentoConsulta dados){
        if ( !pacienteRepository.existsById(dados.idPaciente())
            || ( dados.idMedico() != null
                && !medicoRepository.existsById(dados.idMedico()))) {

            throw new ValidacaoException("ID não encontrado");
        }

        validadores.forEach(v -> v.validar(dados)); // vai fazer as validações.
            
        var paciente = pacienteRepository.getReferenceById(dados.idPaciente());
        var medico  = escolherMedico(dados);

        if (medico == null) {
            throw new ValidacaoException("Não há médicos disponíveis");
        }
        var consulta = new Consulta(null, medico, paciente, dados.data());

        consultaRepository.save(consulta);

        return new DadosDetalhamentoConsulta(consulta);

    }

    public void cancelarConsulta(DadosCancelamentoConsulta dados){
        if (!consultaRepository.existsById(dado))
    }

    private Medico escolherMedico(DadosAgendamentoConsulta dados) {
        if (dados.idMedico() != null) {
            return  medicoRepository.getReferenceById(dados.idMedico());
        }
        if (dados.especialidade() == null) {
            throw  new ValidacaoException("Informe a Especialidade");
        }

        return medicoRepository.escolherMedicoDisponivel(dados.especialidade(), dados.data());
    }


}
