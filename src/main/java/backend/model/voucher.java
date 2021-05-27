package backend.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="voucher")
public class voucher implements Serializable {
	

	@Id
	private String code;

	@JsonIgnore
	@ManyToMany(mappedBy = "voucher",fetch = FetchType.EAGER)
	Set<client> client;



	@ManyToOne
    @JoinColumn(name="id_entreprise")
	private entreprise entreprise;


	@ManyToOne
	@JoinColumn(name="id_formation")
	private formation formation;

	@ManyToOne
	@JoinColumn(name="id_examen")
	private examen examen;
	
	@JsonIgnore
	@Column
	private boolean delete=false;

	public voucher() {
		super();
		// TODO Auto-generated constructor stub
	}

	public voucher(String code, backend.model.@NotNull entreprise entreprise, backend.model.@NotNull formation formation) {
		this.code = code;
		this.entreprise = entreprise;
		this.formation = formation;
	}

	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}

	public backend.model.entreprise getEntreprise() {
		return entreprise;
	}

	public void setEntreprise(backend.model.entreprise entreprise) {
		this.entreprise = entreprise;
	}

	public boolean isDelete() {
		return delete;
	}

	public void setDelete(boolean delete) {
		this.delete = delete;
	}

	public backend.model.formation getFormation() {
		return formation;
	}

	public void setFormation(backend.model.formation formation) {
		this.formation = formation;
	}

	@Override
	public String toString() {
		return "voucher{" +
				"code='" + code + '\'' +
				", entreprise=" + entreprise +
				", formation=" + formation +
				'}';
	}

	public Set<client> getClient() {
		return client;
	}

	public void setClient(Set<client> client) {
		this.client = client;
	}

	public backend.model.examen getExamen() {
		return examen;
	}

	public void setExamen(backend.model.examen examen) {
		this.examen = examen;
	}
}














