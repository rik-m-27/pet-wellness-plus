package com.sm.petwellnessplus.controllers;

import com.sm.petwellnessplus.exceptions.ResourceNotFoundException;
import com.sm.petwellnessplus.models.Pet;
import com.sm.petwellnessplus.response.ApiResponse;
import com.sm.petwellnessplus.services.pet.IPetService;
import com.sm.petwellnessplus.utils.FeedBackMessage;
import com.sm.petwellnessplus.utils.UrlMapping;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(UrlMapping.PET)
@RequiredArgsConstructor
public class PetController {

    private final IPetService petService;

    @PostMapping(UrlMapping.SAVE_PETS)
    public ResponseEntity<ApiResponse> savePets(@RequestBody List<Pet> pets){
        try {
            List<Pet> savedPets = petService.savePetsForAppointment(pets);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse(FeedBackMessage.PETS_CREATE_SUCCESS, savedPets));
        }catch (Exception ex){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(ex.getMessage(), null));
        }
    }

    @GetMapping(UrlMapping.GET_PET_BY_ID)
    public ResponseEntity<ApiResponse> getPetById(@PathVariable Long petId) {
        try {
            Pet pet = petService.getPetById(petId);
            return ResponseEntity.status(HttpStatus.FOUND).body(new ApiResponse(FeedBackMessage.PET_FOUND, pet));
        }catch (ResourceNotFoundException rnfex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(rnfex.getMessage(), null));
        }catch (Exception ex){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(ex.getMessage(), null));
        }
    }

    @DeleteMapping(UrlMapping.DELETE_PET_BY_ID)
    public ResponseEntity<ApiResponse> deletePetById(@PathVariable Long petId) {
        try {
            petService.deletePet(petId);
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(FeedBackMessage.PET_DELETE_SUCCESS, null));
        }catch (ResourceNotFoundException rnfex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(rnfex.getMessage(), null));
        }catch (Exception ex){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(ex.getMessage(), null));
        }
    }

    @PutMapping(UrlMapping.UPDATE_PET)
    public ResponseEntity<ApiResponse> updatePet(@PathVariable Long petId, @RequestBody Pet pet){
        try{
            Pet thePet = petService.updatePet(pet, petId);
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(FeedBackMessage.PET_UPDATE_SUCCESS, null));
        }catch (ResourceNotFoundException rnfex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(rnfex.getMessage(), null));
        }catch (Exception ex){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(ex.getMessage(), null));
        }
    }




}
