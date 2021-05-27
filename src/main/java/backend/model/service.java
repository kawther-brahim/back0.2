package backend.model;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.*;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;



@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public abstract class service implements Serializable {

	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@NotNull
	@Column
	private String libelle;

	@NotNull
	@Column
	private String description;

	@NotNull
	@Column
	private String constructeur;

	@NotNull
	@Column
	private String technologie;
	
	@NotNull
	@Column
	private double prix;
	

	
	@JsonIgnore
	@Column
	private boolean delete=false;
	
	public service() {
		super();
		// TODO Auto-generated constructor stub
	}


	public service(@NotNull String libelle, @NotNull String description, @NotNull String constructeur, @NotNull String technologie, @NotNull double prix) {
		this.libelle = libelle;
		this.description = description;
		this.constructeur = constructeur;
		this.technologie = technologie;
		this.prix = prix;
	}

	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}


	public double getPrix() {
		return prix;
	}

	public void setPrix(double prix) {
		this.prix = prix;
	}


	public boolean isDelete() {
		return delete;
	}


	public void setDelete(boolean delete) {
		this.delete = delete;
	}


	public String getConstructeur() {
		return constructeur;
	}

	public void setConstructeur(String constructeur) {
		this.constructeur = constructeur;
	}

	public String getTechnologie() {
		return technologie;
	}

	public void setTechnologie(String technologie) {
		this.technologie = technologie;
	}

	@Override
	public String toString() {
		return "service{" +
				"id=" + id +
				", libelle='" + libelle + '\'' +
				", description='" + description + '\'' +
				", constructeur='" + constructeur + '\'' +
				", technologie='" + technologie + '\'' +
				", prix=" + prix +
				", delete=" + delete +
				'}';
	}
/*@ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "inscriptionoffre", joinColumns = @JoinColumn(name = "id_offre", referencedColumnName = "id"),inverseJoinColumns = @JoinColumn(name = "id_client", referencedColumnName = "id"))
	private Set<client> clients=new HashSet<>();*/
	
	
	
}
