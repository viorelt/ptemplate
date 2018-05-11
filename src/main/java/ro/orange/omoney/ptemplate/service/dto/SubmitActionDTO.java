package ro.orange.omoney.ptemplate.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the SubmitAction entity.
 */
public class SubmitActionDTO implements Serializable {

    private Long id;

    private String endpoint;

    private Long labelKeyId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public Long getLabelKeyId() {
        return labelKeyId;
    }

    public void setLabelKeyId(Long i18NId) {
        this.labelKeyId = i18NId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SubmitActionDTO submitActionDTO = (SubmitActionDTO) o;
        if(submitActionDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), submitActionDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SubmitActionDTO{" +
            "id=" + getId() +
            ", endpoint='" + getEndpoint() + "'" +
            "}";
    }
}
