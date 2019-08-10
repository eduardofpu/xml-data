package xml.com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import xml.com.service.PessoaService;

@RestController
@RequestMapping("/xml")
public class PessoaController {

    private PessoaService pessoaService;

    @Autowired
    public PessoaController(PessoaService pessoaService) {
        this.pessoaService = pessoaService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void createPessoa(@RequestParam MultipartFile caminho) {
         pessoaService.createPessoa(caminho);
    }
}
