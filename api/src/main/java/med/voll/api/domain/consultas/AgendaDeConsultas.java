package med.voll.api.domain.consultas;

import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.medico.MedicoRepository;
import med.voll.api.domain.paciente.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AgendaDeConsultas {

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private PacienteRepository pacienteRepository;


    public void agendar(DadosAgendamentoConsulta dados){
        if ( !pacienteRepository.existsById(dados.idPaciente())
            || ( dados.idMedico() != null
                && !medicoRepository.existsById(dados.idMedico()))) {

            throw new ValidacaoException("ID não encontrado");
        }
            
        var paciente = pacienteRepository.getReferenceById(dados.idPaciente());
        var medico  = escolherMedico(dados);
        var consulta = new Consulta(null, medico, paciente, dados.data());

        consultaRepository.save(consulta);

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
