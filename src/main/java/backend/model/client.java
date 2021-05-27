package backend.model;


import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public  class client extends personne implements Serializable{



	@Column
	private String niveau;

	@JsonIgnore
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "voucherClient", joinColumns = @JoinColumn(name = "client_id"), inverseJoinColumns = @JoinColumn(name = "voucher_id"))
	Set<voucher> voucher;

	@ManyToOne
	@JoinColumn(name="id_entreprise")
	private entreprise entreprise;

	@JsonIgnore
	@OneToMany(mappedBy = "client",fetch = FetchType.EAGER)
	private Set<demandedevis> demandes=new HashSet<demandedevis>();


	@JsonIgnore
	@OneToMany(mappedBy = "client",fetch = FetchType.EAGER)
	private Set<demandedevis> inscription=new HashSet<demandedevis>();

	@JsonIgnore
	@OneToMany(mappedBy = "client",fetch = FetchType.EAGER)
	private Set<demandeEntreprise> demandesentreprise=new HashSet<demandeEntreprise>();

	public client() {
		super();
		// TODO Auto-generated constructor stub
	}

	public client(Long cin, @NotNull String username, @NotNull String prenom, @NotNull String email, @NotNull String adresse, @NotNull int tel, String roles, @NotNull String niveau) {
		super(cin, username, prenom, email, adresse, tel, roles);
		this.niveau = niveau;
	}

	public client(Long cin, @NotNull String username, @NotNull String prenom, @NotNull String email, @NotNull String password, @NotNull String adresse, @NotNull int tel, @NotNull String niveau) {
		super(cin, username, prenom, email, password, adresse, tel);
		this.niveau = niveau;
	}

	public String getNiveau() {
		return niveau;
	}

	public void setNiveau(String niveau) {
		this.niveau = niveau;
	}


	@Override
	public String toString() {
		return "client [niveau=" + niveau + super.toString()+ "]";
	}

	public Set<demandedevis> getDemandes() {
		return demandes;
	}

	public void setDemandes(Set<demandedevis> demandes) {
		this.demandes = demandes;
	}

	public Set<demandedevis> getInscription() {
		return inscription;
	}

	public void setInscription(Set<demandedevis> inscription) {
		this.inscription = inscription;
	}

	public Set<backend.model.voucher> getVoucher() {
		return voucher;
	}

	public void setVoucher(Set<backend.model.voucher> voucher) {
		this.voucher = voucher;
	}

	public backend.model.entreprise getEntreprise() {
		return entreprise;
	}

	public void setEntreprise(backend.model.entreprise entreprise) {
		this.entreprise = entreprise;
	}

	public Set<demandeEntreprise> getDemandesentreprise() {
		return demandesentreprise;
	}

	public void setDemandesentreprise(Set<demandeEntreprise> demandesentreprise) {
		this.demandesentreprise = demandesentreprise;
	}
}
