package ir.maktabsharif.achareh.entity.bank;

import ir.maktabsharif.achareh.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Bank extends BaseEntity<Long> {
    private String name;
    private String code;
    @OneToMany(targetEntity = BankBranch.class , mappedBy="bank")
    @Builder.Default
    private List<BankBranch> branches = new ArrayList<>();
}
