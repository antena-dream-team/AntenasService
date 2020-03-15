package br.gov.sp.fatec.entrepreneur.service;

import br.gov.sp.fatec.entrepreneur.domain.Entrepreneur;
import br.gov.sp.fatec.entrepreneur.repository.EntrepreneurRepository;
import br.gov.sp.fatec.utils.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EntrepreneurService {

    @Autowired
    EntrepreneurRepository repository;

    public Entrepreneur save(Entrepreneur entrepreneur) {
        return repository.save(entrepreneur);
    }

    public void deactivate(Long id) throws NotFoundException {
        Entrepreneur found = repository.findById(id).orElse(null);
        NotFoundException.throwIfNull(found);

        found.setActive(false);
        repository.save(found);
    }

    public List<Entrepreneur> findAll() {
        return repository.findAll();
    }

    public List<Entrepreneur> findActive() {
        return repository.findAllByActive(true);
    }

    public Entrepreneur findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public Entrepreneur update(Long id, Entrepreneur entrepreneur) throws NotFoundException {
        Entrepreneur found = repository.findById(id).orElse(null);
        NotFoundException.throwIfNull(found);

        found.setName(entrepreneur.getName());
        found.setEmail(entrepreneur.getEmail());
        found.setCompany(entrepreneur.getCompany());
        found.setTelephone(entrepreneur.getTelephone());
        found.setCpf(entrepreneur.getCpf());
        found.setActive(entrepreneur.isActive());

        return repository.save(found);
    }

}
