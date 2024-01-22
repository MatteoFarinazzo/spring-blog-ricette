package org.learning.ricette.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

@Entity
@Table
public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotEmpty
    private String name;

    @OneToMany(mappedBy = "category")
    private List<Ricetta> ricetteList;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Ricetta> getRicetteList() {
        return ricetteList;
    }

    public void setRicetteList(List<Ricetta> ricetteList) {
        this.ricetteList = ricetteList;
    }
}
