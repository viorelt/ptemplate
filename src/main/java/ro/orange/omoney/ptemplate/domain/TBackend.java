package ro.orange.omoney.ptemplate.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * The TemplateBackend class is the extension of an attribute template
 * used for certain backend processing
 */
@ApiModel(description = "The TemplateBackend class is the extension of an attribute template used for certain backend processing")
@Entity
@Table(name = "t_backend")
public class TBackend implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    /**
     * este tipul payload-ului si poate fi: numele unei clase,
     * un anumit tip de plugin Spring cu ajutorul caruia se va face
     * maparea dintre o structura sursa (de TransactionHistory)
     * in structura necesara template-ului curent
     */
    @ApiModelProperty(value = "este tipul payload-ului si poate fi: numele unei clase, un anumit tip de plugin Spring cu ajutorul caruia se va face maparea dintre o structura sursa (de TransactionHistory) in structura necesara template-ului curent")
    @Column(name = "jhi_type")
    private String type;

    /**
     * Boolean.TRUE if the template is used for recurring payments
     */
    @ApiModelProperty(value = "Boolean.TRUE if the template is used for recurring payments")
    @Column(name = "recurrence")
    private Boolean recurrence;

    /**
     * The date on which payment is automatically made
     */
    @ApiModelProperty(value = "The date on which payment is automatically made")
    @Column(name = "recurring_date")
    private Instant recurringDate;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public TBackend type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean isRecurrence() {
        return recurrence;
    }

    public TBackend recurrence(Boolean recurrence) {
        this.recurrence = recurrence;
        return this;
    }

    public void setRecurrence(Boolean recurrence) {
        this.recurrence = recurrence;
    }

    public Instant getRecurringDate() {
        return recurringDate;
    }

    public TBackend recurringDate(Instant recurringDate) {
        this.recurringDate = recurringDate;
        return this;
    }

    public void setRecurringDate(Instant recurringDate) {
        this.recurringDate = recurringDate;
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
        TBackend tBackend = (TBackend) o;
        if (tBackend.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), tBackend.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TBackend{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", recurrence='" + isRecurrence() + "'" +
            ", recurringDate='" + getRecurringDate() + "'" +
            "}";
    }
}
