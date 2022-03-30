package br.com.zup.edu.pets.controller;

import java.time.LocalDate;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.zup.edu.pets.model.Pet;
import br.com.zup.edu.pets.model.TipoDoPet;

public class PetDTO {
	
	@NotBlank
	private String nome;
	
	@NotNull
	@PastOrPresent
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataNascimento;
	
    @NotNull
    @Enumerated(EnumType.STRING)
    private TipoDoPet tipo;
	
    @NotBlank
	private String raca;

	public PetDTO(@NotBlank String nome, @NotNull @PastOrPresent LocalDate dataNascimento, @NotNull TipoDoPet tipo,
			@NotBlank String raca) {
		this.nome = nome;
		this.dataNascimento = dataNascimento;
		this.tipo = tipo;
		this.raca = raca;
	}
    
	public PetDTO() {
		
	}
	
	public Pet paraPet() {
		return new Pet(nome, dataNascimento, tipo, raca);
	}
	
	public String getNome() {
		return nome;
	}

	public LocalDate getDataNascimento() {
		return dataNascimento;
	}

	public TipoDoPet getTipo() {
		return tipo;
	}

	public String getRaca() {
		return raca;
	}
	
}
