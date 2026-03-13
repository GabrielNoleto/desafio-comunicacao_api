package com.luizalebs.comunicacao_api.factory;

import com.luizalebs.comunicacao_api.api.dto.ComunicacaoInDTO;
import com.luizalebs.comunicacao_api.infraestructure.enums.ModoEnvioEnum;

import java.util.Date;

    public class ComunicacaoDataFactory {

        public static ComunicacaoInDTO.ComunicacaoInDTOBuilder
        criarInDTOValidoBuilder() {
            return ComunicacaoInDTO.builder()
                    .dataHoraEnvio(new Date())
                    .nomeDestinatario("João Silva")
                    .emailDestinatario("joao@teste.com")
                    .telefoneDestinatario("11999999999")
                    .mensagem("Mensagem padrão de teste")
                    .modoDeEnvio(ModoEnvioEnum.EMAIL);
        }


    }
