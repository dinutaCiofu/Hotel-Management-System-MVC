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
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "hotel")
@Component
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Hotel  implements Comparable<Hotel>, Subject {
    @Id
    @GeneratedValue
    @Column(name="ID",columnDefinition = "char(36)")
    @Type(type = "org.hibernate.type.UUIDCharType")
    private UUID id;

    @NotNull
    @Column(unique = true, length = 20, name = "nume")
    private String name;

    @NotNull
    @Column(length = 50, name="address")
    private String address;

    @NotNull
    @Column(length = 50, name="nr_rooms")
    private Integer nrRooms;

    @OneToMany(mappedBy = "location", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Room> rooms;

    @Override
    public int compareTo(Hotel o) {
        return this.name.compareTo(o.getName());
    }

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
