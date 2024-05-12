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

@Entity
@Component
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Room implements Comparable<Room>, Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String nrRoom;

    @NotNull
    private Double price;

    @NotNull
    private Boolean isAvailable;

    @NotNull
    @Enumerated(EnumType.STRING)
    private RoomFloor floor;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    private List<RoomFacilities> facilities;


    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    @JoinColumn(name = "location")
    private Hotel location;

    @OneToMany(mappedBy = "room", fetch = FetchType.EAGER)
    private List<Reservation> reservations;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    private List<Image> images;

    @Transient
    private List<Observer> observers = new ArrayList<>();

    @Override
    public int compareTo(Room o) {
        return this.location.compareTo(o.getLocation());
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
        for (Observer observer : observers) {
            observer.update();
        }
    }

    public void setPrice(Double price) {
        this.price = price;
        notifyObservers();
    }

    public void setIsAvailable(Boolean isAvailable) {
        this.isAvailable = isAvailable;
        notifyObservers();
    }
}
