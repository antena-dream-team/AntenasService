package br.gov.sp.fatec.entrepreneur.service;

import br.gov.sp.fatec.cadi.domain.Cadi;
import br.gov.sp.fatec.entrepreneur.domain.Entrepreneur;
import br.gov.sp.fatec.entrepreneur.repository.EntrepreneurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EntrepreneurService {

    @Autowired
    EntrepreneurRepository repository;

    public Entrepreneur save(Entrepreneur entrepreneur) {
        return repository.save(entrepreneur);
    }

    public void delete(Long id) {
        Optional<Entrepreneur> entrepreneur = repository.findById(id);
        // TODO: NotFoundException - check if exists
        repository.deleteById(id);
    }

}
