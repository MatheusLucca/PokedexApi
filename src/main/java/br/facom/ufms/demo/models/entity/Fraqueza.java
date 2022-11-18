package br.facom.ufms.demo.models.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "fraquezas")
public class Fraqueza {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String nome;

    @JsonBackReference
    @ManyToMany(mappedBy = "fraquezas", fetch = FetchType.LAZY)
    private List<Pokemon> pokemons;
}
