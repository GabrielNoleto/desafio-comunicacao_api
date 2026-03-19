package com.luizalebs.comunicacao_api.service;

import com.luizalebs.comunicacao_api.infraestructure.exceptions.ResourceNotFoundException;
import com.luizalebs.comunicacao_api.infraestructure.exceptions.IllegalArgumentException;
import com.luizalebs.comunicacao_api.api.dto.ComunicacaoInDTO;
import com.luizalebs.comunicacao_api.api.dto.ComunicacaoOutDTO;
import com.luizalebs.comunicacao_api.business.mapper.ComunicacaoMapper;
import com.luizalebs.comunicacao_api.business.service.ComunicacaoService;
import com.luizalebs.comunicacao_api.infraestructure.entities.ComunicacaoEntity;
import com.luizalebs.comunicacao_api.infraestructure.enums.StatusEnvioEnum;
import com.luizalebs.comunicacao_api.infraestructure.repositories.ComunicacaoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ComunicacaoServiceTest {

    @Mock
    private ComunicacaoRepository repository;

    @Mock
    private ComunicacaoMapper converter;

    @InjectMocks
    private ComunicacaoService service;

    @Test
    @DisplayName("Deve agendar comunicação com sucesso")
    void deveAgendarComSucesso(){

        ComunicacaoInDTO inDTO = new ComunicacaoInDTO();
        ComunicacaoOutDTO outDTO = new ComunicacaoOutDTO();
        ComunicacaoEntity entity = new ComunicacaoEntity();


        when(converter.paraComunicacaoEntity(inDTO)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(entity);
        when(converter.paraComunicacaoDTO(entity)).thenReturn(outDTO);

        ComunicacaoOutDTO  resultado = service.agendarComunicacao(inDTO);

        assertNotNull(resultado);
        assertEquals(StatusEnvioEnum.PENDENTE, inDTO.getStatusEnvio());
        verify(repository, times(1)).save(entity);

    }


    @Test
    @DisplayName("Deve alterar status para CANCELADO com sucesso")
    void deveAlterarStatusComSucesso(){

        String email = "teste@gmail.com";
        ComunicacaoEntity entity = new ComunicacaoEntity();

        entity.setEmailDestinatario(email);
        entity.setStatusEnvio(StatusEnvioEnum.PENDENTE);


        ComunicacaoOutDTO outDTO = new ComunicacaoOutDTO();

        when(repository.findByEmailDestinatario(email)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(entity);
        when(converter.paraComunicacaoDTO(entity)).thenReturn(outDTO);

        ComunicacaoOutDTO resultado = service.alterarStatusComunicacao(email);

        assertNotNull(resultado);

        assertEquals(StatusEnvioEnum.CANCELADO, entity.getStatusEnvio());

        verify(repository, times(1)).findByEmailDestinatario(email);
        verify(repository,times(1)).save(entity);

    }





    @Test
    @DisplayName("Deve retornar o status quando o email existir")
    void deveRetornarStatusQuandoEmailExiste() {

        ComunicacaoEntity entity = new ComunicacaoEntity();
        ComunicacaoOutDTO outDTO = new ComunicacaoOutDTO();
        when(repository.findByEmailDestinatario("sucesso@teste.com")).thenReturn(entity);
        when(converter.paraComunicacaoDTO(entity)).thenReturn(outDTO);

        // Act
        ComunicacaoOutDTO resultado = service.buscarStatusComunicacao("sucesso@teste.com");

        // Assert
        assertNotNull(resultado);
        verify(repository, times(1)).findByEmailDestinatario("sucesso@teste.com");
    }




    @Test
    @DisplayName("Deve lançar exceção ao tentar cancelar status com e-mail inexistente")
    void alterarStatus_CaminhoTriste() {
        String emailInexistente = "nao_existo@gmail.com";

        when(repository.findByEmailDestinatario(emailInexistente)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> {
            service.alterarStatusComunicacao(emailInexistente);
        });

        verify(repository, never()).save(any());
    }



    @Test
    @DisplayName("Deve lançar exceção quando email não existir")
    void buscarStatus_EmailNaoEncontrado(){
        when(repository.findByEmailDestinatario(anyString())).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () ->{
            service.alterarStatusComunicacao("gabriel@gmail.com");
        });
    }





    @Test
    @DisplayName("Deve lançar exceção quando DTO for nulo")
    void deveLançarExcecaoQuandoDtoForNulo(){
        assertThrows(IllegalArgumentException.class, () -> {
            service.agendarComunicacao(null);
        });

    }




}
