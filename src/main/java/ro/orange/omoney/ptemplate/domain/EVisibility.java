package ro.orange.omoney.ptemplate.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

import ro.orange.omoney.ptemplate.domain.enumeration.VisibilityType;

import ro.orange.omoney.ptemplate.domain.enumeration.OperandType;

/**
 * A EVisibility.
 */
@Entity
@Table(name = "e_visibility")
public class EVisibility implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "jhi_type")
    private VisibilityType type;

    @Column(name = "jhi_value")
    private String value;

    @Enumerated(EnumType.STRING)
    @Column(name = "operand")
    private OperandType operand;

    @ManyToOne
    private EUi eUi;

    @ManyToOne
    private Element parent;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public VisibilityType getType() {
        return type;
    }

    public EVisibility type(VisibilityType type) {
        this.type = type;
        return this;
    }

    public void setType(VisibilityType type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public EVisibility value(String value) {
        this.value = value;
        return this;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public OperandType getOperand() {
        return operand;
    }

    public EVisibility operand(OperandType operand) {
        this.operand = operand;
        return this;
    }

    public void setOperand(OperandType operand) {
        this.operand = operand;
    }

    public EUi getEUi() {
        return eUi;
    }

    public EVisibility eUi(EUi eUi) {
        this.eUi = eUi;
        return this;
    }

    public void setEUi(EUi eUi) {
        this.eUi = eUi;
    }

    public Element getParent() {
        return parent;
    }

    public EVisibility parent(Element element) {
        this.parent = element;
        return this;
    }

    public void setParent(Element element) {
        this.parent = element;
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
        EVisibility eVisibility = (EVisibility) o;
        if (eVisibility.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), eVisibility.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EVisibility{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", value='" + getValue() + "'" +
            ", operand='" + getOperand() + "'" +
            "}";
    }
}
