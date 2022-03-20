package org.factoriaf5.coders.repositories;

import org.factoriaf5.coders.domain.Coder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoderRepository extends JpaRepository<Coder, Long> {
}