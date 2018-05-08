package ro.orange.omoney.ptemplate.service.dto;


import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the SubmitAction entity.
 */
public class SubmitActionDTO implements Serializable {

    private Long id;

    private String labelKey;

    private String endpoint;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLabelKey() {
        return labelKey;
    }

    public void setLabelKey(String labelKey) {
        this.labelKey = labelKey;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
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
            ", labelKey='" + getLabelKey() + "'" +
            ", endpoint='" + getEndpoint() + "'" +
            "}";
    }
}
