package backend.model;


import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.*;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;



@Entity
@Table(name="examen",uniqueConstraints={@UniqueConstraint(columnNames = "code")})
public class examen extends service implements Serializable {

	@NotNull
	@Column
	private String code;

	@NotNull
	@Column
	private long duree;

	@NotNull
	@Column
	private String niveau;

	@Column
	private String fileName;

	@JsonIgnore
	@Column
	private boolean delete=false;

	@JsonIgnore
	@OneToMany(mappedBy = "examen")
	private Set<inscriptionExamen> inscription=new HashSet<inscriptionExamen>();

	@OneToMany( mappedBy = "examen")
	private List<voucher> voucher=new ArrayList<>();
	
	public examen() {
		super();
		// TODO Auto-generated constructor stub
	}

	public examen(@NotNull String libelle, @NotNull String description, @NotNull String constructeur, @NotNull String technologie, @NotNull double prix, @NotNull String code, @NotNull long duree, @NotNull String niveau, String fileName) {
		super(libelle, description, constructeur, technologie, prix);
		this.code = code;
		this.duree = duree;
		this.niveau = niveau;
		this.fileName = fileName;
	}

	public String getNiveau() {
		return niveau;
	}

	public void setNiveau(String niveau) {
		this.niveau = niveau;
	}


	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public boolean isDelete() {
		return delete;
	}

	public void setDelete(boolean delete) {
		this.delete = delete;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public long getDuree() {
		return duree;
	}

	public void setDuree(long duree) {
		this.duree = duree;
	}


	/*
	 @ManyToMany(cascade = CascadeType.ALL)
	    @JoinTable(name = "inscriptionexamen", joinColumns = @JoinColumn(name = "id_examen", referencedColumnName = "id"),inverseJoinColumns = @JoinColumn(name = "id_client", referencedColumnName = "id"))
		private Set<client> clients=new HashSet<>();*/
	
	
}
