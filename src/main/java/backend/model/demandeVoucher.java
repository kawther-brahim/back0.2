package backend.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;

@JsonIgnoreProperties
@Entity
public class demandeVoucher {


    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    Long id;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "formation_id")
    formation formation;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "examen_id")
    examen examen;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "admin_entreprise_id")
    admin_entreprise admin;

    @JsonFormat(pattern="yyyy-MM-dd")
    LocalDate date;


    int nbPlace;

    String etat;

    public demandeVoucher() {
    }

    public demandeVoucher(Long id, backend.model.formation formation, admin_entreprise admin, LocalDate date, int nbPlace, String etat) {
        this.id = id;
        this.formation = formation;
        this.admin = admin;
        this.date = date;
        this.nbPlace = nbPlace;
        this.etat = etat;
    }

    public demandeVoucher(backend.model.formation formation, admin_entreprise admin, LocalDate date, int nbPlace, String etat) {
        this.formation = formation;
        this.admin = admin;
        this.date = date;
        this.nbPlace = nbPlace;
        this.etat = etat;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public backend.model.formation getFormation() {
        return formation;
    }

    public void setFormation(backend.model.formation formation) {
        this.formation = formation;
    }

    public backend.model.examen getExamen() {
        return examen;
    }

    public void setExamen(backend.model.examen examen) {
        this.examen = examen;
    }

    public admin_entreprise getAdmin() {
        return admin;
    }

    public void setAdmin(admin_entreprise admin) {
        this.admin = admin;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getNbPlace() {
        return nbPlace;
    }

    public void setNbPlace(int nbPlace) {
        this.nbPlace = nbPlace;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    @Override
    public String toString() {
        return "demandeVoucher{" +
                "id=" + id +
                ", formation=" + formation +
                ", admin=" + admin +
                ", date=" + date +
                ", nbPlace=" + nbPlace +
                ", etat='" + etat + '\'' +
                '}';
    }
}
