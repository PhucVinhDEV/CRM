package com.example.CRM.Customer.springBatch.writer;

import com.example.CRM.Customer.CustomerEAV.model.Customer;
import com.example.CRM.Customer.CustomerEAV.repository.CustomerRepository;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;


@RequiredArgsConstructor
@Component
public class CustomerWriter {

    private final CustomerRepository customerRepository;

    @Bean
    @Qualifier("CustomerRepositoryWriter")
    public RepositoryItemWriter<Customer> RepositoryWriter(){
        RepositoryItemWriter<Customer> writer = new RepositoryItemWriter<>();
        writer.setRepository(customerRepository);
        writer.setMethodName("save");
        return writer;
    }

    @Bean
    @Qualifier("CustomerJpaWriter")
    public JpaItemWriter<Customer> jpaWriter(EntityManagerFactory entityManagerFactory) {
        JpaItemWriter<Customer> writer = new JpaItemWriter<>();
        writer.setEntityManagerFactory(entityManagerFactory);
        return writer;
    }

    @Bean
    @Qualifier("CustomerJdbcWriter")
    public JdbcBatchItemWriter<Customer> Jdbcwriter(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<Customer>()
                .itemSqlParameterSourceProvider(customer -> {
                    // Tạo một MapSqlParameterSource cho từng đối tượng Customer
                    MapSqlParameterSource parameterSource = new MapSqlParameterSource();

                    parameterSource.addValue("createdAt", customer.getCreatedAt());
                    parameterSource.addValue("createdBy", customer.getCreatedBy());
                    parameterSource.addValue("dob", customer.getDob());
                    parameterSource.addValue("email", customer.getEmail());
                    parameterSource.addValue("firstName", customer.getFirstName());
                    parameterSource.addValue("isDeleted", false);
                    parameterSource.addValue("lastModifiedAt", customer.getLastModifiedAt());
                    parameterSource.addValue("lastModifiedBy", customer.getLastModifiedBy());
                    parameterSource.addValue("lastName", customer.getLastName());
                    parameterSource.addValue("phone", customer.getPhone());
                    parameterSource.addValue("version", customer.getVersion());
                    parameterSource.addValue("id", customer.getId());
                    return parameterSource; // Trả về một SqlParameterSource
                })
                .sql("INSERT INTO j_customer (created_at, created_by, j_day_of_birth, j_email, j_firstname, is_deleted, last_modified_at, last_modified_by, j_lastname, j_phone, version, id) " +
                        "VALUES (:createdAt, :createdBy, :dob, :email, :firstName, :isDeleted, :lastModifiedAt, :lastModifiedBy, :lastName, :phone, :version, :id)")
                .dataSource(dataSource)
                .assertUpdates(false) // Tắt kiểm tra số bản ghi được cập nhật
                .build();
    }
}
