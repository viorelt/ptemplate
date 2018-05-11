package ro.orange.omoney.ptemplate.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import ro.orange.omoney.ptemplate.domain.enumeration.ValidatorType;

/**
 * A DTO for the EValidator entity.
 */
public class EValidatorDTO implements Serializable {

    private Long id;

    private String value;

    private ValidatorType type;

    private Long eUiId;

    private Long errorMessageId;

    private Long eBackendId;

    private Long eUiId;

    private Long eBackendId;

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

    public ValidatorType getType() {
        return type;
    }

    public void setType(ValidatorType type) {
        this.type = type;
    }

    public Long getEUiId() {
        return eUiId;
    }

    public void setEUiId(Long eUiId) {
        this.eUiId = eUiId;
    }

    public Long getErrorMessageId() {
        return errorMessageId;
    }

    public void setErrorMessageId(Long i18NId) {
        this.errorMessageId = i18NId;
    }

    public Long getEBackendId() {
        return eBackendId;
    }

    public void setEBackendId(Long eBackendId) {
        this.eBackendId = eBackendId;
    }

    public Long getEUiId() {
        return eUiId;
    }

    public void setEUiId(Long eUiId) {
        this.eUiId = eUiId;
    }

    public Long getEBackendId() {
        return eBackendId;
    }

    public void setEBackendId(Long eBackendId) {
        this.eBackendId = eBackendId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EValidatorDTO eValidatorDTO = (EValidatorDTO) o;
        if(eValidatorDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), eValidatorDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EValidatorDTO{" +
            "id=" + getId() +
            ", value='" + getValue() + "'" +
            ", type='" + getType() + "'" +
            "}";
    }
}
