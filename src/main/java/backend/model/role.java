package backend.model;

import javax.persistence.*;

@Entity
@Table(name="roles")
public class role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Enumerated(EnumType.STRING)
    @Column
    private ERole nom;

    public role(ERole nom) {
        this.nom = nom;
    }

    public role() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ERole getNom() {
        return nom;
    }

    public void setNom(ERole nom) {
        this.nom = nom;
    }

    @Override
    public String toString() {
        return "role{" +
                "id=" + id +
                ", nom=" + nom +
                '}';
    }
}
