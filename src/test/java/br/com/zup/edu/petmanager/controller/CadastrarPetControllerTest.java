package br.com.zup.edu.petmanager.controller;

import br.com.zup.edu.petmanager.controller.request.PetRequest;
import br.com.zup.edu.petmanager.controller.request.TipoPetRequest;
import br.com.zup.edu.petmanager.model.Pet;
import br.com.zup.edu.petmanager.repository.PetRepository;
import br.com.zup.edu.petmanager.util.MensagemDeErro;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc(printOnlyOnFailure = false)
@ActiveProfiles("test")
class CadastrarPetControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    @Autowired
    private PetRepository repository;

    @BeforeEach
    void setUp(){
        this.repository.deleteAll();
    }

    @Test
    @DisplayName("Deve cadastrar um pet com dados validos")
    void test1() throws Exception{
        //cenario
        PetRequest petRequest = new PetRequest("Bruno","Viralata", TipoPetRequest.GATO, LocalDate.now().minusMonths(5));
        String payload = mapper.writeValueAsString(petRequest);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/pets")
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload);

        //acao e corretude
        mockMvc.perform(request)
                .andExpect(
                        MockMvcResultMatchers.status().isCreated()
                )
                .andExpect(
                        MockMvcResultMatchers.redirectedUrlPattern("http://localhost/pets/*")
                );

        List<Pet> pets = repository.findAll();
        assertEquals(1,pets.size());
    }

    @Test
    @DisplayName("Não deve cadastrar um pet com dados invalidos")
    void test2() throws Exception{
        //cenario
        PetRequest petRequest = new PetRequest(null,null, null, null);
        String payload = mapper.writeValueAsString(petRequest);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/pets")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Accept-Language","pt-br")
                .content(payload);

        //acao e corretude
        String payloadResponse = mockMvc.perform(request)
                .andExpect(
                        MockMvcResultMatchers.status().isBadRequest()
                )
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        MensagemDeErro mensagemDeErro = mapper.readValue(payloadResponse, MensagemDeErro.class);

        assertEquals(4,mensagemDeErro.getMensagens().size());
        assertThat(mensagemDeErro.getMensagens(), containsInAnyOrder(
                "O campo raca não deve estar em branco",
                "O campo tipo não deve ser nulo",
                "O campo nome não deve estar em branco",
                "O campo dataNascimento não deve ser nulo"
        ));
    }

    @Test
    @DisplayName("Não deve cadastrar um pet com data de nascimento no passado")
    void test3() throws Exception{
        //cenario
        PetRequest petRequest = new PetRequest("Bruno","Viralata", TipoPetRequest.GATO, LocalDate.now());
        String payload = mapper.writeValueAsString(petRequest);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/pets")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Accept-Language","pt-br")
                .content(payload);

        //acao e corretude
        String payloadResponse = mockMvc.perform(request)
                .andExpect(
                        MockMvcResultMatchers.status().isBadRequest()
                )
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        MensagemDeErro mensagemDeErro = mapper.readValue(payloadResponse, MensagemDeErro.class);

        assertEquals(1,mensagemDeErro.getMensagens().size());
        assertThat(mensagemDeErro.getMensagens(), containsInAnyOrder(
                "O campo dataNascimento deve ser uma data passada"
        ));
    }

}