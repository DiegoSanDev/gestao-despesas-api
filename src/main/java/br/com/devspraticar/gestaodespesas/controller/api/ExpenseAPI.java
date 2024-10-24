package br.com.devspraticar.gestaodespesas.controller.api;

import br.com.devspraticar.gestaodespesas.dto.ErrorMessageDTO;
import br.com.devspraticar.gestaodespesas.dto.ErrorMessageShortDTO;
import br.com.devspraticar.gestaodespesas.dto.request.ExpenseRequestDTO;
import br.com.devspraticar.gestaodespesas.dto.response.ExpenseResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;

public interface ExpenseAPI {

    @Operation(summary = "Cria uma nova despesa")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Despesa criada com sucesso",
            content = { @Content(mediaType = "application/json",
                schema = @Schema(implementation = ExpenseResponseDTO.class))},
            headers = {
                @Header(name = "Location", description = "URI do recurso criado", schema = @Schema(type = "string"))
            }),
        @ApiResponse(responseCode = "422", description = "Erros de validação de regra de negócio. Dependendo da validação o objeto violations pode não existir.",
            content = { @Content(mediaType = "application/json",
                schema = @Schema(implementation = ErrorMessageDTO.class)) }),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor",
            content = { @Content(mediaType = "application/json",
                schema = @Schema(implementation = ErrorMessageShortDTO.class)) })
    })
    ResponseEntity<ExpenseResponseDTO> createExpense(ExpenseRequestDTO body);

    @Operation(summary = "Busca uma despesa por id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Consulta realizada comsucesso",
                content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ExpenseResponseDTO.class))}),
        @ApiResponse(responseCode = "422", description = "Erros de validação de regra de negócio. Dependendo da validação o objeto violations pode não existir.",
                content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessageDTO.class)) }),
        @ApiResponse(responseCode = "404", description = "Despesa não encontrada",
                content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessageDTO.class)) }),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor",
                content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessageShortDTO.class)) })
    })
    ResponseEntity<ExpenseResponseDTO> findById(Long id);

}