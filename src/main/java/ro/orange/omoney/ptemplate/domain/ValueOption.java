package ro.orange.omoney.ptemplate.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * Entitatea ValueOption reprezinta un option pentru un element de tipul
 * EUiType.SELECTION
 */
@ApiModel(description = "Entitatea ValueOption reprezinta un option pentru un element de tipul EUiType.SELECTION")
@Entity
@Table(name = "value_option")
public class ValueOption implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    /**
     * label reprezinta ceea ce vizualizeaza utilizatorul
     * din cadrul unui select-option
     */
    @ApiModelProperty(value = "label reprezinta ceea ce vizualizeaza utilizatorul din cadrul unui select-option")
    @Column(name = "jhi_label")
    private String label;

    /**
     * value reprezinta ceea ce a selectat utilizatorul
     * din cadrul unui select-option si va fi trimis catre
     * backend
     */
    @ApiModelProperty(value = "value reprezinta ceea ce a selectat utilizatorul din cadrul unui select-option si va fi trimis catre backend")
    @Column(name = "jhi_value")
    private String value;

    @ManyToOne
    private EUi eUi;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public ValueOption label(String label) {
        this.label = label;
        return this;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getValue() {
        return value;
    }

    public ValueOption value(String value) {
        this.value = value;
        return this;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public EUi getEUi() {
        return eUi;
    }

    public ValueOption eUi(EUi eUi) {
        this.eUi = eUi;
        return this;
    }

    public void setEUi(EUi eUi) {
        this.eUi = eUi;
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
        ValueOption valueOption = (ValueOption) o;
        if (valueOption.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), valueOption.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ValueOption{" +
            "id=" + getId() +
            ", label='" + getLabel() + "'" +
            ", value='" + getValue() + "'" +
            "}";
    }
}
