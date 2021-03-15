package ai.adv.financialdata;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class FinancialDataServiceApplication {

  public static void main(String[] args) {
    SpringApplication.run(FinancialDataServiceApplication.class, args);
  }
}
