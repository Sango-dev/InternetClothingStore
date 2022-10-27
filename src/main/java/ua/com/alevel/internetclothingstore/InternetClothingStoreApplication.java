package ua.com.alevel.internetclothingstore;

import org.flywaydb.core.Flyway;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class InternetClothingStoreApplication {
   /* private static final String URL = "jdbc:postgresql://localhost:5432/InternetClothingShop";
    private static final String USER = "postgres";
    private static final String PASSWORD = "rootroot";*/

    public static void main(String[] args) {
        final ConfigurableApplicationContext context = SpringApplication.run(InternetClothingStoreApplication.class, args);
        /*Flyway.configure().dataSource(URL, USER, PASSWORD)
                .baselineOnMigrate(true)
                .schemas("public")
                .load().migrate();*/

        /*PasswordEncoder encoder = context.getBean(PasswordEncoder.class);
        System.out.println(encoder.encode("admin"));*/
    }

}
