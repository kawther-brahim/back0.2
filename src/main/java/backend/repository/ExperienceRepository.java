package backend.repository;


import backend.model.experience;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExperienceRepository extends JpaRepository<experience, Long> {
    List<experience> findByFormateurCin(long cin, Sort e);
}
