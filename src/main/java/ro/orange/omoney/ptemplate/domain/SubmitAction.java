package ro.orange.omoney.ptemplate.domain;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A SubmitAction.
 */
@Entity
@Table(name = "submit_action")
public class SubmitAction implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    /**
     * The backend endpoint where the payment is sent to.
     */
    @ApiModelProperty(value = "The backend endpoint where the payment is sent to.")
    @Column(name = "endpoint")
    private String endpoint;

    @ManyToOne
    private I18N labelKey;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public SubmitAction endpoint(String endpoint) {
        this.endpoint = endpoint;
        return this;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public I18N getLabelKey() {
        return labelKey;
    }

    public SubmitAction labelKey(I18N i18N) {
        this.labelKey = i18N;
        return this;
    }

    public void setLabelKey(I18N i18N) {
        this.labelKey = i18N;
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
        SubmitAction submitAction = (SubmitAction) o;
        if (submitAction.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), submitAction.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SubmitAction{" +
            "id=" + getId() +
            ", endpoint='" + getEndpoint() + "'" +
            "}";
    }
}
