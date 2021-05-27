package backend.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table
public class notification implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column
    private String content;

    @NotNull
    @Column
    private boolean viewed;

    @ManyToOne
    @JoinColumn(name="cin_personne")
    private personne personne;

    public notification() {
        super();
    }

    public notification(@NotNull String content) {
        this.content = content;
    }

    public notification(@NotNull String content, @NotNull boolean viewed, backend.model.personne personne) {
        this.content = content;
        this.viewed = viewed;
        this.personne = personne;
    }

    public notification(Long id, @NotNull String content, @NotNull boolean viewed, backend.model.personne personne) {
        this.id = id;
        this.content = content;
        this.viewed = viewed;
        this.personne = personne;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public backend.model.personne getPersonne() {
        return personne;
    }

    public void setPersonne(backend.model.personne personne) {
        this.personne = personne;
    }

    public boolean isViewed() {
        return viewed;
    }

    public void setViewed(boolean viewed) {
        this.viewed = viewed;
    }

    @Override
    public String toString() {
        return "notification{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", viewed=" + viewed +
                ", personne=" + personne +
                '}';
    }
}
