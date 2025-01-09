package com.example.CRM.CustomerEAV.repository;

import com.example.CRM.CustomerEAV.model.Attribute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface AttributeRepository extends JpaRepository<Attribute,Integer> {

    Optional<Attribute> findByAttributeName(String attributeName);


    void deleteByAttributeId(Integer AttributeId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM NumberAttributeValue WHERE attribute_id = :attributeId", nativeQuery = true)
    void deleteNumberAttributeValues(@Param("attributeId") Integer attributeId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM DateAttributeValue WHERE attribute_id = :attributeId", nativeQuery = true)
    void deleteDateAttributeValues(@Param("attributeId") Integer attributeId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM StringAttributeValue WHERE attribute_id = :attributeId", nativeQuery = true)
    void deleteStringAttributeValues(@Param("attributeId") Integer attributeId);

    @Modifying
    @Transactional
    @Query(value = """
            DELETE FROM NumberAttributeValue WHERE attribute_id = :attributeId;
            DELETE FROM DateAttributeValue WHERE attribute_id = :attributeId;
            DELETE FROM StringAttributeValue WHERE attribute_id = :attributeId;
            DELETE FROM Attribute WHERE id = :attributeId;
            """, nativeQuery = true)
    void deleteAttributeAndValues(@Param("attributeId") Integer attributeId);

    @Transactional
    default void deleteAllAttributeValues(Integer attributeId) {
        try {
            deleteNumberAttributeValues(attributeId);
            deleteDateAttributeValues(attributeId);
            deleteStringAttributeValues(attributeId);
            deleteByAttributeId(attributeId);
        } catch (Exception e) {
            // Log error
            throw new RuntimeException("Error deleting attribute values: " + e.getMessage());
        }
    }
    // Backup method in case batch delete fails
    @Transactional
    default void deleteAllAttributeValuesWithFallback(Integer attributeId) {
        try {
            deleteAttributeAndValues(attributeId);
        } catch (Exception e) {
            // Fallback to individual deletes
            deleteNumberAttributeValues(attributeId);
            deleteDateAttributeValues(attributeId);
            deleteStringAttributeValues(attributeId);
            deleteByAttributeId(attributeId);
            throw new RuntimeException("Fallback delete completed with warning: " + e.getMessage());
        }
    }
}
