package br.com.zup.edu.pets.controller;

import br.com.zup.edu.pets.model.Pet;
import br.com.zup.edu.pets.model.TipoDoPet;

public class PetDetalhesDTO {
	
	private String nome;
	private String raca;
	private TipoDoPet tipo;
	
	public PetDetalhesDTO(Pet pet) {
		this.nome = pet.getNome();
		this.raca = pet.getRaca();
		this.tipo = pet.getTipo();
	}
	
	public PetDetalhesDTO() {
		
	}

	public String getNome() {
		return nome;
	}

	public String getRaca() {
		return raca;
	}

	public TipoDoPet getTipo() {
		return tipo;
	}
}
