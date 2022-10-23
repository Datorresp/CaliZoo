package co.edu.icesi.CaliZoo.controller;

import co.edu.icesi.CaliZoo.api.AnimalAPI;
import co.edu.icesi.CaliZoo.dto.AnimalDTO;
import co.edu.icesi.CaliZoo.mapper.AnimalMapper;
import co.edu.icesi.CaliZoo.model.Animal;
import co.edu.icesi.CaliZoo.service.AnimalService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
public class AnimalController implements AnimalAPI {

    public final AnimalService animalService;
    public final AnimalMapper animalMapper;

    @Override
    public AnimalDTO getAnimal(UUID animalId) {
        return animalMapper.fromAnimal(animalService.getAnimal(animalId));
    }

    @Override
    public AnimalDTO createAnimal(AnimalDTO animalDTO) throws RuntimeException {

            if (checkName(animalDTO.getName())){

                if (validateRepeatName(animalDTO.getName())){

                    if (validateDate(animalDTO.getArrival_date())){

                        if (isLlama(animalDTO)){

                            return animalMapper.fromAnimal(animalService.createAnimal(animalMapper.fromDTO(animalDTO)));
                        }else{

                            throw new RuntimeException("Not is a Llama");
                        }
                    }else{

                        throw new RuntimeException("Arrival date must be before today");
                    }
                }else{

                    throw new RuntimeException("Name is repeat");
                }
            }else {

                throw new RuntimeException("Name's not valid");
            }
    }


    @Override
    public List<AnimalDTO> getAnimals() {
        return animalService.getAnimals().stream().map(animalMapper::fromAnimal).collect(Collectors.toList());
    }

    public boolean checkName(String name){

        boolean check = false;
         if(name.length() <= 120){

             //FALTA CHEQUEO ESPACIOS
             if (name.matches("[aA-zZ ]{0,120}")){

                 check = true;
             }
         }
        return check;
    }

    public boolean isLlama(AnimalDTO animalDTO){

        boolean check = false;

        //Maximum Llama Age is 28
        if(animalDTO.getAge() >= 0 && animalDTO.getAge() <=  28){

            if(animalDTO.getWeight() >= 10 && animalDTO.getWeight() <= 200) {

                if (animalDTO.getHeight() >= 1.1  && animalDTO.getHeight() <= 1.5){

                    check = true;
                }
            }
        }

        return check;
    }

    public boolean validateRepeatName(String name)throws RuntimeException {

        boolean check = true;
        List<Animal> list = animalService.getAnimals();

        if (list != null && name != null){
            for (int i = 0; i <= list.size() -1; i++){

                if (name.equalsIgnoreCase(list.get(i).getName())) {

                    return false;
                }
            }
        }else {

            throw new RuntimeException("Please, write animal's name");
        }

        return check;
    }

    public boolean validateDate(LocalDateTime date)throws RuntimeException {

        boolean check = true;
        LocalDateTime today = LocalDateTime.now();
        if(date != null){
            if (date.isAfter(today)){

                check = false;
            }
        }else{

            throw new RuntimeException("When the animal has been entered?");
        }
        return check;
    }

    public boolean validateParents(Animal animalId1, Animal animalId2) throws RuntimeException {

        boolean check = false;
        AnimalDTO a1 = getAnimal(animalId1.getId());
        AnimalDTO a2 = getAnimal(animalId2.getId());

        if (animalId1 != null && animalId2 != null) {
            if (a1.getSex().equals("MALE")) {

                if (a2.getSex().equals("FEMALE")) {

                    check = true;
                }
            } else {

                if (a2.getSex().equals("MALE")) {

                    check = true;
                }
            }
        }else {

            throw new RuntimeException("Parents doesn't exist, please introduce the parents");
        }
        return check;
    }
}
