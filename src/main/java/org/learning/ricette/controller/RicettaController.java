package org.learning.ricette.controller;

import org.learning.ricette.model.Ricetta;
import org.learning.ricette.repositories.RicettaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

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

}
