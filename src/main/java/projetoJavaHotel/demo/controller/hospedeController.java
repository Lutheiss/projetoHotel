package projetoJavaHotel.demo.controller;

import projetoJavaHotel.demo.dto.hospedeValoresResponse;
import projetoJavaHotel.demo.dto.hospedeRequest;
import projetoJavaHotel.demo.dto.hospedeResponse;
import projetoJavaHotel.demo.service.hospedeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hospedes")
@RequiredArgsConstructor
public class hospedeController {

    private final hospedeService hospedeService;

    @PostMapping
    public ResponseEntity<hospedeResponse> criar(@Valid @RequestBody hospedeRequest request) {
        hospedeResponse response = hospedeService.criar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<hospedeResponse>> listarTodos() {
        List<hospedeResponse> hospedes = hospedeService.listarTodos();
        return ResponseEntity.ok(hospedes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<hospedeResponse> buscarPorId(@PathVariable Long id) {
        hospedeResponse response = hospedeService.buscarPorId(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}/valores")
    public ResponseEntity<hospedeValoresResponse> buscarComValores(@PathVariable Long id) {
        hospedeValoresResponse response = hospedeService.buscarComValores(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<hospedeResponse>> buscar(@RequestParam String q) {
        List<hospedeResponse> hospedes = hospedeService.buscar(q);
        return ResponseEntity.ok(hospedes);
    }

    @PutMapping("/{id}")
    public ResponseEntity<hospedeResponse> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody hospedeRequest request) {
        hospedeResponse response = hospedeService.atualizar(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        hospedeService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}