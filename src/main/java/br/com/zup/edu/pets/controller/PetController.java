package br.com.zup.edu.pets.controller;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.zup.edu.pets.model.Pet;
import br.com.zup.edu.pets.repository.PetRepository;

@RestController
@RequestMapping("/pets")
public class PetController {
	
	private final PetRepository repository;
	
	public PetController(PetRepository repository) {
		this.repository = repository;
	}
	
	@PostMapping
	ResponseEntity<Void> cadastrar(@RequestBody @Valid PetDTO request, UriComponentsBuilder uriComponentsBuilder){
		Pet novoPet = request.paraPet();
		
		URI location = uriComponentsBuilder.path("/pets/{id}").buildAndExpand(novoPet.getId()).toUri();
		
		return ResponseEntity.created(location).build();
	}
}
