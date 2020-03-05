package br.gov.sp.fatec.cadi.service;

import br.gov.sp.fatec.cadi.domain.Cadi;
import br.gov.sp.fatec.cadi.repository.CadiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CadiService {

    @Autowired
    CadiRepository repository;

    public Cadi save(Cadi cadi) {
        return repository.save(cadi);
    }

    public void delete(Long id) {
        Optional<Cadi> cadi = repository.findById(id);
        // TODO: NotFoundException
        repository.deleteById(id);
    }

}
