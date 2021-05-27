package backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import backend.model.voucher;

public interface VoucherRepository extends JpaRepository<voucher, String> {

	List<voucher> findByEntrepriseRcs(long entrepriseId);
	List<voucher> findByClientCin(long cin);
}
