package com.congthanh.project.repository.ecommerce.store;

import com.congthanh.project.entity.ecommerce.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StoreRepository extends JpaRepository<Store, String>, StoreCustomRepository {

    Optional<Store> findById(String id);

}
