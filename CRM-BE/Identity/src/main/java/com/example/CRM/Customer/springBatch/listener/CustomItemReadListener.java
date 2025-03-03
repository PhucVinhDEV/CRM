package com.example.CRM.Customer.springBatch.listener;

import com.example.CRM.Customer.CustomerEAV.model.Customer;
import org.springframework.batch.core.ItemReadListener;

public class CustomItemReadListener implements ItemReadListener<Customer> {
    @Override
    public void beforeRead() {
        // Không cần làm gì trước khi đọc
    }

    @Override
    public void afterRead(Customer item) {
        // Không cần làm gì sau khi đọc thành công
    }

    @Override
    public void onReadError(Exception ex) {
        // Xử lý lỗi khi đọc
        System.err.println("Error reading item: " + ex.getMessage());
    }
}
