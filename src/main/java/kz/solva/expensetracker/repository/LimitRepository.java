package kz.solva.expensetracker.repository;

import kz.solva.expensetracker.model.Limit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;

public interface LimitRepository extends JpaRepository<Limit, Long> {

    @Query("SELECT CASE WHEN COUNT(l) > 0 THEN true ELSE false END " +
            "FROM Limit l " +
            "WHERE l.id = :id " +
            "AND :now BETWEEN l.limitDatetime AND :endDate")
    boolean findByIdAndLimitDatetime(Long id, LocalDateTime now, LocalDateTime endDate);

}