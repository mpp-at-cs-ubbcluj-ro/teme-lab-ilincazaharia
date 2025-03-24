package ro.mpp2024;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ro.mpp2024.repository.ComputerRepairRequestRepository;
import ro.mpp2024.repository.ComputerRepairedFormRepository;
import ro.mpp2024.repository.jdbc.ComputerRepairRequestJdbcRepository;
import ro.mpp2024.repository.jdbc.ComputerRepairedFormJdbcRepository;
import ro.mpp2024.repository.mock.ComputerRepairRequestInMemoryRepository;
import ro.mpp2024.repository.mock.ComputerRepairedFormInMemoryRepository;
import ro.mpp2024.services.ComputerRepairServices;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

@Configuration
public class RepairShopConfig {
    @Bean
    Properties getProps() {
        Properties props = new Properties();
        try {
            System.out.println("Searchin bd.config in directory"+((new File(".")).getAbsolutePath()));
            props.load(new FileReader(new File("bd.config")));
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalStateException("Config file not found!");
        }
        return props;
    }

    @Bean
    ComputerRepairRequestRepository requestsRepo(){

        return new ComputerRepairRequestJdbcRepository(getProps());
    }

    @Bean
    ComputerRepairedFormRepository formsRepo(){
       return new ComputerRepairedFormJdbcRepository(getProps());

    }

    @Bean
    ComputerRepairServices services(){
        return new ComputerRepairServices(requestsRepo(),formsRepo());
    }

}
