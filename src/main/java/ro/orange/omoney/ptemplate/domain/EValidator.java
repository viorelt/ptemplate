package ro.orange.omoney.ptemplate.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

import ro.orange.omoney.ptemplate.domain.enumeration.ValidatorType;

/**
 * A EValidator.
 */
@Entity
@Table(name = "e_validator")
public class EValidator implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "jhi_value")
    private String value;

    @Enumerated(EnumType.STRING)
    @Column(name = "jhi_type")
    private ValidatorType type;

    @ManyToOne
    private EUi eUi;

    @ManyToOne
    private I18N errorMessage;

    @ManyToOne
    private EBackend eBackend;

    @ManyToOne
    private EUi eUi;

    @ManyToOne
    private EBackend eBackend;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public EValidator value(String value) {
        this.value = value;
        return this;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public ValidatorType getType() {
        return type;
    }

    public EValidator type(ValidatorType type) {
        this.type = type;
        return this;
    }

    public void setType(ValidatorType type) {
        this.type = type;
    }

    public EUi getEUi() {
        return eUi;
    }

    public EValidator eUi(EUi eUi) {
        this.eUi = eUi;
        return this;
    }

    public void setEUi(EUi eUi) {
        this.eUi = eUi;
    }

    public I18N getErrorMessage() {
        return errorMessage;
    }

    public EValidator errorMessage(I18N i18N) {
        this.errorMessage = i18N;
        return this;
    }

    public void setErrorMessage(I18N i18N) {
        this.errorMessage = i18N;
    }

    public EBackend getEBackend() {
        return eBackend;
    }

    public EValidator eBackend(EBackend eBackend) {
        this.eBackend = eBackend;
        return this;
    }

    public void setEBackend(EBackend eBackend) {
        this.eBackend = eBackend;
    }

    public EUi getEUi() {
        return eUi;
    }

    public EValidator eUi(EUi eUi) {
        this.eUi = eUi;
        return this;
    }

    public void setEUi(EUi eUi) {
        this.eUi = eUi;
    }

    public EBackend getEBackend() {
        return eBackend;
    }

    public EValidator eBackend(EBackend eBackend) {
        this.eBackend = eBackend;
        return this;
    }

    public void setEBackend(EBackend eBackend) {
        this.eBackend = eBackend;
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
        EValidator eValidator = (EValidator) o;
        if (eValidator.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), eValidator.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EValidator{" +
            "id=" + getId() +
            ", value='" + getValue() + "'" +
            ", type='" + getType() + "'" +
            "}";
    }
}
