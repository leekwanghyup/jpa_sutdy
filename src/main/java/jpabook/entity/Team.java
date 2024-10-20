package jpabook.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Team {
    @Id
    @Column(name = "TEAM_ID")
    private String id;
    private String name;
}
