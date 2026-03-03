package com.luizalebs.comunicacao_api.api;

import com.luizalebs.comunicacao_api.api.dto.ComunicacaoInDTO;
import com.luizalebs.comunicacao_api.api.dto.ComunicacaoOutDTO;
import com.luizalebs.comunicacao_api.business.service.ComunicacaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@Tag(name="Comunicação", description = "Comunica usuários")
@RestController
@RequestMapping("/comunicacao")
public class ComunicacaoController {

    private final ComunicacaoService service;

    public ComunicacaoController(ComunicacaoService service) {
        this.service = service;
    }

    @PostMapping("/agendar")
    @Operation(summary = "Agenda uma nova comunicação", description = "Persiste um agendamento com status inicial PENDENTE para processamento posterior")
    @ApiResponse(responseCode = "200", description = "Comunicação agendada com sucesso")
    @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos")
    @ApiResponse(responseCode = "500", description = "Erro interno")
    public ResponseEntity<ComunicacaoOutDTO> agendar(@RequestBody ComunicacaoInDTO dto)  {
        return ResponseEntity.ok(service.agendarComunicacao(dto));
    }

    @GetMapping()
    @Operation(summary = "Busca status por email",description = "Consulta o estado atual e os detalhes de um agendamento através do e-mail do destinatário")
    @ApiResponse(responseCode = "200", description = "Status encontrado com sucesso")
    @ApiResponse(responseCode = "404", description = "Status não encontrado")
    @ApiResponse(responseCode = "500", description = "Erro interno")
    public ResponseEntity<ComunicacaoOutDTO> buscarStatus(@RequestParam String emailDestinatario) {
        return ResponseEntity.ok(service.buscarStatusComunicacao(emailDestinatario));
    }

    @PatchMapping("/cancelar")
    @Operation(summary = "Cancela comunicação", description = "Altera o status da comunicação agendada para cancelado")
    @ApiResponse(responseCode = "200", description = "Status cancelado com sucesso")
    @ApiResponse(responseCode = "404", description = "Status não encontrado")
    @ApiResponse(responseCode = "500", description = "Erro interno")
    public ResponseEntity<ComunicacaoOutDTO> cancelarStatus(@RequestParam String emailDestinatario) {
        return ResponseEntity.ok(service.alterarStatusComunicacao(emailDestinatario));
    }

}
