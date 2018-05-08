package ro.orange.omoney.ptemplate.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Element entity.
 */
public class ElementDTO implements Serializable {

    private Long id;

    private String code;

    private String name;

    private Boolean deleted;

    private Long templateId;

    private Long uiId;

    private Long backendId;

    private Long initId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Long getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Long templateId) {
        this.templateId = templateId;
    }

    public Long getUiId() {
        return uiId;
    }

    public void setUiId(Long eUiId) {
        this.uiId = eUiId;
    }

    public Long getBackendId() {
        return backendId;
    }

    public void setBackendId(Long eBackendId) {
        this.backendId = eBackendId;
    }

    public Long getInitId() {
        return initId;
    }

    public void setInitId(Long eValueId) {
        this.initId = eValueId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ElementDTO elementDTO = (ElementDTO) o;
        if(elementDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), elementDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ElementDTO{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", name='" + getName() + "'" +
            ", deleted='" + isDeleted() + "'" +
            "}";
    }
}
