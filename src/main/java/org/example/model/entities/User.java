package org.example.model.entities;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.view.Observer;
import org.hibernate.annotations.Type;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Component
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User implements Subject {
    @Id
    @GeneratedValue
    @Column(name="ID",columnDefinition = "char(36)")
    @Type(type = "org.hibernate.type.UUIDCharType")
    private UUID id;

    @NotNull
    @Column(length = 50, name="nume")
    private String name;

    @NotNull
    @Column(length = 50, name="email")
    private String email;

    @NotNull
    @Column(length = 50, name="parola")
    private String password;

    @NotNull
    @Column(name="tip_utilizator")
    @Enumerated(EnumType.STRING)
    private UserType userType;

    @Override
    public void attach(Observer o) {

    }

    @Override
    public void detach(Observer o) {

    }

    @Override
    public void notifyObservers() {

    }
}
