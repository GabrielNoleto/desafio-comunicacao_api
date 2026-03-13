package com.luizalebs.comunicacao_api.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.luizalebs.comunicacao_api.api.ComunicacaoController;
import com.luizalebs.comunicacao_api.api.dto.ComunicacaoInDTO;
import com.luizalebs.comunicacao_api.api.dto.ComunicacaoOutDTO;
import com.luizalebs.comunicacao_api.business.service.ComunicacaoService;
import com.luizalebs.comunicacao_api.factory.ComunicacaoDataFactory;
import com.luizalebs.comunicacao_api.infraestructure.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.text.SimpleDateFormat;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ComunicacaoController.class)
public class ComunicacaoControllerTest {

    @Autowired
    private MockMvc mockMvc;   //simulador de chamadas API

    @MockBean
    private ComunicacaoService service; // dublê da service

    @Autowired
    private ObjectMapper objectMapper; // converte objetos em Json


    @Test
    @DisplayName("Deve agendar comunicação e retornar 200 OK")
        void deveAgendarComSucesso() throws Exception {

           ComunicacaoInDTO inDTO = ComunicacaoDataFactory.criarInDTOValidoBuilder().build();
           ComunicacaoOutDTO outDTO = new ComunicacaoOutDTO();



            objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));

            when(service.agendarComunicacao(any(ComunicacaoInDTO.class))).thenReturn(outDTO);

            // 3. Executar o teste
            mockMvc.perform(post("/comunicacao/agendar")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(inDTO))) //
                    .andDo(print())
                    .andExpect(status().isOk());
        }



        @Test
        @DisplayName("Deve retornar 200 ao buscar comunicacao por email")
        void deveRetornar200Ok() throws  Exception{

        ComunicacaoInDTO inDTO = ComunicacaoDataFactory.criarInDTOValidoBuilder()
                .emailDestinatario("gabriel@gmail.com")
                .build();
        ComunicacaoOutDTO outDTO = new ComunicacaoOutDTO();


            when(service.buscarStatusComunicacao(any())).thenReturn(outDTO);

            mockMvc.perform(get("/comunicacao")
                            .param("emailDestinatario", inDTO.getEmailDestinatario()))
                            .andDo(print())
                            .andExpect(status().isOk());

        }



    @Test
    @DisplayName("Deve retornar 404 quando o e-mail não for encontrado (Global Handler)")
    void deveRetornar404AoBuscarEmailInexistente() throws Exception {

        String email = "naoexiste@teste.com";

        when(service.buscarStatusComunicacao(email))
                .thenThrow(new ResourceNotFoundException("Status não encontrado"));

        mockMvc.perform(get("/") //
                        .param("emailDestinatario", email) //parâmetro
                        .contentType(MediaType.APPLICATION_JSON)) // tipo de conteúdo (texto Json)
                .andExpect(status().isNotFound()); // valida se a Global converteu para 404
    }





    @Test
    @DisplayName("Deve cancelar uma comunicação e retornar 200 OK")
    void deveCancelarComSucesso() throws Exception {

        String email = "teste@gmail.com";
        ComunicacaoOutDTO outDTO = new ComunicacaoOutDTO();

        when(service.alterarStatusComunicacao(eq(email))).thenReturn(outDTO);

        mockMvc.perform(patch("/comunicacao/cancelar")
                        .param("emailDestinatario", email))
                .andDo(print())
                .andExpect(status().isOk());
    }


    @Test
    @DisplayName("Deve retornar 404 ao tentar cancelar e-mail inexistente")
    void deveRetornar404AoCancelarInexistente() throws Exception {
        String email = "nao_existe@gmail.com";

        when(service.alterarStatusComunicacao(email))
                .thenThrow(new ResourceNotFoundException("Registro não encontrado"));

        mockMvc.perform(patch("/comunicacao/cancelar")
                        .param("emailDestinatario", email))
                .andExpect(status().isNotFound());
    }

        }







