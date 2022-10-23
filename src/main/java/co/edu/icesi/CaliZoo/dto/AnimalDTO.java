package co.edu.icesi.CaliZoo.dto;

import co.edu.icesi.CaliZoo.model.Animal;
import co.edu.icesi.CaliZoo.model.Sex;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnimalDTO {

    private UUID id;

    private String name;

    private Sex sex;

    private double weight;

    private int age;

    private  double height;

    private LocalDateTime arrival_date;

    private Animal[] parents = new Animal[2];
}
