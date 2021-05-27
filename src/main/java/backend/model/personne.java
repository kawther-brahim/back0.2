package backend.model;

import java.io.Serializable;
import java.util.*;

import javax.persistence.*;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


@Entity
@Table
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public class personne implements UserDetails,Serializable {


	@Id
	private Long cin;
	
	@NotNull
	@Column
	private String username;
	
	@NotNull
	@Column
	private String prenom;
	
	@NotNull
	@Column(unique=true)
	private String email;
	
	@NotNull
	@Column
	private String password;
	
	@NotNull
	@Column
	private String adresse;

	@NotNull
	@Column
	private int tel;

	@Column
	private String role ;

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@JsonIgnore
	@Column
	private boolean delete=false;

	@Column
	private String fileName;

	@OneToMany( mappedBy = "personne")
	private List<notification> notification=new ArrayList<>();
	
	public personne() {
		super();
		// TODO Auto-generated constructor stub
	}


	public personne(Long cin, @NotNull String username, @NotNull String prenom, @NotNull String email, @NotNull String adresse, @NotNull int tel, String roles) {
		this.cin = cin;
		this.username = username;
		this.prenom = prenom;
		this.email = email;
		this.adresse = adresse;
		this.tel = tel;
		this.role=role ;
	}

	public personne(@NotNull String username, @NotNull String prenom, @NotNull String email, @NotNull String password, @NotNull String adresse, @NotNull int tel, String fileName) {
		this.username = username;
		this.prenom = prenom;
		this.email = email;
		this.password = password;
		this.adresse = adresse;
		this.tel = tel;
		this.fileName = fileName;
	}

	public personne(Long cin, @NotNull String username, @NotNull String prenom, @NotNull String email, @NotNull String password, @NotNull String adresse, @NotNull int tel) {
		this.cin = cin;
		this.username = username;
		this.prenom = prenom;
		this.email = email;
		this.password = password;
		this.adresse = adresse;
		this.tel = tel;
	}

	public personne(Long cin, @NotNull String username, @NotNull String prenom, @NotNull String email, @NotNull String password, @NotNull String adresse, @NotNull int tel, String roles) {
		this.cin = cin;
		this.username = username;
		this.prenom = prenom;
		this.email = email;
		this.password = password;
		this.adresse = adresse;
		this.tel = tel;
		this.role=role ;
	}

	public String getUsername() {
		return username;
	}

	@JsonIgnore
	@Transient
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@JsonIgnore
	@Transient
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@JsonIgnore
	@Transient
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@JsonIgnore
	@Transient
	@Override
	public boolean isEnabled() {
		return true;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	@JsonIgnore
	@Transient
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority(role));
		return authorities;
	}

	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getAdresse() {
		return adresse;
	}
	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}
	public int getTel() {
		return tel;
	}
	public void setTel(int tel) {
		this.tel = tel;
	}



	public Long getCin() {
		return cin;
	}

	public void setCin(Long cin) {
		this.cin = cin;
	}


	public boolean isDelete() {
		return delete;
	}


	public void setDelete(boolean delete) {
		this.delete = delete;
	}


	@Override
	public String toString() {
		return "personne [nom=" + username + ", prenom=" + prenom + ", email=" + email
				+ ", password=" + password + ", adresse=" + adresse + ", tel=" + tel + ", cin=" + cin + "]";
	}




}
