package projetoJavaHotel.demo.service;

import projetoJavaHotel.demo.dto.*;
import projetoJavaHotel.demo.entity.checkIn;
import projetoJavaHotel.demo.entity.hospede;
import projetoJavaHotel.demo.repository.checkInRepository;
import projetoJavaHotel.demo.repository.hospedeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class checkInService {

    private final checkInRepository checkInRepository;
    private final hospedeRepository hospedeRepository;

    private static final BigDecimal DIARIA_SEMANA = new BigDecimal("120.00");
    private static final BigDecimal DIARIA_FIM_SEMANA = new BigDecimal("150.00");
    private static final BigDecimal GARAGEM_SEMANA = new BigDecimal("15.00");
    private static final BigDecimal GARAGEM_FIM_SEMANA = new BigDecimal("20.00");
    private static final LocalTime HORARIO_LIMITE_SAIDA = LocalTime.of(16, 30);

    @Transactional
    public checkInResponse realizarcheckIn(checkInRequest request) {
        hospede hospede = hospedeRepository.findById(request.getHospedeId())
                .orElseThrow(() -> new RuntimeException("Hóspede não localizado"));

        checkInRepository.encontraHospedeAtivoPorId(hospede.getId())
                .ifPresent(c -> {
                    throw new RuntimeException("Esse hospede já possui um check-in ativo");
                });

        checkIn checkIn = new checkIn();
        checkIn.setHospede(hospede);
        checkIn.setDataEntrada(request.getDataEntrada());
        checkIn.setAdicionalVeiculo(request.getAdicionalVeiculo());
        checkIn.setAtivo(true);

        checkIn saved = checkInRepository.save(checkIn);
        return convertToResponse(saved);
    }

    @Transactional
    public checkInResponse realizarCheckOut(Long checkInId, checkOutRequest request) {
        checkIn checkIn = checkInRepository.findById(checkInId)
                .orElseThrow(() -> new RuntimeException("Check-in não encontrado"));

        if (checkIn.getDataSaida() != null) {
            throw new RuntimeException("Check-out já foi realizado");
        }

        checkIn.setDataSaida(request.getDataSaida());
        checkIn.setAtivo(false);

        BigDecimal valorTotal = calcularValorTotal(
                checkIn.getDataEntrada(),
                checkIn.getDataSaida(),
                checkIn.getAdicionalVeiculo()
        );
        checkIn.setValorTotal(valorTotal);

        checkIn saved = checkInRepository.save(checkIn);
        return convertToResponse(saved);
    }

    public List<checkInResponse> listarHospedesNoHotel() {
        return checkInRepository.encontraHospedesHospedados().stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    public List<checkInResponse> listarHospedesQueJaSairam() {
        return checkInRepository.encontraHospedesCheckOut().stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    public checkInResponse buscarPorId(Long id) {
        checkIn checkIn = checkInRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Check-in não localizado"));
        return convertToResponse(checkIn);
    }

    public List<checkInResponse> listarTodos() {
        return checkInRepository.findAll().stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    private BigDecimal calcularValorTotal(LocalDateTime entrada, LocalDateTime saida, Boolean temVeiculo) {
        BigDecimal total = BigDecimal.ZERO;
        LocalDate dataAtual = entrada.toLocalDate();
        LocalDate dataSaida = saida.toLocalDate();

        boolean cobraDiariaExtra = saida.toLocalTime().isAfter(HORARIO_LIMITE_SAIDA);
        if (cobraDiariaExtra) {
            dataSaida = dataSaida.plusDays(1);
        }

        while (!dataAtual.isAfter(dataSaida)) {
            boolean isFimDeSemana = dataAtual.getDayOfWeek() == DayOfWeek.SATURDAY ||
                    dataAtual.getDayOfWeek() == DayOfWeek.SUNDAY;

            BigDecimal diaria = isFimDeSemana ? DIARIA_FIM_SEMANA : DIARIA_SEMANA;
            total = total.add(diaria);

            if (temVeiculo) {
                BigDecimal garagem = isFimDeSemana ? GARAGEM_FIM_SEMANA : GARAGEM_SEMANA;
                total = total.add(garagem);
            }

            dataAtual = dataAtual.plusDays(1);
        }

        return total;
    }

    private checkInResponse convertToResponse(checkIn checkIn) {
        checkInResponse response = new checkInResponse();
        response.setId(checkIn.getId());
        response.setDataEntrada(checkIn.getDataEntrada());
        response.setDataSaida(checkIn.getDataSaida());
        response.setAdicionalVeiculo(checkIn.getAdicionalVeiculo());
        response.setValorTotal(checkIn.getValorTotal());
        response.setAtivo(checkIn.isAtivo());

        hospede h = checkIn.getHospede();
        hospedeResponse hospedeResp = new hospedeResponse();
        hospedeResp.setId(h.getId());
        hospedeResp.setNome(h.getNome());
        hospedeResp.setDocumento(h.getDocumento());
        hospedeResp.setTelefone(h.getTelefone());
        hospedeResp.setDataCadastro(h.getDataCadastro());
        response.setHospede(hospedeResp);

        return response;
    }
}