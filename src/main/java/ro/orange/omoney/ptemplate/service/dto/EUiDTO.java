package ro.orange.omoney.ptemplate.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import ro.orange.omoney.ptemplate.domain.enumeration.EUiType;

/**
 * A DTO for the EUi entity.
 */
public class EUiDTO implements Serializable {

    private Long id;

    private EUiType type;

    private Integer index;

    private String icon;

    private String labelKey;

    private String descriptionKey;

    private String hintKey;

    private Boolean readOnly;

    private Boolean required;

    private Boolean visible;

    private String format;

    private String validator;

    private Long labelKeyId;

    private Long descriptionKeyId;

    private Long hintKeyId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EUiType getType() {
        return type;
    }

    public void setType(EUiType type) {
        this.type = type;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getLabelKey() {
        return labelKey;
    }

    public void setLabelKey(String labelKey) {
        this.labelKey = labelKey;
    }

    public String getDescriptionKey() {
        return descriptionKey;
    }

    public void setDescriptionKey(String descriptionKey) {
        this.descriptionKey = descriptionKey;
    }

    public String getHintKey() {
        return hintKey;
    }

    public void setHintKey(String hintKey) {
        this.hintKey = hintKey;
    }

    public Boolean isReadOnly() {
        return readOnly;
    }

    public void setReadOnly(Boolean readOnly) {
        this.readOnly = readOnly;
    }

    public Boolean isRequired() {
        return required;
    }

    public void setRequired(Boolean required) {
        this.required = required;
    }

    public Boolean isVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
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

    public Long getLabelKeyId() {
        return labelKeyId;
    }

    public void setLabelKeyId(Long i18NId) {
        this.labelKeyId = i18NId;
    }

    public Long getDescriptionKeyId() {
        return descriptionKeyId;
    }

    public void setDescriptionKeyId(Long i18NId) {
        this.descriptionKeyId = i18NId;
    }

    public Long getHintKeyId() {
        return hintKeyId;
    }

    public void setHintKeyId(Long i18NId) {
        this.hintKeyId = i18NId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EUiDTO eUiDTO = (EUiDTO) o;
        if(eUiDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), eUiDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EUiDTO{" +
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
