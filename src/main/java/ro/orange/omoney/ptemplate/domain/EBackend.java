package ro.orange.omoney.ptemplate.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;

import java.io.Serializable;
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
     * numele java aferent elementului curent
     */
    @ApiModelProperty(value = "numele java aferent elementului curent")
    @Column(name = "name")
    private String name;

    /**
     * tipul java aferent elementului curent
     */
    @ApiModelProperty(value = "tipul java aferent elementului curent")
    @Column(name = "jhi_type")
    private String type;

    /**
     * formatul java aferent elementului curent
     */
    @ApiModelProperty(value = "formatul java aferent elementului curent")
    @Column(name = "format")
    private String format;

    /**
     * validatorul java aferent elementului curent
     */
    @ApiModelProperty(value = "validatorul java aferent elementului curent")
    @Column(name = "validator")
    private String validator;

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

    public String getValidator() {
        return validator;
    }

    public EBackend validator(String validator) {
        this.validator = validator;
        return this;
    }

    public void setValidator(String validator) {
        this.validator = validator;
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
            ", validator='" + getValidator() + "'" +
            "}";
    }
}
