package backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


@Entity
@Table(name="formateur")
public class formateur extends personne implements Serializable {




	@Column
	private String competence;


	@NotNull
	@Enumerated(EnumType.STRING)
	private EType typeformateur;

	@OneToMany( mappedBy = "formateur")
	private List<formation> formation=new ArrayList<>();

	@JsonIgnore
	@OneToMany( mappedBy = "formateur",fetch = FetchType.EAGER)
	private List<experience> experience=new ArrayList<>();

	@JsonIgnore
	@OneToMany( mappedBy = "formateur")
	private Set<parcours_scolaire> parcours_scolaire=new HashSet<>();

	@JsonIgnore
	@OneToMany( mappedBy = "formateur")
	private Set<certification> certifications=new HashSet<>();


	public formateur() {
		super();
		// TODO Auto-generated constructor stub
	}

	public formateur(Long cin, @NotNull String username, @NotNull String prenom, @NotNull String email, @NotNull String adresse, @NotNull int tel, String roles, List<experience> experience,  @NotNull String competence,  @NotNull EType typeformateur,Set<parcours_scolaire> p) {
		super(cin, username, prenom, email, adresse, tel, roles);
		this.experience = experience;
		this.competence = competence;

		this.typeformateur = typeformateur;
		this.parcours_scolaire=p;
	}

	public formateur(@NotNull String username, @NotNull String prenom, @NotNull String email, @NotNull String password, @NotNull String adresse, @NotNull int tel, String fileName, List<experience> experience,  String competence,@NotNull EType typeformateur,Set<parcours_scolaire> p) {
		super(username, prenom, email, password, adresse, tel, fileName);
		this.experience = experience;
		this.competence = competence;
		this.typeformateur = typeformateur;
		this.parcours_scolaire=p;
	}

	public formateur(Long cin, @NotNull String username, @NotNull String prenom, @NotNull String email, @NotNull String password, @NotNull String adresse, @NotNull int tel, @NotNull String roles, List<experience> experience,  String competence, @NotNull EType typeformateur,Set<parcours_scolaire> p) {
		super(cin, username, prenom, email, password, adresse, tel,roles);
		this.experience = experience;
		this.competence = competence;
		this.typeformateur = typeformateur;
		this.parcours_scolaire=p;
	}

	public formateur(Long cin, @NotNull String username, @NotNull String prenom, @NotNull String email, @NotNull String password, @NotNull String adresse, @NotNull int tel, @NotNull EType typeformateur) {
		super(cin, username, prenom, email, password, adresse, tel);
		this.typeformateur = typeformateur;
	}

	public List<experience> getExperience() {
		return experience;
	}
	public void setExperience(List<experience> experience) {
		this.experience = experience;
	}
	public String getCompetence() {
		return competence;
	}
	public void setCompetence(String competence) {
		this.competence = competence;
	}


	public EType getTypeformateur() {
		return typeformateur;
	}

	public void setTypeformateur(EType typeformateur) {
		this.typeformateur = typeformateur;
	}


	@Override
	public String toString() {
		return "formateur [experience=" + experience + ", competence="
				+ competence + ", typeformateur="
				+ typeformateur+ ", toString()=" + super.toString() + "]";
	}


	public Set<backend.model.parcours_scolaire> getParcours_scolaire() {
		return parcours_scolaire;
	}

	public void setParcours_scolaire(Set<backend.model.parcours_scolaire> parcours_scolaire) {
		this.parcours_scolaire = parcours_scolaire;
	}

	public Set<certification> getCertifications() {
		return certifications;
	}

	public void setCertifications(Set<certification> certifications) {
		this.certifications = certifications;
	}
}
