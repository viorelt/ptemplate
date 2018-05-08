package ro.orange.omoney.ptemplate.domain;

import io.swagger.annotations.ApiModel;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * EValue reprezinta valoarea elementului curent si
 * este fie o valoare definita la nivel de template static,
 * fie la nivel de myTemplate.
 */
@ApiModel(description = "EValue reprezinta valoarea elementului curent si este fie o valoare definita la nivel de template static, fie la nivel de myTemplate.")
@Entity
@Table(name = "e_value")
public class EValue implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "jhi_value")
    private String value;

    @ManyToOne
    private MyTemplate myTemplate;

    @ManyToOne
    private Element element;

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

    public EValue value(String value) {
        this.value = value;
        return this;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public MyTemplate getMyTemplate() {
        return myTemplate;
    }

    public EValue myTemplate(MyTemplate myTemplate) {
        this.myTemplate = myTemplate;
        return this;
    }

    public void setMyTemplate(MyTemplate myTemplate) {
        this.myTemplate = myTemplate;
    }

    public Element getElement() {
        return element;
    }

    public EValue element(Element element) {
        this.element = element;
        return this;
    }

    public void setElement(Element element) {
        this.element = element;
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
        EValue eValue = (EValue) o;
        if (eValue.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), eValue.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EValue{" +
            "id=" + getId() +
            ", value='" + getValue() + "'" +
            "}";
    }
}
