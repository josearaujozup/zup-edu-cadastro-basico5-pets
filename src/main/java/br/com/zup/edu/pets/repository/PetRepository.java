package br.com.zup.edu.pets.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.zup.edu.pets.model.Pet;

public interface PetRepository extends JpaRepository<Pet, Long>{

}
