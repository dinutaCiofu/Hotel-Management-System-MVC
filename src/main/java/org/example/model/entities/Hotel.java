package org.example.model.entities;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.view.Observer;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "hotel")
@Component
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Hotel  implements Comparable<Hotel>, Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String name;

    @NotNull
    private String address;

    @NotNull
    private Long nrRooms;

    @OneToMany(mappedBy = "location", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Room> rooms;

    @Transient
    private List<Observer> observers = new ArrayList<>();

    @Override
    public int compareTo(Hotel o) {
        return this.name.compareTo(o.getName());
    }

    @Override
    public void attach(Observer o) {
        observers.add(o);
    }

    @Override
    public void detach(Observer o) {
        observers.remove(o);
    }

    @Override
    public void notifyObservers() {
        for(Observer observer : observers){
            observer.update();
        }
    }

    public void setRooms(Set<Room> rooms) {
        this.rooms = rooms;
        notifyObservers();
    }
}
