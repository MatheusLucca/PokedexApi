package br.facom.ufms.demo.models.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PokemonDTO {
    private String nome;
    private double altura;
    private double peso;
    private Integer hp;
    private Integer ataque;
    private Integer defesa;
    private Integer ataqueEspecial;
    private Integer defesaEspecial;
    private Integer velocidade;
    private List<Integer> fraquezas;
    private List<Integer> tipos;
    private List<Integer> habilidades;
}
