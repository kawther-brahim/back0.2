package backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class certification {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column
    private Date datedeb;

    @Column
    private String nom;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="id_formateur")
    private formateur formateur;

    public certification(Long id, Date datedeb,  String nom, backend.model.formateur formateur) {
        this.id = id;
        this.datedeb = datedeb;
        this.nom = nom;
        this.formateur = formateur;
    }

    public certification() {
    }

    public Long getId() {
        return id;
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



    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public backend.model.formateur getFormateur() {
        return formateur;
    }

    public void setFormateur(backend.model.formateur formateur) {
        this.formateur = formateur;
    }
}

