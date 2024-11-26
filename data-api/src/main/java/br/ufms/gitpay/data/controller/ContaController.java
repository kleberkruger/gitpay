package br.ufms.gitpay.data.controller;

import br.ufms.gitpay.data.dto.ContaDTO;
import br.ufms.gitpay.data.dto.ContaGitPayDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/conta")
public class ContaController {

    @PostMapping
    public ResponseEntity<String> salvarConta(@RequestBody ContaDTO contaDTO) {
//        return ResponseEntity.status(HttpStatus.CREATED).build();
        return ResponseEntity.status(HttpStatus.OK).body("Salvar conta");
    }

    @GetMapping
    public ResponseEntity<String> listarContas() {
//        return ResponseEntity.ok(contas.values().stream().flatMap(List::stream).toList());
        return ResponseEntity.status(HttpStatus.OK).body("Listar contas");
    }

    @GetMapping("/{banco}")
    public ResponseEntity<String> listarContasPorBanco(@PathVariable String banco) {
//        return ResponseEntity.ok(contas.getOrDefault(banco, new ArrayList<>()));
//        return ResponseEntity.noContent().build();
        return ResponseEntity.status(HttpStatus.OK).body("Listar contas por banco");
    }

    @DeleteMapping
    public ResponseEntity<String> deletarConta(@RequestBody ContaDTO contaDTO) {
//        contas.getOrDefault(contaDTO.banco(), new ArrayList<>())
//                .removeIf(c -> c.agencia() == contaDTO.agencia()
//                        && c.numero() == contaDTO.numero()
//                        && c.tipoConta().equals(contaDTO.tipoConta()));
//        return ResponseEntity.noContent().build();
        return ResponseEntity.status(HttpStatus.OK).body("Deletar conta");
    }

    @PostMapping("/666")
    public ResponseEntity<String> salvarContaGitPay(@RequestBody ContaGitPayDTO contaGitPayDTO) {
//        contasGitPay.put(contaGitPayDTO.numero(), contaGitPayDTO);
//        return ResponseEntity.status(HttpStatus.CREATED).build();
        return ResponseEntity.status(HttpStatus.OK).body("Salvar conta GitPay");
    }

    @GetMapping("/666")
    public ResponseEntity<String> listarContasGitPay() {
//        return ResponseEntity.ok(new ArrayList<>(contasGitPay.values()));
//        return ResponseEntity.noContent().build();
        return ResponseEntity.status(HttpStatus.OK).body("Listar contas GitPay");
    }
}
