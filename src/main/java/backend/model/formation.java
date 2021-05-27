package backend.model;


import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.NotNull;



@Entity
@Table(name="formation")
public class formation extends service implements Serializable {


	@NotNull
	@Column
	private Date datedeb;

	@NotNull
	@Column
	private Date datefin;
	
	@Column
	private String support_cours;
	
	@ManyToOne
    @JoinColumn(name="id_salle")
	private salle salle;
    
    @ManyToOne
    @JoinColumn(name="id_formateur")
	private formateur formateur;

	@OneToMany( mappedBy = "formation")
	private List<voucher> voucher=new ArrayList<>();


	@JsonIgnore
	@OneToMany(mappedBy = "formation")
	private Set<demandedevis> demandes=new HashSet<demandedevis>();

	@JsonIgnore
	@OneToMany(mappedBy = "formation")
	private Set<demandeVoucher> demandesVoucher=new HashSet<demandeVoucher>();


	public formation() {
		super();
		// TODO Auto-generated constructor stub
	}



	public formation(@NotNull String libelle, @NotNull String description, @NotNull String constructeur, @NotNull String technologie, @NotNull double prix, @NotNull Date datedeb, @NotNull Date datefin, String support_cours, backend.model.salle salle, backend.model.formateur formateur) {
		super(libelle, description, constructeur, technologie, prix);
		this.datedeb = datedeb;
		this.datefin = datefin;
		this.support_cours = support_cours;
		this.salle = salle;
		this.formateur = formateur;
	}

	public Date getDatefin() {
		return datefin;
	}

	public void setDatefin(Date datefin) {
		this.datefin = datefin;
	}

	public Date getDatedeb() {
		return datedeb;
	}

	public void setDatedeb(Date datedeb) {
		this.datedeb = datedeb;
	}

	public salle getSalle() {
		return salle;
	}


	public void setSalle(salle salle) {
		this.salle = salle;
	}


	public formateur getFormateur() {
		return formateur;
	}


	public void setFormateur(formateur formateur) {
		this.formateur = formateur;
	}


	public String getSupport_cours() {
		return support_cours;
	}


	public void setSupport_cours(String support_cours) {
		this.support_cours = support_cours;
	}


	@Override
	public String toString() {
		return "formation{" +
				"datedeb=" + datedeb +
				", datefin=" + datefin +
				", support_cours='" + support_cours + '\'' +
				", salle=" + salle +
				", formateur=" + formateur +
				", voucher=" + voucher +
				", demandes=" + demandes +
				", demandesVoucher=" + demandesVoucher + ", toString()=" + super.toString() + "]";
	}

	public Set<demandedevis> getDemandes() {
		return demandes;
	}

	public void setDemandes(Set<demandedevis> demandes) {
		this.demandes = demandes;
	}


}















