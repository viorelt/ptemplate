package ro.orange.omoney.ptemplate.service.dto;


import java.time.Instant;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Template entity.
 */
public class TemplateDTO implements Serializable {

    private Long id;

    private String code;

    private String createdBy;

    private Instant createdDate;

    private Boolean deleted;

    private Long uiId;

    private Long backendId;

    private Long parentId;

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

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public Boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Long getUiId() {
        return uiId;
    }

    public void setUiId(Long tUiId) {
        this.uiId = tUiId;
    }

    public Long getBackendId() {
        return backendId;
    }

    public void setBackendId(Long tBackendId) {
        this.backendId = tBackendId;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long templateId) {
        this.parentId = templateId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TemplateDTO templateDTO = (TemplateDTO) o;
        if(templateDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), templateDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TemplateDTO{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", deleted='" + isDeleted() + "'" +
            "}";
    }
}
