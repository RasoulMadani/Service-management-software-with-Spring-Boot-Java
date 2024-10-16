package ir.maktabsharif.achareh.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Duty extends BaseEntity<Long> {
    public Duty(String name) {
        this.name = name;
    }

    @NotEmpty(message = "name.cannot.be.empty")
    @NotNull(message = "name.cannot.be.null")
    @Size(min = 3, max = 150, message = "name.must.be.between.3.and.15.characters")
    private String name;

    @OneToMany(targetEntity = SubDuty.class , mappedBy="duty")
    private List<SubDuty> subDuties = new ArrayList<>();
}
