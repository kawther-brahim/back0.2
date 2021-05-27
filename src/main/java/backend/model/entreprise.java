package backend.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.*;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="entreprise")
public class entreprise implements Serializable {
	
	@Id
	private Long rcs;
	
	@NotNull
	@Column
	private String nom;


	@JsonIgnore
	@OneToMany(mappedBy = "entreprise",fetch = FetchType.EAGER)
	private Set<demandeEntreprise> demandeclient=new HashSet<demandeEntreprise>();
	
	@OneToMany( mappedBy = "entreprise")
	private List<voucher> voucher=new ArrayList<>();

	@OneToMany( mappedBy = "entreprise")
	private List<client> client=new ArrayList<>();

	@OneToMany( mappedBy = "entreprise")
	private List<admin_entreprise> admin_entreprise=new ArrayList<>();
	  
	@JsonIgnore
	@Column
	private boolean delete=false;
	
	public entreprise() {
		super();
		// TODO Auto-generated constructor stub
	}

	public entreprise(Long rcs, @NotNull String nom) {
		this.rcs = rcs;
		this.nom = nom;

	}

	public entreprise(@NotNull String nom) {
		this.nom = nom;
	}


	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	
	public Long getRcs() {
		return rcs;
	}

	public void setRcs(Long rcs) {
		this.rcs = rcs;
	}

	public boolean isDelete() {
		return delete;
	}

	public void setDelete(boolean delete) {
		this.delete = delete;
	}


	@Override
	public String toString() {
		return "entreprise{" +
				"rcs=" + rcs +
				", nom='" + nom + '\'' +

				'}';
	}

	public Set<demandeEntreprise> getDemandeclient() {
		return demandeclient;
	}

	public void setDemandeclient(Set<demandeEntreprise> demandeclient) {
		this.demandeclient = demandeclient;
	}
}
