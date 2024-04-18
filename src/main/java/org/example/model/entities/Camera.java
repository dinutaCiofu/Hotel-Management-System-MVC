package org.example.model.entities;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
public class Camera implements Comparable<Camera>{
    @Id
    @GeneratedValue
    @Column(name="ID",columnDefinition = "char(36)")
    @Type(type = "org.hibernate.type.UUIDCharType")
    private UUID id;

    @Column(unique = true, length = 20, name = "numar_camera")
    private String numarCamera;

    @NotNull
    @Column(length = 50, name="pret")
    private Double pret;

    @NotNull
    @Column(length = 50, name="disponibilitate")
    private Boolean esteDisponibila;

    @NotNull
    @Column(name="pozitie_camera")
    @Enumerated(EnumType.STRING)
    private PozitieCamera pozitie;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    private List<FacilitatiCamera> facilitati;


    @ManyToOne(fetch = FetchType.EAGER, cascade=CascadeType.DETACH)
    @JoinColumn(name = "locatie")
    private Hotel locatie;

    @Override
    public int compareTo(Camera o) {
        return this.locatie.compareTo(o.getLocatie());
    }
}
