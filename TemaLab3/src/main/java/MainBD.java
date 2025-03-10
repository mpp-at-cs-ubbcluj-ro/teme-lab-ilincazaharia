import ro.mpp2024.Car;
import ro.mpp2024.CarRepository;
import ro.mpp2024.CarsDBRepository;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class MainBD {
    public static void main(String[] args) {

        Properties props=new Properties();
        try {
            props.load(new FileReader("bd.config"));
        } catch (IOException e) {
            System.out.println("Cannot find bd.config "+e);
        }

        CarRepository carRepo=new CarsDBRepository(props);

        //adaugare
        carRepo.add(new Car("Tesla","Model S", 2019));
        System.out.println("Toate masinile din db");
        for(Car car:carRepo.findAll())
            System.out.println(car);

        //cautare dupa producator
        String manufacturer="Skoda";
        System.out.println("Masinile produse de "+manufacturer);
        for(Car car:carRepo.findByManufacturer(manufacturer))
            System.out.println(car);

        //cautare dupa an intr-un interval
        int year_min = 2017;
        int year_max = 2021;
        System.out.println("Masinile cu anul de fabricatie in intervalul "+year_min+"-"+year_max);
        for(Car car:carRepo.findBetweenYears(year_min, year_max))
            System.out.println(car);

        //update la masina cu id 1
        carRepo.update(1,new Car("Dacia","Logan",2022));
        System.out.println("Toate masinile din db");
        for(Car car:carRepo.findAll())
            System.out.println(car);

    }
}