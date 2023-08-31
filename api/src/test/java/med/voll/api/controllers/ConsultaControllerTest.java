package med.voll.api.controllers;

import med.voll.api.domain.consultas.AgendaDeConsultas;
import med.voll.api.domain.consultas.DadosAgendamentoConsulta;
import med.voll.api.domain.consultas.DadosDetalhamentoConsulta;
import med.voll.api.domain.medico.Especialidade;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;


import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class ConsultaControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonTester<DadosAgendamentoConsulta> dadosAgendamentoConsultaJacksonTester; //criam o json com dados passados

    @Autowired
    private JacksonTester<DadosDetalhamentoConsulta> dadosDetalhamentoConsultaJacksonTester;

    /**
     * Anotação que moca o serviço para não precisar utilizar o BD
     * */
    @MockBean
    private AgendaDeConsultas agendaDeConsultas;



    @Test
    @DisplayName("Retorna http 400 quando há informações inválidas")
    @WithMockUser
    void agendarCenario1() throws Exception {
        var response = mvc.perform(post("/consultas")).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());

    }

    @Test
    @DisplayName("Retorna http 200 ao enviar dados corretos de agendamento")
    @WithMockUser
    void agendarCenario2() throws Exception {

        var data = LocalDateTime.now().plusHours(1);
        var especialidade = Especialidade.CARDIOLOGIA;
        var response = mvc.perform(
                post("/consultas")
                        .contentType(MediaType.APPLICATION_JSON) // precisa indicar o tipo de dados que serão enviados
                        .content(dadosAgendamentoConsultaJacksonTester.write(
                                new DadosAgendamentoConsulta(2L, 5L, data, especialidade)
                        ).getJson())
                )
                .andReturn()
                .getResponse();

        var jsonResponse  = dadosDetalhamentoConsultaJacksonTester.write(
                new DadosDetalhamentoConsulta(null, 2l, 5L, data)
        ).getJson();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());



    }

}