package com.example.CRM.CustomerEAV.repository;

import com.example.CRM.CustomerEAV.model.Attribute;
import com.example.CRM.common.util.MessageUtil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface AttributeRepository extends JpaRepository<Attribute,Integer> {
    // Giữ nguyên các methods khác
    Optional<Attribute> findByAttributeName(String attributeName);
    void deleteByAttributeId(Integer AttributeId);

//    @Modifying
//    @Transactional
//    @Query(value = """
//            DELETE FROM J_NUMBERATTRIBUTEVALUE WHERE attribute_id = :attributeId;
//            DELETE FROM J_DATEATTRIBUTEVALUE WHERE attribute_id = :attributeId;
//            DELETE FROM J_STRINGATTRIBUTEVALUE WHERE attribute_id = :attributeId;
//            DELETE FROM J_ATTRIBUTE WHERE attribute_id = :attributeId;
//            """, nativeQuery = true)
//    void deleteAttributeAndValues(@Param("attributeId") Integer attributeId);

    @Modifying
    @Transactional
    @Query(value = "CALL delete_attribute_and_values(:aid)", nativeQuery = true)
    void deleteAttributeAndValues(@Param("aid") Integer attributeId);
}
