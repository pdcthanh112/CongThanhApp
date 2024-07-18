package com.congthanh.project.repository.ecommerce.voucher;

import com.congthanh.project.entity.ecommerce.Voucher;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Qualifier;

public class VoucherCustomRepositoryImpl implements VoucherCustomRepository {

    @PersistenceContext
    @Qualifier("ecommerceEntityManagerFactory")
    private EntityManager entityManager;

    @Override
    public Voucher getVoucherByCode(String code) {
        try {
            String sql = "SELECT v FROM Voucher v WHERE v.code = :code";
            TypedQuery<Voucher> query = entityManager.createQuery(sql, Voucher.class);
            query.setParameter("code", code);
            return query.getSingleResult();
        } catch (Exception e) {
            return null;
        }

    }

    @Override
    public Long countUsedVoucher(String voucherId) {
        String sql = "SELECT ";
        TypedQuery<Voucher> query = entityManager.createQuery(sql, Voucher.class);
        return (long) query.executeUpdate();
    }
}
