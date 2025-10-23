package projetoJavaHotel.demo.repository;

import projetoJavaHotel.demo.entity.hospede;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface hospedeRepository extends JpaRepository<hospede, Long> {

    @Query(value = """
            select h.*
            from hospedes h
            where
              unaccent(lower(h.nome)) like unaccent(lower(concat('%', :q, '%')))
              or (
                coalesce(:qDigits, '') <> '' and (
                  regexp_replace(h.documento, '[^0-9]', '', 'g') like concat('%', :qDigits, '%')
                  or regexp_replace(h.telefone,  '[^0-9]', '', 'g') like concat('%', :qDigits, '%')
                )
              )
            """, nativeQuery = true)
    List<hospede> buscarNomeDocumentoTelefone(@Param("q") String q, @Param("qDigits") String qDigits);

    Optional<hospede> findByDocumento(String documento);

    boolean existsByDocumento(String documento);
}
