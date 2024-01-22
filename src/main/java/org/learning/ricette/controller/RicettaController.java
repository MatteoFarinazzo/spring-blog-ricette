package org.learning.ricette.controller;

import jakarta.validation.Valid;
import org.learning.ricette.model.Ricetta;
import org.learning.ricette.repositories.CategoriaRepository;
import org.learning.ricette.repositories.RicettaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/ricette")
public class RicettaController {
    @Autowired
    private RicettaRepository ricettaRepository;
    @Autowired
    private CategoriaRepository categoriaRepository;

    @GetMapping
    public String index(@RequestParam(name = "keyword", required = false) String searchKeyword, Model model) {
        List<Ricetta> ricetteList;
        if (searchKeyword != null) {
            ricetteList = ricettaRepository.findByTitleContaining(searchKeyword);
        } else {
            ricetteList = ricettaRepository.findAll();
        }
        model.addAttribute("ricetteList", ricetteList);
        model.addAttribute("preloadSearch", searchKeyword);
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
        model.addAttribute("category", categoriaRepository.findAll());
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

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        Optional<Ricetta> result = ricettaRepository.findById(id);
        if (result.isPresent()) {
            model.addAttribute("ricetta", result.get());
            model.addAttribute("category", categoriaRepository.findAll());
            return "ricette/create";
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Ricetta with id" + id + "not found");
        }
    }

    @PostMapping("/edit/{id}")
    public String update(@PathVariable Integer id, @Valid @ModelAttribute("ricetta") Ricetta formRicetta, BindingResult bindingResult) {

        Optional<Ricetta> result = ricettaRepository.findById(id);
        if (result.isPresent()) {
            Ricetta ricettaToEdit = result.get();
            if (bindingResult.hasErrors()) {
                return "ricette/create";
            }

            formRicetta.setImage(ricettaToEdit.getImage());
            Ricetta savedRicetta = ricettaRepository.save(formRicetta);
            return "redirect:/ricette/show/" + id;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Ricetta with id" + id + "not found");
        }
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        Optional<Ricetta> result = ricettaRepository.findById(id);
        if (result.isPresent()) {
            ricettaRepository.deleteById(id);
            redirectAttributes.addFlashAttribute("redirectMessage", "La ricetta " + result.get().getTitle() + " è stata eliminata");
            return "redirect:/ricette";
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Ricetta with id" + id + "not found");
        }
    }


}
