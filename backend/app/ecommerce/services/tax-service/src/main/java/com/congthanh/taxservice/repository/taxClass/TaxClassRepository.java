package com.congthanh.taxservice.repository.taxClass;

import com.congthanh.taxservice.model.entity.TaxClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TaxClassRepository extends JpaRepository<TaxClass, Long> {
    boolean existsByName(String name);

    @Query(value = """
         SELECT CASE
                   WHEN count(1)> 0 THEN TRUE
                   ELSE FALSE
                END
         FROM TaxClass tc
         WHERE tc.name = ?1
         AND tc.id != ?2
        """)
    boolean existsByNameNotUpdatingTaxClass(String name, Long id);
}
