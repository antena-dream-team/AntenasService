package br.gov.sp.fatec.entrepreneur.service;

import br.gov.sp.fatec.entrepreneur.domain.Entrepreneur;
import br.gov.sp.fatec.entrepreneur.repository.EntrepreneurRepository;
import br.gov.sp.fatec.utils.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static br.gov.sp.fatec.utils.exception.NotFoundException.throwIfEntrepreneurIsNull;

@Service
public class EntrepreneurService {

    @Autowired
    EntrepreneurRepository repository;

    public Entrepreneur save(Entrepreneur entrepreneur) {
        return repository.save(entrepreneur);
    }

    public List<Entrepreneur> findAll() {
        return repository.findAll();
    }

    public List<Entrepreneur> findActive() {
        return repository.findAllByActive(true);
    }

    public Entrepreneur findById(Long id) {
        Entrepreneur found = repository.getOne(id);
        throwIfEntrepreneurIsNull(found, id);

        return found;
    }

    public Entrepreneur deactivate(Long id) {
        Entrepreneur found = repository.getOne(id);
        throwIfEntrepreneurIsNull(found, id);

        found.setActive(false);
        repository.save(found);

        return found;
    }

    public Entrepreneur update(Long id, Entrepreneur entrepreneur) {
        Entrepreneur found = repository.findById(id).orElse(null);
        throwIfEntrepreneurIsNull(found, id);

        found.setName(entrepreneur.getName());
        found.setEmail(entrepreneur.getEmail());
        found.setCompany(entrepreneur.getCompany());
        found.setTelephone(entrepreneur.getTelephone());
        found.setCpf(entrepreneur.getCpf());
        found.setActive(entrepreneur.isActive());

        return repository.save(found);
    }

}
