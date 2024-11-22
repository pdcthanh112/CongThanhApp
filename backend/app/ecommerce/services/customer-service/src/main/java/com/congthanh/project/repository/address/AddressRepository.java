package com.congthanh.project.repository.address;

import com.congthanh.project.model.entity.Address;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface AddressRepository extends JpaRepository<Address, Long>, AddressCustomRepository {

}
