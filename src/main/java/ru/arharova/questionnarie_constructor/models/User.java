package ru.arharova.questionnarie_constructor.models;

import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "AppUser")
public class User extends SecurityUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String email;

    @Enumerated(value = EnumType.STRING)
    private Role role;
    @Enumerated(value = EnumType.STRING)
    private Status status;

//    @OneToMany
//    @JoinTable(
//            name = "UsersAnswer",
//            joinColumns = {@JoinColumn(name = "appUser_id")},
//            inverseJoinColumns = {@JoinColumn(name = "answer_id")}
//    )
//    private List<Answer> answers = new LinkedList<>();
@ManyToMany(cascade = CascadeType.ALL)
@JoinTable(
        name = "UsersAnswer",
        joinColumns = {@JoinColumn(name = "appUser_id")},
        inverseJoinColumns = {@JoinColumn(name = "answer_id")}
)
private List<Answer> answers = new LinkedList<>();
}
