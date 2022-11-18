package br.facom.ufms.demo.models.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "pokemons")
public class Pokemon {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String nome;
    private Double altura;
    private Double peso;

    @JsonManagedReference
    @ManyToMany
    @JoinTable(
            name = "pokemon_fraqueza",
            joinColumns = @JoinColumn(name = "pokemon_id"),
            inverseJoinColumns = @JoinColumn(name = "fraqueza_id")
    )
    private List<Fraqueza> fraquezas = new ArrayList<Fraqueza>();

    @JsonManagedReference
    @ManyToMany
    @JoinTable(
            name = "pokemon_tipo",
            joinColumns = @JoinColumn(name = "pokemon_id"),
            inverseJoinColumns = @JoinColumn(name = "tipo_id")
    )
    private List<Tipo> tipos = new ArrayList<Tipo>();

    @JsonManagedReference
    @ManyToMany
    @JoinTable(
            name = "pokemon_habilidade",
            joinColumns = @JoinColumn(name = "pokemon_id"),
            inverseJoinColumns = @JoinColumn(name = "habilidade_id")
    )
    private List<Habilidade> habilidades = new ArrayList<Habilidade>();


    private Integer hp;
    private Integer ataque;
    private Integer defesa;
    private Integer ataqueEspecial;
    private Integer defesaEspecial;
    private Integer velocidade;

}
