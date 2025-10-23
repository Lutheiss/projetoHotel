package projetoJavaHotel.demo.service;

import projetoJavaHotel.demo.dto.*;
import projetoJavaHotel.demo.entity.checkIn;
import projetoJavaHotel.demo.entity.hospede;
import projetoJavaHotel.demo.repository.checkInRepository;
import projetoJavaHotel.demo.repository.hospedeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;


import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class hospedeService {

    private final hospedeRepository hospedeRepository;
    private final checkInRepository checkInRepository;

    @Transactional
    public hospedeResponse criar(hospedeRequest request) {
        if (hospedeRepository.existsByDocumento(request.getDocumento())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Já existe um hóspede com este documento");
        }

        hospede hospede = new hospede();
        hospede.setNome(request.getNome());
        hospede.setDocumento(request.getDocumento());
        hospede.setTelefone(request.getTelefone());

        hospede saved = hospedeRepository.save(hospede);
        return convertToResponse(saved);
    }

    @Transactional
    public hospedeResponse atualizar(Long id, hospedeRequest request) {
        hospede hospede = hospedeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Hóspede não encontrado"));

        if (!hospede.getDocumento().equals(request.getDocumento()) &&
                hospedeRepository.existsByDocumento(request.getDocumento())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Já existe um hóspede com este documento");
        }

        hospede.setNome(request.getNome());
        hospede.setDocumento(request.getDocumento());
        hospede.setTelefone(request.getTelefone());

        hospede saved = hospedeRepository.save(hospede);
        return convertToResponse(saved);
    }

    @Transactional
    public void deletar(Long id) {
        if (!hospedeRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Hóspede não encontrado");
        }
        hospedeRepository.deleteById(id);
    }

    public hospedeResponse buscarPorId(Long id) {
        hospede hospede = hospedeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Hóspede não encontrado"));
        return convertToResponse(hospede);
    }

    public List<hospedeResponse> listarTodos() {
        return hospedeRepository.findAll().stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    public List<hospedeResponse> buscar(String busca) {
        String q = (busca == null) ? "" : busca.trim();
        String qDigits = q.replaceAll("\\D", "");

        return hospedeRepository.buscarNomeDocumentoTelefone(q, qDigits).stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    public hospedeValoresResponse buscarComValores(Long id) {
        hospede hospede = hospedeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Hóspede não encontrado"));

        List<checkIn> checkIns = checkInRepository.findByHospedeId(id);

        BigDecimal valorTotal = checkIns.stream()
                .filter(c -> c.getValorTotal() != null)
                .map(checkIn::getValorTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal valorUltima = checkIns.stream()
                .filter(c -> c.getValorTotal() != null)
                .sorted((c1, c2) -> c2.getDataEntrada().compareTo(c1.getDataEntrada()))
                .findFirst()
                .map(checkIn::getValorTotal)
                .orElse(BigDecimal.ZERO);

        boolean noHotel = checkIns.stream()
                .anyMatch(checkIn::isAtivo);

        hospedeValoresResponse response = new hospedeValoresResponse();
        response.setId(hospede.getId());
        response.setNome(hospede.getNome());
        response.setDocumento(hospede.getDocumento());
        response.setTelefone(hospede.getTelefone());
        response.setValorTotal(valorTotal);
        response.setValorUltimaHospedagem(valorUltima);
        response.setNoHotel(noHotel);

        return response;
    }

    private hospedeResponse convertToResponse(hospede hospede) {
        hospedeResponse response = new hospedeResponse();
        response.setId(hospede.getId());
        response.setNome(hospede.getNome());
        response.setDocumento(hospede.getDocumento());
        response.setTelefone(hospede.getTelefone());
        response.setDataCadastro(hospede.getDataCadastro());
        return response;
    }
}