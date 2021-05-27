package backend.model;


import java.io.Serializable;
import java.util.ArrayList;

import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;


@Entity
@Table(name="salle")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) 
public class salle implements Serializable {

	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	@Column
	private String nom;
	
	@NotNull
	@Column
	private int capacite;
	
	@JsonIgnore
	@Column
	private boolean delete=false;
	
	@OneToMany(mappedBy="salle")
    private Set<formation> formation;
    
    
    @OneToMany( mappedBy = "salle")
    private List<materiel> materiel=new ArrayList<>();
  
	
	public salle() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	public salle(Long id, @NotNull String nom, @NotNull int capacite) {
		super();
		this.id = id;
		this.nom = nom;
		this.capacite = capacite;
	}


	public salle(@NotNull String nom, @NotNull int capacite) {
		super();
		this.nom = nom;
		this.capacite = capacite;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public int getCapacite() {
		return capacite;
	}
	public void setCapacite(int capacite) {
		this.capacite = capacite;
		
	}

	public boolean isDelete() {
		return delete;
	}


	public void setDelete(boolean delete) {
		this.delete = delete;
	}


	@Override
	public String toString() {
		return "salle [id=" + id + ", nom=" + nom + ", capacite=" + capacite + ", materiel=" + materiel + "]";
	}
	
	
}
