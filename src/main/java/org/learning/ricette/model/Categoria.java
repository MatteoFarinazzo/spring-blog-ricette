package org.learning.ricette.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;

@Entity
@Table(name = "categorie")
public class Categoria {
    @Id
    private Integer id;
    @NotEmpty
    private String name;
}
