package com.luizalebs.comunicacao_api.business.service;


import com.luizalebs.comunicacao_api.business.mapper.ComunicacaoMapper;
import com.luizalebs.comunicacao_api.infraestructure.client.ComunicacaoClient;
import com.luizalebs.comunicacao_api.infraestructure.entities.ComunicacaoEntity;
import com.luizalebs.comunicacao_api.infraestructure.enums.StatusEnvioEnum;
import com.luizalebs.comunicacao_api.infraestructure.repositories.ComunicacaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class cron {

    private final ComunicacaoRepository repository;
    private final ComunicacaoClient client;
    private final ComunicacaoMapper converter;


    @Scheduled(cron = "0 * * * * *")

    public void enviarComunicacaoPendente(){

        List<ComunicacaoEntity> pendentes = repository.findByStatusEnvio(StatusEnvioEnum.PENDENTE);

        for(ComunicacaoEntity entidade : pendentes){
            try{
                client.enviarComunicacao(converter.paraComunicacaoDTO(entidade));
                entidade.setStatusEnvio(StatusEnvioEnum.ENVIADO);
                repository.save(entidade);
                System.out.println("Comunicação eviada com sucesso");
            }catch(Exception e){
                System.err.println("Falha no envio de ID " + entidade.getId() +": " + e.getMessage());
            }
        }
    }

}
