package br.gov.sp.fatec.entrepreneur.repository;

import br.gov.sp.fatec.entrepreneur.domain.Entrepreneur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

@Repository
public interface EntrepreneurRepository extends JpaRepository<Entrepreneur, Long>, JpaSpecificationExecutor<Entrepreneur> {
}
