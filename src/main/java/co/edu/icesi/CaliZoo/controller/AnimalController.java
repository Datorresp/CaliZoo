package co.edu.icesi.CaliZoo.controller;

import co.edu.icesi.CaliZoo.api.AnimalAPI;
import co.edu.icesi.CaliZoo.dto.AnimalDTO;
import co.edu.icesi.CaliZoo.mapper.AnimalMapper;
import co.edu.icesi.CaliZoo.model.Animal;
import co.edu.icesi.CaliZoo.service.AnimalService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
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
    public AnimalDTO createAnimal(AnimalDTO userDTO) {
        return animalMapper.fromAnimal(animalService.createAnimal(animalMapper.fromDTO(userDTO)));
    }

    @Override
    public List<AnimalDTO> getAnimals() {
        return animalService.getAnimals().stream().map(animalMapper::fromAnimal).collect(Collectors.toList());
    }

    private boolean checkName(String name){

        boolean check = false;
         if(name.length() <= 120){

             //FALTA CHEQUEO ESPACIOS
             if (name.matches("/^[A-Za-z\\s]+$/g")){

                 check = true;
             }
         }
        return check;
    }

    private boolean isLlama(AnimalDTO animalDTO){

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

    private boolean validateRepeatName(String name){

        boolean check = true;
        List<Animal> list = animalService.getAnimals();

        for (int i = 0; i <= list.size(); i++){

            if (name.equalsIgnoreCase(list.get(i).getName())){

                check = false;
            }
        }

        return check;
    }

    private boolean validateDate(Date date){

        boolean check = true;
        Date today = new Date();

        if (date.after(today)){

            check = false;
        }
        return check;
    }
}
