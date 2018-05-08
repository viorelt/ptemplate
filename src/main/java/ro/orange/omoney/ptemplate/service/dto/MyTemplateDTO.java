package ro.orange.omoney.ptemplate.service.dto;


import java.time.Instant;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the MyTemplate entity.
 */
public class MyTemplateDTO implements Serializable {

    private Long id;

    private Long accountId;

    private String name;

    private String createdBy;

    private Instant createdDate;

    private Long staticTemplateId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Long getStaticTemplateId() {
        return staticTemplateId;
    }

    public void setStaticTemplateId(Long templateId) {
        this.staticTemplateId = templateId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MyTemplateDTO myTemplateDTO = (MyTemplateDTO) o;
        if(myTemplateDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), myTemplateDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MyTemplateDTO{" +
            "id=" + getId() +
            ", accountId=" + getAccountId() +
            ", name='" + getName() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            "}";
    }
}
