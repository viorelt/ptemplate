package ro.orange.omoney.ptemplate.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * This is an option for the element of type EUiType.SELECTION
 */
@ApiModel(description = "This is an option for the element of type EUiType.SELECTION")
@Entity
@Table(name = "value_option")
public class ValueOption implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    /**
     * The option's backend corresponding value.
     */
    @ApiModelProperty(value = "The option's backend corresponding value.")
    @Column(name = "jhi_value")
    private String value;

    @ManyToOne
    private EUi eUi;

    @ManyToOne
    private I18N label;

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

    public I18N getLabel() {
        return label;
    }

    public ValueOption label(I18N i18N) {
        this.label = i18N;
        return this;
    }

    public void setLabel(I18N i18N) {
        this.label = i18N;
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
            ", value='" + getValue() + "'" +
            "}";
    }
}
