package ro.orange.omoney.ptemplate.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A I18N.
 */
@Entity
@Table(name = "i_18_n")
public class I18N implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "i18N")
    @JsonIgnore
    private Set<Translate> translates = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public I18N code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public I18N description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Translate> getTranslates() {
        return translates;
    }

    public I18N translates(Set<Translate> translates) {
        this.translates = translates;
        return this;
    }

    public I18N addTranslates(Translate translate) {
        this.translates.add(translate);
        translate.setI18N(this);
        return this;
    }

    public I18N removeTranslates(Translate translate) {
        this.translates.remove(translate);
        translate.setI18N(null);
        return this;
    }

    public void setTranslates(Set<Translate> translates) {
        this.translates = translates;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        I18N i18N = (I18N) o;
        if (i18N.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), i18N.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "I18N{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
