package assessment.testingapp.services;

import assessment.testingapp.command.MatchTesterCommand;
import assessment.testingapp.dto.TesterInfo;
import assessment.testingapp.entities.Device;
import assessment.testingapp.repositories.TesterMatchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class TesterMatchService {

    private final TesterMatchRepository testerMatchRepository;
// Caching response as the value is unlikely to change often
    @Cacheable("get_all_devices")
    public List<Device> getAllDevices() {
        return testerMatchRepository.getAllDevices();
    }
// Caching response as the value is unlikely to change often
    @Cacheable("get_all_countries")
    public Set<String> getAllCountries() {
        return testerMatchRepository.getAllCountries();
    }

    public List<TesterInfo> getTestersInfo(MatchTesterCommand matchTesterCommand) {
        return testerMatchRepository.getTestersInfo(matchTesterCommand);
    }

}
