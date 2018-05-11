package ro.orange.omoney.ptemplate.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Translate.
 */
@Entity
@Table(name = "translate")
public class Translate implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "lang")
    private String lang;

    @Column(name = "jhi_value")
    private String value;

    @ManyToOne
    private I18N i18N;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLang() {
        return lang;
    }

    public Translate lang(String lang) {
        this.lang = lang;
        return this;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getValue() {
        return value;
    }

    public Translate value(String value) {
        this.value = value;
        return this;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public I18N getI18N() {
        return i18N;
    }

    public Translate i18N(I18N i18N) {
        this.i18N = i18N;
        return this;
    }

    public void setI18N(I18N i18N) {
        this.i18N = i18N;
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
        Translate translate = (Translate) o;
        if (translate.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), translate.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Translate{" +
            "id=" + getId() +
            ", lang='" + getLang() + "'" +
            ", value='" + getValue() + "'" +
            "}";
    }
}
