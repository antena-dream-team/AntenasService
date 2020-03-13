package br.gov.sp.fatec.cadi.service;

import br.gov.sp.fatec.cadi.domain.Cadi;
import br.gov.sp.fatec.cadi.repository.CadiRepository;
import br.gov.sp.fatec.utils.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CadiService {

    @Autowired
    CadiRepository repository;

    public Cadi save(Cadi cadi) {
        return repository.save(cadi);
    }

    public void deactivate(Long id) throws NotFoundException {
        Cadi found = repository.findById(id).orElse(null);
        NotFoundException.throwIfNull(found);

        found.setActive(false);
        repository.save(found);
    }

    public List<Cadi> findAll() {
        return repository.findAll();
    }

    public List<Cadi> findActive() {
        return repository.findAllByActive(true);
    }

    public Cadi findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public Cadi update(Long id, Cadi cadi) throws NotFoundException {
        Cadi found = repository.findById(id).orElse(null);
        NotFoundException.throwIfNull(found);

        found.setActive(cadi.isActive());
        found.setName(cadi.getName());
        found.setEmail(cadi.getEmail());
        found.setCpf(cadi.getCpf());
        found.setPosition(cadi.getPosition());

        return repository.save(found);
    }

}
