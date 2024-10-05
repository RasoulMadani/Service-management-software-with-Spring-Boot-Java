package ir.maktabsharif.achareh.seeder;

import com.github.javafaker.Faker;
import ir.maktabsharif.achareh.entity.*;
import ir.maktabsharif.achareh.enums.RoleUserEnum;
import ir.maktabsharif.achareh.enums.StatusOrderEnum;
import ir.maktabsharif.achareh.enums.StatusUserEnum;
import ir.maktabsharif.achareh.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class DatabaseSeeder implements CommandLineRunner {
    private final UserJpaRepository userRepository;
    private final DutyJpaRepository dutyRepository;
    private final SubDutyJpaRepository subDutyRepository;
    private final OrderJpaRepository orderRepository;
    private final SuggestionJpaRepository suggestionRepository;
    @Override
    public void run(String... args) throws Exception {
        Faker faker = new Faker();

        // ایجاد Duty با داده‌های تصادفی
        Duty duty1 = dutyRepository.save(new Duty(faker.job().field()));
        Duty duty2 = dutyRepository.save(new Duty(faker.job().field()));

        // ایجاد SubDuty با داده‌های تصادفی
        SubDuty subDuty1 = subDutyRepository.save(new SubDuty(
                faker.job().keySkills(),
                faker.number().randomDouble(2, 50, 200),
                faker.lorem().sentence(15),
                duty1));

        SubDuty subDuty2 = subDutyRepository.save(new SubDuty(
                faker.job().keySkills(),
                faker.number().randomDouble(2, 50, 200),
                faker.lorem().sentence(15),
                duty2));

        // ایجاد User با داده‌های تصادفی
        User user1 = User.builder()
                .name(faker.name().firstName())
                .username(faker.name().username())
                .email(faker.internet().emailAddress())
                .password("Password123!")
                .role(RoleUserEnum.CUSTOMER)
                .status(StatusUserEnum.NEW_USER)
                .build();

        User user2 = User.builder()
                .name(faker.name().firstName())
                .username(faker.name().username())
                .email(faker.internet().emailAddress())
                .password("Password123!")
                .role(RoleUserEnum.CUSTOMER)
                .status(StatusUserEnum.NEW_USER)
                .build();

        user1 = userRepository.save(user1);
        user2 = userRepository.save(user2);

        // ارتباط بین User و SubDuty
        user1.addSubDuty(subDuty1);
        user2.addSubDuty(subDuty2);

        userRepository.saveAll(Arrays.asList(user1, user2));

        // ایجاد Order با داده‌های تصادفی
        Order order1 = Order.builder()
                .description(faker.lorem().paragraph())
                .status(StatusOrderEnum.WAITING)
                .date(LocalDate.now().plusDays(2))
                .time(LocalTime.of(10, 0))
                .user(user1)
                .subDuty(subDuty1)
                .build();
        Order order2 = Order.builder()
                .description(faker.lorem().paragraph())
                .status(StatusOrderEnum.WAITING)
                .date(LocalDate.now().plusDays(2))
                .time(LocalTime.of(10, 0))
                .user(user1)
                .subDuty(subDuty1)
                .build();

        order1 = orderRepository.save(order1);
        order2 = orderRepository.save(order2);

        // ایجاد Suggestion با داده‌های تصادفی
        Suggestion suggestion1 = Suggestion.builder()
                .suggestionPrice(faker.number().randomDouble(2, 100, 500))
                .order(order1)
                .user(user1)
                .startDate(LocalDate.now().plusDays(2))
                .startTime(LocalTime.of(11, 0))
                .durationOfWork(faker.number().numberBetween(1, 5) + " hours")
                .build();
        Suggestion suggestion2 = Suggestion.builder()
                .suggestionPrice(faker.number().randomDouble(2, 100, 500))
                .order(order1)
                .user(user2)
                .startDate(LocalDate.now().plusDays(2))
                .startTime(LocalTime.of(11, 0))
                .durationOfWork(faker.number().numberBetween(1, 5) + " hours")
                .build();

        suggestionRepository.save(suggestion1);

        System.out.println("Test data inserted using Faker.");
    }
}
