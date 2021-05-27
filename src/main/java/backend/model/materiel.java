package backend.model;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="materiel")
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class materiel implements Serializable {

	@Id
	private String reference;
	
	@NotNull
	@Column
	private String nom;
	
	@NotNull
	@Column
	private String etat;
	
    
	@ManyToOne
    @JoinColumn(name="id_salle")
	private salle salle;
	
	@JsonIgnore
	@Column
	private boolean delete=false;

	

	public materiel() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public materiel(@NotNull String nom, @NotNull String etat,backend.model.salle salle) {
		super();
		this.nom = nom;
		this.etat = etat;
		this.salle = salle;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}

	public salle getSalle() {
		return salle;
	}

	public void setSalle(salle salle) {
		this.salle = salle;
	}

	
	public String getEtat() {
		return etat;
	}

	public void setEtat(String etat) {
		this.etat = etat;
	}

	public boolean isDelete() {
		return delete;
	}

	public void setDelete(boolean delete) {
		this.delete = delete;
	}

	@Override
	public String toString() {
		return "materiel [reference=" + reference + ", nom=" + nom + ", etat=" + etat + ", salle=" + salle + "]";
	}

}
