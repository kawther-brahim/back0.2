package backend.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDateTime;

@JsonIgnoreProperties
@Entity
public class inscriptionExamen {


    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    Long id;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "examen_id")
    examen examen;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id")
    client client;


    Date date;


    String etat;

    public inscriptionExamen() {
    }

    public inscriptionExamen(Long id, backend.model.examen examen, client client, Date date, String etat) {
        this.id = id;
        this.examen = examen;
        this.client = client;
        this.date = date;
        this.etat = etat;
    }

    public inscriptionExamen(backend.model.examen examen, client client, Date date, String etat) {
        this.examen = examen;
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

    public backend.model.examen getExamen() {
        return examen;
    }

    public void setExamen(backend.model.examen examen) {
        this.examen = examen;
    }

    public client getClient() {
        return client;
    }

    public void setClient(client client) {
        this.client = client;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }
}