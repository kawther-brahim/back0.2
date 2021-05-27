package backend.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.time.LocalDateTime;

@JsonIgnoreProperties
@Entity
public class demandeEntreprise {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    Long id;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "entreprise_id")
    entreprise entreprise;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id")
    client client;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    LocalDateTime date;


    String etat;

    public demandeEntreprise(Long id, backend.model.entreprise entreprise, backend.model.client client, LocalDateTime date, String etat) {
        this.id = id;
        this.entreprise = entreprise;
        this.client = client;
        this.date = date;
        this.etat = etat;
    }

    public demandeEntreprise() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public backend.model.entreprise getEntreprise() {
        return entreprise;
    }

    public void setEntreprise(backend.model.entreprise entreprise) {
        this.entreprise = entreprise;
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
}
