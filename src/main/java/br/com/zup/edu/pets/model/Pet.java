package br.com.zup.edu.pets.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Pet {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String nome;
	
	@Column(nullable = false)
    private LocalDate dataNascimento;
	
	@Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoDoPet tipo;
	
	@Column(nullable = false)
	private String raca;

	public Pet(String nome, LocalDate dataNascimento, TipoDoPet tipo, String raca) {
		this.nome = nome;
		this.dataNascimento = dataNascimento;
		this.tipo = tipo;
		this.raca = raca;
	}
	
	/**
	 * @deprecated construtor é de uso do hibernate
	 */
	@Deprecated
	public Pet() {
		
	}
	
	public Long getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public TipoDoPet getTipo() {
		return tipo;
	}

	public String getRaca() {
		return raca;
	}	
}
