package com.hugo.api.repository;

import com.hugo.api.entity.JornadaLaboral;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JornadaLaboralRepository extends JpaRepository<JornadaLaboral, Integer> {
}
