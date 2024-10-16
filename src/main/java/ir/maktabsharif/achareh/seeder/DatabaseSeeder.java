package ir.maktabsharif.achareh.seeder;

import com.github.javafaker.Faker;
import ir.maktabsharif.achareh.entity.*;
import ir.maktabsharif.achareh.enums.RoleUserEnum;
import ir.maktabsharif.achareh.enums.OrderStatusEnum;
import ir.maktabsharif.achareh.enums.StatusUserEnum;
import ir.maktabsharif.achareh.repository.*;
import ir.maktabsharif.achareh.repository.userRepository.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;


@RequiredArgsConstructor
@Component
public class DatabaseSeeder implements CommandLineRunner {
    private final UserJpaRepository userRepository;
    private final DutyJpaRepository dutyRepository;
    private final SubDutyJpaRepository subDutyRepository;
    private final OrderJpaRepository orderRepository;
    private final SuggestionJpaRepository suggestionRepository;
    private final FinancialJpaRepository financialJpaRepository;
    private final AddressJpaRepository addressJpaRepository;

    @Override
    public void run(String... args) throws Exception {
        if (
                dutyRepository.count() == 0
                        && userRepository.count() == 0
                        && subDutyRepository.count() == 0
                        && orderRepository.count() == 0
                        && suggestionRepository.count() == 0
        ) {
            Faker faker = new Faker();

            // ایجاد Duty با داده‌های تصادفی
            Duty duty1 = dutyRepository.save(new Duty(faker.job().field() ));
            Duty duty2 = dutyRepository.save(new Duty(faker.job().field() ));

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


            Financial financial = new Financial(user1, 1000.0);
            Financial financial1 = new Financial(user2, 2000.0);
            financialJpaRepository.saveAll(Arrays.asList(financial1, financial));

            Address address1 = new Address("tehran", "golestan", "shohada", "kjlsdj");
            Address address2 = new Address("tehran1", "golestan1", "shohada1", "kjlsdj1");
            address1 = addressJpaRepository.save(address1);
            address2 = addressJpaRepository.save(address2);

            // ایجاد Order با داده‌های تصادفی
            Order order1 = Order.builder()
                    .description(faker.lorem().paragraph())
                    .suggestionPrice(faker.number().randomDouble(2, 210, 400))
                    .status(OrderStatusEnum.WAITING)
                    .date(LocalDate.now().plusDays(2))
                    .time(LocalTime.of(10, 0))
                    .user(user1)
                    .subDuty(subDuty1)

                    .build();
            Order order2 = Order.builder()
                    .description(faker.lorem().paragraph())
                    .suggestionPrice(faker.number().randomDouble(2, 210, 400))
                    .status(OrderStatusEnum.WAITING)
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
                    .durationOfWork(faker.number().numberBetween(1, 5))
                    .build();
            Suggestion suggestion2 = Suggestion.builder()
                    .suggestionPrice(faker.number().randomDouble(2, 100, 500))
                    .order(order1)
                    .user(user2)
                    .startDate(LocalDate.now().plusDays(2))
                    .startTime(LocalTime.of(11, 0))
                    .durationOfWork(faker.number().numberBetween(1, 5))
                    .build();

            suggestionRepository.save(suggestion1);

            System.out.println("Test data inserted using Faker.");
        }
    }
}
