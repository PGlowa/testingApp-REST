package assessment.testingapp.controllers;

import assessment.testingapp.command.MatchTesterCommand;
import assessment.testingapp.dto.TesterInfo;
import assessment.testingapp.entities.Device;
import assessment.testingapp.services.TesterMatchService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
public class TesterMatchController {

    private final TesterMatchService testerMatchService;

    @GetMapping("/devices")
    public List<Device> fetchAllDevices() {
        return testerMatchService.getAllDevices();
    }

    @GetMapping("/countries")
    public Set<String> fetchAllCountries() {
        return testerMatchService.getAllCountries();
    }

    @GetMapping("/testerinfo")
    public List<TesterInfo> fetchATestersInfo(MatchTesterCommand matchTesterCommand) {
        return testerMatchService.getTestersInfo(matchTesterCommand);
    }


}
