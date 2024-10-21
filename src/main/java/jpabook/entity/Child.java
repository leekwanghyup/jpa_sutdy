package jpabook.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Child {

    @Id
    @GeneratedValue
    @Column(name="child_id")
    private Long id;
    private String name;

    @ManyToOne
    @JoinColumn(name="parent_id")
    Parent parent;

    public Child(String name) {
        this.name = name;
    }
}
