package ro.orange.omoney.ptemplate.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * Entitatea EBackend pastreaza configurarile necesare
 * procesarii elementului curent pe server
 */
@ApiModel(description = "Entitatea EBackend pastreaza configurarile necesare procesarii elementului curent pe server")
@Entity
@Table(name = "e_backend")
public class EBackend implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    /**
     * The java name of the current element
     */
    @ApiModelProperty(value = "The java name of the current element")
    @Column(name = "name")
    private String name;

    /**
     * The java type of the current element
     */
    @ApiModelProperty(value = "The java type of the current element")
    @Column(name = "jhi_type")
    private String type;

    /**
     * The java format of the current element value
     */
    @ApiModelProperty(value = "The java format of the current element value")
    @Column(name = "format")
    private String format;

    @Column(name = "required")
    private Boolean required;

    @OneToMany(mappedBy = "eBackend")
    @JsonIgnore
    private Set<EValidator> validators = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public EBackend name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public EBackend type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFormat() {
        return format;
    }

    public EBackend format(String format) {
        this.format = format;
        return this;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public Boolean isRequired() {
        return required;
    }

    public EBackend required(Boolean required) {
        this.required = required;
        return this;
    }

    public void setRequired(Boolean required) {
        this.required = required;
    }

    public Set<EValidator> getValidators() {
        return validators;
    }

    public EBackend validators(Set<EValidator> eValidators) {
        this.validators = eValidators;
        return this;
    }

    public EBackend addValidators(EValidator eValidator) {
        this.validators.add(eValidator);
        eValidator.setEBackend(this);
        return this;
    }

    public EBackend removeValidators(EValidator eValidator) {
        this.validators.remove(eValidator);
        eValidator.setEBackend(null);
        return this;
    }

    public void setValidators(Set<EValidator> eValidators) {
        this.validators = eValidators;
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
        EBackend eBackend = (EBackend) o;
        if (eBackend.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), eBackend.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EBackend{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", type='" + getType() + "'" +
            ", format='" + getFormat() + "'" +
            ", required='" + isRequired() + "'" +
            "}";
    }
}
