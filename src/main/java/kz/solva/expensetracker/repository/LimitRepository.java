package kz.solva.expensetracker.repository;

import kz.solva.expensetracker.model.Limit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface LimitRepository extends JpaRepository<Limit, Long> {
    @Query("SELECT l " +
            "FROM Limit l " +
            "WHERE l.id in :limitsIds " +
            "ORDER BY l.id")
    List<Limit> findAllByIdIn(List<Long> limitsIds);

    @Query("SELECT CASE WHEN COUNT(l) > 0 THEN true ELSE false END " +
            "FROM Limit l " +
            "WHERE l.id = :id " +
            "AND :now BETWEEN l.limitDatetime AND :endDate")
    boolean findByIdAndLimitDatetime(Long id, LocalDate now, LocalDate endDate);
}