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
import java.util.List;
import java.util.UUID;

@Entity
@Component
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Room implements Comparable<Room>, Subject{
    @Id
    @GeneratedValue
    @Column(name="ID",columnDefinition = "char(36)")
    @Type(type = "org.hibernate.type.UUIDCharType")
    private UUID id;

    @Column(unique = true, length = 20, name = "numar_camera")
    private String nrRoom;

    @NotNull
    @Column(length = 50, name="pret")
    private Double price;

    @NotNull
    @Column(length = 50, name="disponibilitate")
    private Boolean isAvailable;

    @NotNull
    @Column(name="pozitie_camera")
    @Enumerated(EnumType.STRING)
    private RoomFloor floor;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    private List<RoomFacilities> facilities;


    @ManyToOne(fetch = FetchType.EAGER, cascade=CascadeType.DETACH)
    @JoinColumn(name = "location")
    private Hotel location;

    @Override
    public int compareTo(Room o) {
        return this.location.compareTo(o.getLocation());
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
