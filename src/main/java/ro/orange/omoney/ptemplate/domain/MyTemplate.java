package ro.orange.omoney.ptemplate.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A MyTemplate.
 */
@Entity
@Table(name = "my_template")
public class MyTemplate implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    /**
     * The user account identifier
     */
    @ApiModelProperty(value = "The user account identifier")
    @Column(name = "account_id")
    private Long accountId;

    /**
     * numele sub care a fost salvata plata mea
     */
    @ApiModelProperty(value = "numele sub care a fost salvata plata mea")
    @Column(name = "name")
    private String name;

    /**
     * Who created the version
     */
    @ApiModelProperty(value = "Who created the version")
    @Column(name = "created_by")
    private String createdBy;

    /**
     * When the version was created
     */
    @ApiModelProperty(value = "When the version was created")
    @Column(name = "created_date")
    private Instant createdDate;

    @OneToMany(mappedBy = "myTemplate")
    @JsonIgnore
    private Set<EValue> properties = new HashSet<>();

    @ManyToOne
    private Template staticTemplate;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAccountId() {
        return accountId;
    }

    public MyTemplate accountId(Long accountId) {
        this.accountId = accountId;
        return this;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getName() {
        return name;
    }

    public MyTemplate name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public MyTemplate createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public MyTemplate createdDate(Instant createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public Set<EValue> getProperties() {
        return properties;
    }

    public MyTemplate properties(Set<EValue> eValues) {
        this.properties = eValues;
        return this;
    }

    public MyTemplate addProperties(EValue eValue) {
        this.properties.add(eValue);
        eValue.setMyTemplate(this);
        return this;
    }

    public MyTemplate removeProperties(EValue eValue) {
        this.properties.remove(eValue);
        eValue.setMyTemplate(null);
        return this;
    }

    public void setProperties(Set<EValue> eValues) {
        this.properties = eValues;
    }

    public Template getStaticTemplate() {
        return staticTemplate;
    }

    public MyTemplate staticTemplate(Template template) {
        this.staticTemplate = template;
        return this;
    }

    public void setStaticTemplate(Template template) {
        this.staticTemplate = template;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MyTemplate myTemplate = (MyTemplate) o;
        if (myTemplate.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), myTemplate.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MyTemplate{" +
            "id=" + getId() +
            ", accountId=" + getAccountId() +
            ", name='" + getName() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            "}";
    }
}
