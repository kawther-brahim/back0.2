package backend.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@JsonIgnoreProperties
@Entity
public class demandedevis {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    Long id;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "formation_id")
    formation formation;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id")
    client client;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    LocalDateTime date;


    String etat;


    public demandedevis() {
    }

    public backend.model.formation getFormation() {
        return formation;
    }

    public void setFormation(backend.model.formation formation) {
        this.formation = formation;
    }

    public demandedevis(backend.model.formation formation, clientPassager client, LocalDateTime date, String etat) {
        this.formation = formation;
        this.client = client;
        this.date = date;
        this.etat = etat;
    }

    public demandedevis(Long id, backend.model.@NotNull formation formation, client client, LocalDateTime date, String etat) {
        this.id = id;
        this.formation = formation;
        this.client = client;
        this.date = date;
        this.etat = etat;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public backend.model.client getClient() {
        return client;
    }

    public void setClient(backend.model.client client) {
        this.client = client;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    @Override
    public String toString() {
        return "demandedevis{" +
                "id=" + id +
                ", formation=" + formation +
                ", client=" + client +
                ", date=" + date +
                ", etat='" + etat + '\'' +
                '}';
    }
}