package com.example.CRM;


import com.example.CRM.user.Benifit.model.Benefit;
import com.example.CRM.user.Benifit.repository.BenefitRepository;
import com.example.CRM.Auth.role.model.Role;
import com.example.CRM.user.user.model.User;
import com.example.CRM.Customer.CustomerEAV.model.record.AttributeRecord;
import com.example.CRM.Customer.CustomerEAV.model.record.CustomerRecord;
import com.example.CRM.Customer.CustomerEAV.service.AttributeService;
import com.example.CRM.Customer.CustomerEAV.service.CustomerService;
import com.example.CRM.Auth.role.repository.RoleRepository;
import com.example.CRM.user.user.repository.UserRepository;
import com.example.CRM.Customer.CustomerEAV.util.TypeOfValue;
import com.example.CRM.common.util.BenefitUtil;
import com.example.CRM.common.util.RoleNameUtil;
import lombok.AllArgsConstructor;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
@AllArgsConstructor
public class ApplicationRunnerInitialize {

    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final AttributeService attributeService;
    private final CustomerService customerService;
    private final BenefitRepository benefitRepository;
    @Bean
    ApplicationRunner applicationRunner() {
        return args -> {
            // Step 1: Create Benefits
            List<Benefit> benefits = List.of(
                    new Benefit(BenefitUtil.GUEST, 500, 500, BigInteger.ZERO),
                    new Benefit(BenefitUtil.SLIVER, 500, 500, BigInteger.valueOf(1_000_000)),
                    new Benefit(BenefitUtil.GOLD, 500, 500, BigInteger.valueOf(5_000_000))
            );

// Lưu các Benefit chưa tồn tại
            benefitRepository.saveAll(
                    benefits.stream()
                            .filter(b -> !benefitRepository.existsByName(b.getName()))
                            .toList()
            );

// Step 2: Create Roles
            List<Role> roles = List.of(
                    new Role(RoleNameUtil.ROOT, "Super Admin => Root"),
                    new Role(RoleNameUtil.MARKETER, "Admin of department = director")
            );

// Lưu các Role chưa tồn tại
            roleRepository.saveAll(
                    roles.stream()
                            .filter(r -> !roleRepository.existsByRoleName(r.getRoleName()))
                            .toList()
            );

// Step 3: Create Users and Assign Roles & Benefits
            Map<String, Benefit> benefitMap = benefits.stream()
                    .collect(Collectors.toMap(Benefit::getName, b -> b));

            roleRepository.findByRoleName(RoleNameUtil.ROOT).ifPresent(role -> {
                User admin = new User("admin@easyjob.com", "Trần Phúc Admin",
                        passwordEncoder.encode("admin123"),
                        role, benefitMap.get(BenefitUtil.GOLD));

                if (!userRepository.existsByEmail(admin.getEmail())) {
                    userRepository.save(admin);
                }
            });

            roleRepository.findByRoleName(RoleNameUtil.MARKETER).ifPresent(role -> {
                List<User> marketers = List.of(
                        new User("marketer1@easyjob.com", "Marketer One",
                                passwordEncoder.encode("mark123"),
                                role, benefitMap.get(BenefitUtil.GUEST)),

                        new User("marketer2@easyjob.com", "Marketer Two",
                                passwordEncoder.encode("mark123"),
                                role, benefitMap.get(BenefitUtil.SLIVER)),

                        new User("marketer3@easyjob.com", "Marketer Three",
                                passwordEncoder.encode("mark123"),
                                role, benefitMap.get(BenefitUtil.GOLD))
                );

                userRepository.saveAll(
                        marketers.stream()
                                .filter(user -> !userRepository.existsByEmail(user.getEmail()))
                                .toList()
                );
            });



            AttributeRecord attribute1 = new AttributeRecord(
                    null,"BirthPlace", TypeOfValue.STRING
            );
            attributeService.save(attribute1);
            AttributeRecord attribute2 = new AttributeRecord(
                    null,"Age", TypeOfValue.NUMBER
            );
            attributeService.save(attribute2);


            CustomerRecord customer1 = new CustomerRecord(
                    null, // ID để trống hoặc bạn có thể truyền null
                    "john.doe@example.com",
                    "John",
                    "Doe",
                    "+123456789",
                    LocalDate.of(1993, 5, 10), // Định dạng ngày sinh với cả ngày và giờ (00:00)
                    new HashMap<>() {{
                        put("Age", 30); // Thêm thuộc tính Age
                        put("BirthPlace", "New York"); // Thêm thuộc tính BirthPlace
                    }}
            );
            customerService.save(customer1);
        };
    }
}
