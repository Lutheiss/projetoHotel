package projetoJavaHotel.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class hospedeResponse {
    private Long id;
    private String nome;
    private String documento;
    private String telefone;
    private LocalDateTime dataCadastro;
}