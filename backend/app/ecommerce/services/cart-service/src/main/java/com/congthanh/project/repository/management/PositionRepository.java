package com.congthanh.project.repository.management;

import com.congthanh.project.entity.management.Position;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Transactional
public interface PositionRepository extends JpaRepository<Position, Integer> {

  Optional<Position> findById(int id);

  Optional<Position> findByName(String name);

  @Modifying
  @Query(nativeQuery = true, value = "UPDATE mydream.position SET status = 'Deteted' WHERE id = ?1")
  boolean deletePosition(int id);

}
