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

    private String value;

    private Long eUiId;

    private Long labelId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Long getLabelId() {
        return labelId;
    }

    public void setLabelId(Long i18NId) {
        this.labelId = i18NId;
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
            ", value='" + getValue() + "'" +
            "}";
    }
}
