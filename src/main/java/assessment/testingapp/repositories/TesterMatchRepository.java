package assessment.testingapp.repositories;

import assessment.testingapp.command.MatchTesterCommand;
import assessment.testingapp.dto.TesterInfo;
import assessment.testingapp.entities.*;

import java.util.List;
import java.util.Set;

public interface TesterMatchRepository {

    List<Device> getAllDevices();

    List<Bug> getBugsByTesterAndDevice(Set<String> testerId, Set<String> deviceId);

    Set<String> getTestersIdByCountries(Set<String> countries);

    Set<String> getTestersWithDevices(Set<String> devices);

    List<TesterInfo> getTestersInfo(MatchTesterCommand matchTesterCommand);

    List<Tester> getTestersById(Set<String> testerId);

    Set<String> getAllCountries();
}
