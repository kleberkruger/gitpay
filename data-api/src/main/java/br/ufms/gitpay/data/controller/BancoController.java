package br.ufms.gitpay.data.controller;

import br.ufms.gitpay.data.service.BancoService;
import br.ufms.gitpay.domain.model.banco.Banco;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/banco")
public class BancoController {

    private final BancoService service;

    public BancoController(BancoService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Banco> save(@RequestBody @Valid Banco banco) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(banco));
    }

    @PutMapping
    public ResponseEntity<Banco> update(@RequestBody @Valid Banco banco) {
        service.save(banco);
        return ResponseEntity.status(HttpStatus.OK).body(banco);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable(value = "id") short id) {
        service.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body("Banco excluído com sucesso");
    }

    @GetMapping("/{id}")
    public ResponseEntity<Banco> findById(@PathVariable(value = "id") short id) {
        var banco = service.findById(id);
        return banco.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping
    public ResponseEntity<List<Banco>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }
}
