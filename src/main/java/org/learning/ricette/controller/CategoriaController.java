package org.learning.ricette.controller;

import jakarta.validation.Valid;
import org.learning.ricette.model.Categoria;
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
@RequestMapping("/categorie")
public class CategoriaController {

    @Autowired
    private RicettaRepository ricettaRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @GetMapping
    public String index(Model model) {
        List<Categoria> categoryList;
        categoryList = categoriaRepository.findAll();
        model.addAttribute("categoryList", categoryList);
        return "category/list";
    }

    @GetMapping("/create")
    public String create(Model model) {
        Categoria categoria = new Categoria();
        model.addAttribute("category", categoria);
        return "category/create";
    }

    @PostMapping("/create")
    public String category(@Valid @ModelAttribute("categoria") Categoria formCategory, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("categoria", categoriaRepository.findAll());
            return "category/create";
        }
        Categoria savedCategoria = categoriaRepository.save(formCategory);
        return "redirect:/categorie";

    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        Optional<Categoria> result = categoriaRepository.findById(id);
        if (result.isPresent()) {
            model.addAttribute("category", result.get());
            return "category/create";
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Categoria with id" + id + "not found");
        }
    }

    @PostMapping("/edit/{id}")
    public String update(@PathVariable Integer id, @Valid @ModelAttribute("category") Categoria formCategoria, BindingResult bindingResult) {

        Optional<Categoria> result = categoriaRepository.findById(id);
        if (result.isPresent()) {
            Categoria categoriaToEdit = result.get();
            if (bindingResult.hasErrors()) {
                return "category/create";
            }

            Categoria savedCategoria = categoriaRepository.save(formCategoria);
            return "redirect:/categorie";
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Categoria with id" + id + "not found");
        }
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        Optional<Categoria> result = categoriaRepository.findById(id);
        if (result.isPresent()) {
            categoriaRepository.deleteById(id);
            redirectAttributes.addFlashAttribute("redirectMessage", "La categoria " + result.get().getName() + " è stata eliminata");
            return "redirect:/categorie";
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Categoria with id" + id + "not found");
        }
    }
}
