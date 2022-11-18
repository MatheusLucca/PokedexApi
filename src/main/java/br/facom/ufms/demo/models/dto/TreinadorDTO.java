package br.facom.ufms.demo.models.dto;

import br.facom.ufms.demo.models.entity.Pokebola;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TreinadorDTO {
    private String nome;
    private Integer xp;
    private List<Integer> pokebolas;
}
