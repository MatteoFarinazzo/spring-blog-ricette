package org.learning.ricette.controller;

import jakarta.validation.Valid;
import org.learning.ricette.model.Ricetta;
import org.learning.ricette.repositories.RicettaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/ricette")
public class RicettaController {
    @Autowired
    private RicettaRepository ricettaRepository;

    @GetMapping
    public String index(Model model) {
        List<Ricetta> ricetteList = ricettaRepository.findAll();
        model.addAttribute("ricetteList", ricetteList);
        return "ricette/list";
    }

    @GetMapping("/show/{id}")
    public String show(@PathVariable Integer id, Model model) {
        Optional<Ricetta> result = ricettaRepository.findById(id);
        if (result.isPresent()) {
            Ricetta ricetta = result.get();
            model.addAttribute("ricetta", ricetta);
            return "ricette/show";
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Ricetta with id " + id + " not found");
        }
    }

    @GetMapping("/create")
    public String create(Model model) {
        Ricetta ricetta = new Ricetta();
        model.addAttribute("ricetta", ricetta);
        return "ricette/create";
    }

    @PostMapping("/create")
    public String personalizzata(@Valid @ModelAttribute("ricetta") Ricetta formRicetta, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "ricette/create";
        }
        Ricetta savedRicetta = ricettaRepository.save(formRicetta);
        return "redirect:/ricette/show/" + savedRicetta.getId();
    }

}
