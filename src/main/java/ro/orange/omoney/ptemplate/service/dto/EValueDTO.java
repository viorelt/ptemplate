package ro.orange.omoney.ptemplate.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the EValue entity.
 */
public class EValueDTO implements Serializable {

    private Long id;

    private String value;

    private Long myTemplateId;

    private Long elementId;

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

    public Long getMyTemplateId() {
        return myTemplateId;
    }

    public void setMyTemplateId(Long myTemplateId) {
        this.myTemplateId = myTemplateId;
    }

    public Long getElementId() {
        return elementId;
    }

    public void setElementId(Long elementId) {
        this.elementId = elementId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EValueDTO eValueDTO = (EValueDTO) o;
        if(eValueDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), eValueDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EValueDTO{" +
            "id=" + getId() +
            ", value='" + getValue() + "'" +
            "}";
    }
}
