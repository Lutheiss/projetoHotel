package projetoJavaHotel.demo.controller;

import projetoJavaHotel.demo.dto.checkInRequest;
import projetoJavaHotel.demo.dto.checkInResponse;
import projetoJavaHotel.demo.dto.checkOutRequest;
import projetoJavaHotel.demo.service.checkInService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/checkIns")
@RequiredArgsConstructor
public class checkInController {

    private final checkInService checkInService;

    @PostMapping
    public ResponseEntity<checkInResponse> realizarcheckIn(@Valid @RequestBody checkInRequest request) {
        checkInResponse response = checkInService.realizarcheckIn(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}/checkout")
    public ResponseEntity<checkInResponse> realizarCheckOut(
            @PathVariable Long id,
            @Valid @RequestBody checkOutRequest request) {
        checkInResponse response = checkInService.realizarCheckOut(id, request);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<checkInResponse>> listarTodos() {
        List<checkInResponse> checkIns = checkInService.listarTodos();
        return ResponseEntity.ok(checkIns);
    }

    @GetMapping("/{id}")
    public ResponseEntity<checkInResponse> buscarPorId(@PathVariable Long id) {
        checkInResponse response = checkInService.buscarPorId(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/no-hotel")
    public ResponseEntity<List<checkInResponse>> listarHospedesNoHotel() {
        List<checkInResponse> hospedes = checkInService.listarHospedesNoHotel();
        return ResponseEntity.ok(hospedes);
    }

    @GetMapping("/historico")
    public ResponseEntity<List<checkInResponse>> listarHospedesQueJaSairam() {
        List<checkInResponse> hospedes = checkInService.listarHospedesQueJaSairam();
        return ResponseEntity.ok(hospedes);
    }
}