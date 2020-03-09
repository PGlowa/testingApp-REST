package assessment.testingapp.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MatchTesterCommand {
    Set<String> country;
    Set<String> deviceId;
}
