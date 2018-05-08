package ro.orange.omoney.ptemplate.service.dto;


import java.time.Instant;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the TBackend entity.
 */
public class TBackendDTO implements Serializable {

    private Long id;

    private String type;

    private Boolean recurrence;

    private Instant recurringDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean isRecurrence() {
        return recurrence;
    }

    public void setRecurrence(Boolean recurrence) {
        this.recurrence = recurrence;
    }

    public Instant getRecurringDate() {
        return recurringDate;
    }

    public void setRecurringDate(Instant recurringDate) {
        this.recurringDate = recurringDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TBackendDTO tBackendDTO = (TBackendDTO) o;
        if(tBackendDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), tBackendDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TBackendDTO{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", recurrence='" + isRecurrence() + "'" +
            ", recurringDate='" + getRecurringDate() + "'" +
            "}";
    }
}
