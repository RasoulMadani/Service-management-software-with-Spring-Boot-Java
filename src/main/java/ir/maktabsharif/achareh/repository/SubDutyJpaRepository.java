package ir.maktabsharif.achareh.repository;

import ir.maktabsharif.achareh.entity.SubDuty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SubDutyJpaRepository extends JpaRepository<SubDuty,Long>{

//    @Query("select s from SubDuty s where s.dutyId=1")
//    List<SubDuty> getAllSubDutiesWithDutyId();
    Optional<SubDuty> findByDutyIdAndName(Long dutyId, String name);
}
