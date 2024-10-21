package jpabook.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Parent {

    @Id @GeneratedValue
    @Column(name = "parent_id")
    private Long id;
    private String name;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.PERSIST)
    private List<Child> childList = new ArrayList<>();

    public Parent(String name) {
        this.name = name;
    }
}
