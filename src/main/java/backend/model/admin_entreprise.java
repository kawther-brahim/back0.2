package backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="admin_entreprise")
public class admin_entreprise extends personne implements Serializable {


    @ManyToOne
    @JoinColumn(name="id_entreprise")
    private entreprise entreprise;

    @JsonIgnore
    @OneToMany(mappedBy = "admin")
    private Set<demandeVoucher> demandesVoucher=new HashSet<demandeVoucher>();

    public admin_entreprise() {
    }

    public admin_entreprise(@NotNull String username, @NotNull String prenom, @NotNull String email, @NotNull String password, @NotNull String adresse, @NotNull int tel, String fileName, backend.model.entreprise entreprise) {
        super(username, prenom, email, password, adresse, tel, fileName);
        this.entreprise = entreprise;
    }

    public admin_entreprise(Long cin, @NotNull String username, @NotNull String prenom, @NotNull String email, @NotNull String password, @NotNull String adresse, @NotNull int tel, String roles, backend.model.entreprise entreprise) {
        super(cin, username, prenom, email, password, adresse, tel, roles);
        this.entreprise = entreprise;
    }

    public backend.model.entreprise getEntreprise() {
        return entreprise;
    }

    public void setEntreprise(backend.model.entreprise entreprise) {
        this.entreprise = entreprise;
    }

    @Override
    public String toString() {
        return "admin_entreprise{" +
                "entreprise=" + entreprise
                + super.toString() + "]";
    }

    public Set<demandeVoucher> getDemandesVoucher() {
        return demandesVoucher;
    }

    public void setDemandesVoucher(Set<demandeVoucher> demandesVoucher) {
        this.demandesVoucher = demandesVoucher;
    }
}
