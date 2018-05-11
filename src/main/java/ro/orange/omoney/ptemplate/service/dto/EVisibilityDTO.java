package ro.orange.omoney.ptemplate.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import ro.orange.omoney.ptemplate.domain.enumeration.VisibilityType;
import ro.orange.omoney.ptemplate.domain.enumeration.OperandType;

/**
 * A DTO for the EVisibility entity.
 */
public class EVisibilityDTO implements Serializable {

    private Long id;

    private VisibilityType type;

    private String value;

    private OperandType operand;

    private Long eUiId;

    private Long parentId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public VisibilityType getType() {
        return type;
    }

    public void setType(VisibilityType type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public OperandType getOperand() {
        return operand;
    }

    public void setOperand(OperandType operand) {
        this.operand = operand;
    }

    public Long getEUiId() {
        return eUiId;
    }

    public void setEUiId(Long eUiId) {
        this.eUiId = eUiId;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long elementId) {
        this.parentId = elementId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EVisibilityDTO eVisibilityDTO = (EVisibilityDTO) o;
        if(eVisibilityDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), eVisibilityDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EVisibilityDTO{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", value='" + getValue() + "'" +
            ", operand='" + getOperand() + "'" +
            "}";
    }
}
