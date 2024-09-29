package ir.maktabsharif.achareh.entity;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Score extends BaseEntity<Long>{

    @DecimalMin(value = "0.0", message = "Price must be at least 0.0")
    @DecimalMax(value = "5.0", message = "Price must be less than or equal to 5.0")
    private Double range;


}
