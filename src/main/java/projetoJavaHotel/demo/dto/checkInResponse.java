package projetoJavaHotel.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class checkInResponse {
    private Long id;
    private hospedeResponse hospede;
    private LocalDateTime dataEntrada;
    private LocalDateTime dataSaida;
    private Boolean adicionalVeiculo;
    private BigDecimal valorTotal;
    private Boolean ativo;
}