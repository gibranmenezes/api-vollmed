package med.voll.api.domain.medico;

import med.voll.api.domain.consultas.Consulta;
import med.voll.api.domain.endereco.DadosEndereco;
import med.voll.api.domain.paciente.DadosCadastroPaciente;
import med.voll.api.domain.paciente.Paciente;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class MedicoRepositoryTest {

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private TestEntityManager em;

    @Test
    @DisplayName("Devolver null quando unico medico cadastrado não está diponivel na data")
    void escolherMedicoDisponivelCenario1() {
        //given arranged
        var proximaSegunda10h = LocalDate.now()
                .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                .atTime(10, 0);

        //when act
        var medico = cadastrarMedico("medico", "medico@email.com", "123534", Especialidade.valueOf("CARDIOLOGIA"));
        var paciente = cadastrarPaciente("paciente", "paciente@email.com", "12345678910");
        cadastrarConsulta(medico, paciente, proximaSegunda10h);

        //assert then
        var medicoLivre = medicoRepository.escolherMedicoDisponivel(Especialidade.CARDIOLOGIA, proximaSegunda10h);
        assertThat(medicoLivre).isNull();


    }

    @Test
    @DisplayName("Devolver medico está diponivel na data")
    public void escolherMedicoDisponivelCenario2() {
        //setup
        var proximaSegunda10h = LocalDate.now()
                .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                .atTime(10, 0);

        var medico = cadastrarMedico("medico", "medico@email.com", "123534", Especialidade.valueOf("CARDIOLOGIA"));

        //assert
        var medicoLivre = medicoRepository.escolherMedicoDisponivel(Especialidade.CARDIOLOGIA, proximaSegunda10h);
        assertThat(medicoLivre).isEqualTo(medico);

    }



    private void cadastrarConsulta(Medico medico, Paciente paciente, LocalDateTime data) {
        em.persist(new Consulta(null, medico, paciente, data));
    }
    private Medico cadastrarMedico(String nome, String email, String crm, Especialidade especialidade) {
        var medico = new Medico(dadosMedico(nome, email, crm, especialidade));
        em.persist(medico);
        return medico;
    }
    private Paciente cadastrarPaciente(String nome, String email, String cpf) {
        var paciente = new Paciente(dadosPaciente(nome, email, cpf));
        em.persist(paciente);
        return paciente;
    }
    private DadosCadastroMedico dadosMedico(String nome, String email, String crm, Especialidade especialidade) {
        return new DadosCadastroMedico(
                nome,
                email,
                "61999999999",
                crm,
                especialidade,
                dadosEndereco()
        );
    }



    private DadosCadastroPaciente dadosPaciente(String nome, String email, String cpf) {
        return new DadosCadastroPaciente(
                nome,
                email,
                "61999999999",
                cpf,
                dadosEndereco()
        );
    }
    private DadosEndereco dadosEndereco() {
        return new DadosEndereco(
                "rua xpto",
                "bairro",
                "00000000",
                "Brasilia",
                "DF",
                null,
                null
        );
    }
}