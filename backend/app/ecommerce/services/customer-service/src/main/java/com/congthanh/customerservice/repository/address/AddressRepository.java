package com.congthanh.customerservice.repository.address;

import com.congthanh.customerservice.model.entity.Address;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface AddressRepository extends JpaRepository<Address, Long>, AddressCustomRepository {

}
