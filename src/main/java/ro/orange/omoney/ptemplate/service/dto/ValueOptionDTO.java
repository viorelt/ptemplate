package ro.orange.omoney.ptemplate.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the ValueOption entity.
 */
public class ValueOptionDTO implements Serializable {

    private Long id;

    private String label;

    private String value;

    private Long eUiId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Long getEUiId() {
        return eUiId;
    }

    public void setEUiId(Long eUiId) {
        this.eUiId = eUiId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ValueOptionDTO valueOptionDTO = (ValueOptionDTO) o;
        if(valueOptionDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), valueOptionDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ValueOptionDTO{" +
            "id=" + getId() +
            ", label='" + getLabel() + "'" +
            ", value='" + getValue() + "'" +
            "}";
    }
}
