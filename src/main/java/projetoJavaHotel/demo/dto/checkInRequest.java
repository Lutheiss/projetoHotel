package projetoJavaHotel.demo.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class checkInRequest {

    @NotNull(message = "id do hóspede é obrigatório")
    private Long hospedeId;

    @NotNull(message = "Data de entrada é obrigatória")
    private LocalDateTime dataEntrada;

    @NotNull(message = "Adicional de veículo é obrigatório")
    private Boolean adicionalVeiculo;
}