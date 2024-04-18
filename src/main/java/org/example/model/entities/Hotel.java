package org.example.model.entities;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
public class Hotel  implements Comparable<Hotel>{
    @Id
    @GeneratedValue
    @Column(name="ID",columnDefinition = "char(36)")
    @Type(type = "org.hibernate.type.UUIDCharType")
    private UUID id;

    @NotNull
    @Column(unique = true, length = 20, name = "nume")
    private String nume;

    @NotNull
    @Column(length = 50, name="adresa")
    private String adresa;

    @NotNull
    @Column(length = 50, name="nr_camere")
    private Integer nrCamere;

    @OneToMany(mappedBy = "locatie", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Camera> camere;

    @Override
    public int compareTo(Hotel o) {
        return this.nume.compareTo(o.getNume());
    }
}
