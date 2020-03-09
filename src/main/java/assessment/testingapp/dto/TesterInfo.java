package assessment.testingapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TesterInfo {
    String testerId;
    String firstName;
    String lastName;
    String country;
    String lastLogin;
    Map<String, List<String>> bugsByDevice;
    Integer totalBugsCount;

}
