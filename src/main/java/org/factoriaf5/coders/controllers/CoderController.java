package org.factoriaf5.coders.controllers;

import org.factoriaf5.coders.domain.Coder;
import org.factoriaf5.coders.repositories.CoderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api")
public class CoderController {
    private final CoderRepository coderRepository;

    @Autowired
    public CoderController(CoderRepository coderRepository) {
        this.coderRepository = coderRepository;
    }

    @GetMapping("/coders")
    public List<Coder> allCoders() {
        return coderRepository.findAll();
    }

    @GetMapping("/coders/{id}")
    public Coder findCoder(@PathVariable Long id) {
        return coderRepository.findById(id).orElseThrow(CoderNotFoundException::new);
    }

    @PostMapping("/coders")
    public Coder addCoder(@RequestBody Coder coder) {
        return coderRepository.save(coder);
    }

    @DeleteMapping("/coders/{id}")
    public Coder deleteCoderById(@PathVariable Long id) {
        Coder coder = coderRepository.findById(id).orElseThrow(CoderNotFoundException::new);
        coderRepository.deleteById(id);
        return coder;
    }

    @PutMapping("/coders")
    public Coder updateCoderById(@RequestBody Coder coder) {
        coderRepository.findById(coder.getCoderId()).orElseThrow(CoderNotFoundException::new);
        return coderRepository.save(coder);
    }
}