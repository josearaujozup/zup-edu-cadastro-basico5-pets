package br.com.zup.edu.pets.controller;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
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
		
		repository.save(novoPet);
		
		URI location = uriComponentsBuilder.path("/pets/{id}").buildAndExpand(novoPet.getId()).toUri();
		
		return ResponseEntity.created(location).build();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> remove(@PathVariable("id") Long idPet){
		
		Pet pet = repository.findById(idPet).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Não existe cadastro de pet para o id informado"));
		
		repository.delete(pet);
		
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> detalhar(@PathVariable("id") Long idPet){
		
		Pet pet = repository.findById(idPet).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Não existe cadastro de pet para o id informado"));
		
		PetDetalhesDTO dto = new PetDetalhesDTO(pet);
		
		return ResponseEntity.ok(dto);
	}
	
	@GetMapping
	public ResponseEntity<?> listar(){
		
		List<Pet> pets = repository.findAll();
		
		List<PetDetalhesDTO> response = pets.stream().map(PetDetalhesDTO::new).collect(Collectors.toList());
		
		return ResponseEntity.ok(response);
	}
	
}
