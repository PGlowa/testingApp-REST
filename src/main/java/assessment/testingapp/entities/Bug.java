package assessment.testingapp.entities;


import com.opencsv.bean.CsvBindByName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Bug {
    @CsvBindByName
    String bugId;
    @CsvBindByName
    String deviceId;
    @CsvBindByName
    String testerId;
}
