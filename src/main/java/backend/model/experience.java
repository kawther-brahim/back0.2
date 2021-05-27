package backend.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@Entity
@Table
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class experience implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column
    private Date datedeb;

    @Column
    private Date datefin;

    @Column
    private String societe;
    @Column
    private String poste;

    @Column
    private String description;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="id_formateur")
    private formateur formateur;

    public experience( Date datedeb, Date datefin, String societe, String poste, String description, backend.model.formateur formateur) {

        this.datedeb = datedeb;
        this.datefin = datefin;
        this.societe = societe;
        this.poste = poste;
        this.description = description;
        this.formateur = formateur;
    }

    public experience() {
    }


    public void setId(long id) {
        this.id = id;
    }

    public Date getDatedeb() {
        return datedeb;
    }

    public void setDatedeb(Date datedeb) {
        this.datedeb = datedeb;
    }

    public Date getDatefin() {
        return datefin;
    }

    public void setDatefin(Date datefin) {
        this.datefin = datefin;
    }

    public String getSociete() {
        return societe;
    }

    public void setSociete(String societe) {
        this.societe = societe;
    }

    public String getPoste() {
        return poste;
    }

    public void setPoste(String poste) {
        this.poste = poste;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public backend.model.formateur getFormateur() {
        return formateur;
    }

    public void setFormateur(backend.model.formateur formateur) {
        this.formateur = formateur;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}