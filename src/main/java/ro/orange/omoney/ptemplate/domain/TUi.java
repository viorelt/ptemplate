package ro.orange.omoney.ptemplate.domain;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A TUi.
 */
@Entity
@Table(name = "t_ui")
public class TUi implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    /**
     * pathul pentru iconul ce apare in dreptul fiecarui template
     * in ecranul platile mele
     */
    @ApiModelProperty(value = "pathul pentru iconul ce apare in dreptul fiecarui template in ecranul platile mele")
    @Column(name = "icon")
    private String icon;

    /**
     * reprezinta culoarea aferenta tipului de template
     */
    @ApiModelProperty(value = "reprezinta culoarea aferenta tipului de template")
    @Column(name = "box_color")
    private String boxColor;

    @OneToOne
    @JoinColumn(unique = true)
    private SubmitAction post;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIcon() {
        return icon;
    }

    public TUi icon(String icon) {
        this.icon = icon;
        return this;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getBoxColor() {
        return boxColor;
    }

    public TUi boxColor(String boxColor) {
        this.boxColor = boxColor;
        return this;
    }

    public void setBoxColor(String boxColor) {
        this.boxColor = boxColor;
    }

    public SubmitAction getPost() {
        return post;
    }

    public TUi post(SubmitAction submitAction) {
        this.post = submitAction;
        return this;
    }

    public void setPost(SubmitAction submitAction) {
        this.post = submitAction;
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
        TUi tUi = (TUi) o;
        if (tUi.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), tUi.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TUi{" +
            "id=" + getId() +
            ", icon='" + getIcon() + "'" +
            ", boxColor='" + getBoxColor() + "'" +
            "}";
    }
}
