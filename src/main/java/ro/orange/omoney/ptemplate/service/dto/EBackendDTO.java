package ro.orange.omoney.ptemplate.service.dto;


import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the EBackend entity.
 */
public class EBackendDTO implements Serializable {

    private Long id;

    private String name;

    private String type;

    private String format;

    private String validator;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getValidator() {
        return validator;
    }

    public void setValidator(String validator) {
        this.validator = validator;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EBackendDTO eBackendDTO = (EBackendDTO) o;
        if(eBackendDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), eBackendDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EBackendDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", type='" + getType() + "'" +
            ", format='" + getFormat() + "'" +
            ", validator='" + getValidator() + "'" +
            "}";
    }
}
