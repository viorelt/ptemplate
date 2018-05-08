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
     * cheia pentru eticheta butonului prin care se face plata
     */
    @ApiModelProperty(value = "cheia pentru eticheta butonului prin care se face plata")
    @Column(name = "label_key")
    private String labelKey;

    /**
     * endpointul la care se face plata
     */
    @ApiModelProperty(value = "endpointul la care se face plata")
    @Column(name = "endpoint")
    private String endpoint;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLabelKey() {
        return labelKey;
    }

    public SubmitAction labelKey(String labelKey) {
        this.labelKey = labelKey;
        return this;
    }

    public void setLabelKey(String labelKey) {
        this.labelKey = labelKey;
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
            ", labelKey='" + getLabelKey() + "'" +
            ", endpoint='" + getEndpoint() + "'" +
            "}";
    }
}
