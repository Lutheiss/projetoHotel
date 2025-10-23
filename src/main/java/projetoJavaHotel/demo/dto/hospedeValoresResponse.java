package projetoJavaHotel.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class hospedeValoresResponse {
    private Long id;
    private String nome;
    private String documento;
    private String telefone;
    private BigDecimal valorTotal;
    private BigDecimal valorUltimaHospedagem;
    private Boolean noHotel;
}