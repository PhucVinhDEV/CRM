package com.example.CRM.Customer.CustomerEAV.repository;

import com.example.CRM.Customer.CustomerEAV.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, UUID> {
    //    @Transactional
//    @Query(value = """
//            DELETE FROM J_NUMBERATTRIBUTEVALUE WHERE attribute_id = :attributeId;
//            DELETE FROM J_DATEATTRIBUTEVALUE WHERE attribute_id = :attributeId;
//            DELETE FROM J_STRINGATTRIBUTEVALUE WHERE attribute_id = :attributeId;
//            DELETE FROM J_ATTRIBUTE WHERE attribute_id = :attributeId;
//            """, nativeQuery = true)
//    void deleteAttributeAndValues(@Param("attributeId") Integer attributeId);

    @Transactional
    @Query(value = "SELECT * FROM get_all_attribute_values(:input_customerid)", nativeQuery = true)
    List<Object[]> getAllAttributeValueByCustomer(@Param("input_customerid") UUID CustomerId);
}
