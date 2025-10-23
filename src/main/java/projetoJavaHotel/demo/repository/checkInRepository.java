package projetoJavaHotel.demo.repository;

import projetoJavaHotel.demo.entity.checkIn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface checkInRepository extends JpaRepository<checkIn, Long> {
    
    List<checkIn> findByHospedeId(Long hospedeId);
    
    @Query("select c from checkIn c where c.hospede.id = :hospedeId and c.ativo = true and c.dataSaida is null")
    Optional<checkIn> encontraHospedeAtivoPorId(@Param("hospedeId") Long hospedeId);
    
    @Query("select c from checkIn c where c.dataSaida IS NULL and c.ativo = true")
    List<checkIn> encontraHospedesHospedados();
    
    @Query("select c from checkIn c where c.dataSaida is not null")
    List<checkIn> encontraHospedesCheckOut();
}