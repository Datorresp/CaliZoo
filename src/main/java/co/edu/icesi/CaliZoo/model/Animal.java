package co.edu.icesi.CaliZoo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@Table
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Animal {

    @Id
    @Type(type="org.hibernate.type.UUIDCharType")
    private UUID id;

    private String name;

    private Sex sex;

    private double weight;

    private int age;

    private  double height;

    private LocalDateTime arrival_date;

    private Animal [] parents = new Animal[2];
    @PrePersist
    public void generateId(){
        this.id = UUID.randomUUID();
    }



}
