package projetoJavaHotel.demo.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class checkOutRequest {

    @NotNull(message = "Data de saída é obrigatória")
    private LocalDateTime dataSaida;
}