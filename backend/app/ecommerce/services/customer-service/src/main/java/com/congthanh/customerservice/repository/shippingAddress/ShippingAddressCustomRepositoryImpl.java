package com.congthanh.customerservice.repository.shippingAddress;

import com.congthanh.customerservice.model.entity.ShippingAddress;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.List;

public class ShippingAddressCustomRepositoryImpl implements ShippingAddressCustomRepository {

    @PersistenceContext
    @Qualifier("ecommerceEntityManagerFactory")
    private EntityManager entityManager;

    @Override
    public List<ShippingAddress> getAddressByCustomerId(String customerId) {
        String sql = "SELECT a FROM ShippingAddress a WHERE a.customer = :customerId ORDER BY isDefault DESC NULLS LAST";
        TypedQuery<ShippingAddress> query = entityManager.createQuery(sql, ShippingAddress.class);
        query.setParameter("customerId", customerId);
        return query.getResultList();
    }

    @Override
    public ShippingAddress getDefaultAddressOfCustomer(String customerId) {
        String sql = "SELECT a FROM ShippingAddress a WHERE a.customer = :customerId AND a.isDefault = true";
        TypedQuery<ShippingAddress> query = entityManager.createQuery(sql, ShippingAddress.class);
        query.setParameter("customerId", customerId);
        return query.getSingleResult();
    }

    @Override
    public boolean setDefaultAddressForCustomer(String customerId, Long addressId) {
        String resetDefault = "UPDATE address SET is_default = false WHERE customer = ?1";
        Query query = entityManager.createNativeQuery(resetDefault);
        query.setParameter(1, customerId);
        query.executeUpdate();

        String updateSingleAddressSql = "UPDATE address SET is_default = true WHERE id = ?1";
        Query updateSingleAddressQuery = entityManager.createNativeQuery(updateSingleAddressSql);
        updateSingleAddressQuery.setParameter(1, addressId);
        return updateSingleAddressQuery.executeUpdate() > 0;
    }
}
