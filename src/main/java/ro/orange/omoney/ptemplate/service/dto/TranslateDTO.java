package ro.orange.omoney.ptemplate.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Translate entity.
 */
public class TranslateDTO implements Serializable {

    private Long id;

    private String lang;

    private String value;

    private Long i18NId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Long getI18NId() {
        return i18NId;
    }

    public void setI18NId(Long i18NId) {
        this.i18NId = i18NId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TranslateDTO translateDTO = (TranslateDTO) o;
        if(translateDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), translateDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TranslateDTO{" +
            "id=" + getId() +
            ", lang='" + getLang() + "'" +
            ", value='" + getValue() + "'" +
            "}";
    }
}
