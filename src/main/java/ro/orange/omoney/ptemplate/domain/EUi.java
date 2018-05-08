package ro.orange.omoney.ptemplate.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import ro.orange.omoney.ptemplate.domain.enumeration.EUiType;

/**
 * The ElementUi entity
 */
@ApiModel(description = "The ElementUi entity")
@Entity
@Table(name = "e_ui")
public class EUi implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    /**
     * tipul elementului
     */
    @ApiModelProperty(value = "tipul elementului")
    @Enumerated(EnumType.STRING)
    @Column(name = "jhi_type")
    private EUiType type;

    /**
     * ordinea elementului in pagina
     */
    @ApiModelProperty(value = "ordinea elementului in pagina")
    @Column(name = "jhi_index")
    private Integer index;

    /**
     * path-ul relativ pentru elementul curent
     */
    @ApiModelProperty(value = "path-ul relativ pentru elementul curent")
    @Column(name = "icon")
    private String icon;

    /**
     * cheia labelului pentru elementul curent
     */
    @ApiModelProperty(value = "cheia labelului pentru elementul curent")
    @Column(name = "label_key")
    private String labelKey;

    /**
     * cheia descrierii pentru elementul curent
     */
    @ApiModelProperty(value = "cheia descrierii pentru elementul curent")
    @Column(name = "description_key")
    private String descriptionKey;

    /**
     * cheia pentru sugestia aferenta elementului curent
     */
    @ApiModelProperty(value = "cheia pentru sugestia aferenta elementului curent")
    @Column(name = "hint_key")
    private String hintKey;

    /**
     * true daca elementul este readonly
     */
    @ApiModelProperty(value = "true daca elementul este readonly")
    @Column(name = "read_only")
    private Boolean readOnly;

    /**
     * true daca elementul este obligatoriu de introdus
     */
    @ApiModelProperty(value = "true daca elementul este obligatoriu de introdus")
    @Column(name = "required")
    private Boolean required;

    /**
     * true daca elementul este vizibil
     */
    @ApiModelProperty(value = "true daca elementul este vizibil")
    @Column(name = "visible")
    private Boolean visible;

    /**
     * formatul necesar interfetei grafice, care va fi aplicat
     * pe valoarea venita din backend
     */
    @ApiModelProperty(value = "formatul necesar interfetei grafice, care va fi aplicat pe valoarea venita din backend")
    @Column(name = "format")
    private String format;

    /**
     * validatorul pentru elementul curent
     */
    @ApiModelProperty(value = "validatorul pentru elementul curent")
    @Column(name = "validator")
    private String validator;

    @OneToMany(mappedBy = "eUi")
    @JsonIgnore
    private Set<ValueOption> options = new HashSet<>();

    @ManyToOne
    private I18N labelKey;

    @ManyToOne
    private I18N descriptionKey;

    @ManyToOne
    private I18N hintKey;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EUiType getType() {
        return type;
    }

    public EUi type(EUiType type) {
        this.type = type;
        return this;
    }

    public void setType(EUiType type) {
        this.type = type;
    }

    public Integer getIndex() {
        return index;
    }

    public EUi index(Integer index) {
        this.index = index;
        return this;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getIcon() {
        return icon;
    }

    public EUi icon(String icon) {
        this.icon = icon;
        return this;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getLabelKey() {
        return labelKey;
    }

    public EUi labelKey(String labelKey) {
        this.labelKey = labelKey;
        return this;
    }

    public void setLabelKey(String labelKey) {
        this.labelKey = labelKey;
    }

    public String getDescriptionKey() {
        return descriptionKey;
    }

    public EUi descriptionKey(String descriptionKey) {
        this.descriptionKey = descriptionKey;
        return this;
    }

    public void setDescriptionKey(String descriptionKey) {
        this.descriptionKey = descriptionKey;
    }

    public String getHintKey() {
        return hintKey;
    }

    public EUi hintKey(String hintKey) {
        this.hintKey = hintKey;
        return this;
    }

    public void setHintKey(String hintKey) {
        this.hintKey = hintKey;
    }

    public Boolean isReadOnly() {
        return readOnly;
    }

    public EUi readOnly(Boolean readOnly) {
        this.readOnly = readOnly;
        return this;
    }

    public void setReadOnly(Boolean readOnly) {
        this.readOnly = readOnly;
    }

    public Boolean isRequired() {
        return required;
    }

    public EUi required(Boolean required) {
        this.required = required;
        return this;
    }

    public void setRequired(Boolean required) {
        this.required = required;
    }

    public Boolean isVisible() {
        return visible;
    }

    public EUi visible(Boolean visible) {
        this.visible = visible;
        return this;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    public String getFormat() {
        return format;
    }

    public EUi format(String format) {
        this.format = format;
        return this;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getValidator() {
        return validator;
    }

    public EUi validator(String validator) {
        this.validator = validator;
        return this;
    }

    public void setValidator(String validator) {
        this.validator = validator;
    }

    public Set<ValueOption> getOptions() {
        return options;
    }

    public EUi options(Set<ValueOption> valueOptions) {
        this.options = valueOptions;
        return this;
    }

    public EUi addOptions(ValueOption valueOption) {
        this.options.add(valueOption);
        valueOption.setEUi(this);
        return this;
    }

    public EUi removeOptions(ValueOption valueOption) {
        this.options.remove(valueOption);
        valueOption.setEUi(null);
        return this;
    }

    public void setOptions(Set<ValueOption> valueOptions) {
        this.options = valueOptions;
    }

    public I18N getLabelKey() {
        return labelKey;
    }

    public EUi labelKey(I18N i18N) {
        this.labelKey = i18N;
        return this;
    }

    public void setLabelKey(I18N i18N) {
        this.labelKey = i18N;
    }

    public I18N getDescriptionKey() {
        return descriptionKey;
    }

    public EUi descriptionKey(I18N i18N) {
        this.descriptionKey = i18N;
        return this;
    }

    public void setDescriptionKey(I18N i18N) {
        this.descriptionKey = i18N;
    }

    public I18N getHintKey() {
        return hintKey;
    }

    public EUi hintKey(I18N i18N) {
        this.hintKey = i18N;
        return this;
    }

    public void setHintKey(I18N i18N) {
        this.hintKey = i18N;
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
        EUi eUi = (EUi) o;
        if (eUi.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), eUi.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EUi{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", index=" + getIndex() +
            ", icon='" + getIcon() + "'" +
            ", labelKey='" + getLabelKey() + "'" +
            ", descriptionKey='" + getDescriptionKey() + "'" +
            ", hintKey='" + getHintKey() + "'" +
            ", readOnly='" + isReadOnly() + "'" +
            ", required='" + isRequired() + "'" +
            ", visible='" + isVisible() + "'" +
            ", format='" + getFormat() + "'" +
            ", validator='" + getValidator() + "'" +
            "}";
    }
}
