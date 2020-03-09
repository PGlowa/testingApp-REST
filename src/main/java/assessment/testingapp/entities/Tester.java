package assessment.testingapp.entities;

import com.opencsv.bean.CsvBindByName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Tester {

    @CsvBindByName
    String testerId;
    @CsvBindByName
    String firstName;
    @CsvBindByName
    String lastName;
    @CsvBindByName
    String country;
    @CsvBindByName
    String lastLogin;

}
