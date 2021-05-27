package backend.repository;

import backend.model.client;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ClientRepository extends JpaRepository<client, Long> {
    List<client> findByEntrepriseRcs(long entrepriseId);
    List<client> findByVoucherCode(String code);
    Optional<client> findByUsername(String username);
}
