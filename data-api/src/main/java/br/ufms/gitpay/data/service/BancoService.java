package br.ufms.gitpay.data.service;

import br.ufms.gitpay.data.entity.BancoEntity;
import br.ufms.gitpay.data.repository.BancoRepository;
import br.ufms.gitpay.domain.model.banco.Banco;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BancoService {

    private final BancoRepository repository;

    public BancoService(BancoRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public Banco save(Banco banco) {
        repository.save(BancoEntity.builder()
                .codigo(banco.getCodigo())
                .nome(banco.getNome())
                .razaoSocial(banco.getRazaoSocial())
                .build());
        return banco;
    }

    @Transactional
    public void deleteById(short codigo) {
        BancoEntity pet = repository.findById(codigo)
                .orElseThrow(() -> new RuntimeException("Banco não encontrado: " + codigo));

        repository.delete(pet);
    }

    @Transactional(readOnly = true)
    public Optional<Banco> findById(short id) {
        return repository.findById(id).map(b -> new Banco(b.getCodigo(), b.getNome(), b.getRazaoSocial()));
    }

    @Transactional(readOnly = true)
    public List<Banco> findAll() {
        return repository.findAll()
                .stream()
                .map(b -> new Banco(b.getCodigo(), b.getNome(), b.getRazaoSocial()))
                .collect(Collectors.toList());
    }
}
