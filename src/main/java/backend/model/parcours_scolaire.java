package backend.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class parcours_scolaire {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column
    private Date datedeb;

    @Column
    private Date datefin;

    @Column
    private String etablissement;
    @Column
    private String specialite;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="id_formateur")
    private formateur formateur;

    public parcours_scolaire(Date datedeb, Date datefin, String etablissement, String specialite, backend.model.formateur formateur) {

        this.datedeb = datedeb;
        this.datefin = datefin;
        this.etablissement = etablissement;
        this.specialite = specialite;
        this.formateur = formateur;
    }

    public parcours_scolaire() {
    }



    public void setId(Long id) {
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

    public String getEtablissement() {
        return etablissement;
    }

    public void setEtablissement(String etablissement) {
        this.etablissement = etablissement;
    }

    public String getSpecialite() {
        return specialite;
    }

    public void setSpecialite(String specialite) {
        this.specialite = specialite;
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
}