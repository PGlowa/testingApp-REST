package assessment.testingapp.repositories;

import assessment.testingapp.command.MatchTesterCommand;
import assessment.testingapp.dto.TesterInfo;
import assessment.testingapp.entities.*;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class TesterMachRepositoryImpl implements TesterMatchRepository {
    @Autowired
    ResourceLoader resourceLoader;

    private static final String DEVICES_CSV = "src/main/resources/csv/devices.csv";
    private static final String BUGS_CSV = "src/main/resources/csv/bugs.csv";
    private static final String TESTERS_CSV = "src/main/resources/csv/testers.csv";
    private static final String TESTERS_DEVICES_CSV = "src/main/resources/csv/tester_device.csv";

    public List<Device> getAllDevices() {
        ArrayList<Device> deviceList = new ArrayList<Device>();
        try (
                Reader reader = Files.newBufferedReader(Paths.get(DEVICES_CSV));
        ) {
// create POJO from csv data
            CsvToBean<Device> csvToBean = new CsvToBeanBuilder(reader)
                    .withType(Device.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();

            for (Device device : (Iterable<Device>) csvToBean) {
                if (device.getDeviceId() != null) {
                    deviceList.add(device);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return deviceList;
    }

    public Set<String> getAllCountries() {
        Set<String> countries = new HashSet<>();
        try (
                Reader reader = Files.newBufferedReader(Paths.get(TESTERS_CSV));
        ) {
// create POJO from csv data
            CsvToBean<Tester> csvToBean = new CsvToBeanBuilder(reader)
                    .withType(Tester.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();

            for (Tester tester : (Iterable<Tester>) csvToBean) {
                if (tester.getTesterId() != null) {
                    countries.add(tester.getCountry());
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return countries;
    }

    public List<Bug> getBugsByTesterAndDevice(Set<String> testerId, Set<String> deviceId) {
        Map<String, List<String>> deviceBugMap = new HashMap<>();
        Map<String, Map<String, List<String>>> bugsList = new HashMap<>();
        List<Bug> bugs = new ArrayList<>();
        try (
                Reader reader = Files.newBufferedReader(Paths.get(BUGS_CSV));
        ) {
// create POJO from csv data
            CsvToBean<Bug> csvToBean = new CsvToBeanBuilder(reader)
                    .withType(Bug.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();


            for (Bug bug : (Iterable<Bug>) csvToBean) {
                if (bug.getBugId() != null && testerId.contains(bug.getTesterId()) && deviceId.contains(bug.getDeviceId())) {
                    bugs.add(bug);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return bugs;
    }


    public Set<String> getTestersIdByCountries(Set<String> countries) {

        Set<String> testersList = new HashSet<>();
        try (
                Reader reader = Files.newBufferedReader(Paths.get(TESTERS_CSV));
        ) {
// create POJO from csv data
            CsvToBean<Tester> csvToBean = new CsvToBeanBuilder(reader)
                    .withType(Tester.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();

            for (Tester tester : (Iterable<Tester>) csvToBean) {
                if (tester.getTesterId() != null && countries != null && countries.contains(tester.getCountry())) {
                    testersList.add(tester.getTesterId());
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return testersList;
    }


    public List<Tester> getTestersById(Set<String> testerIds) {

        List<Tester> testers = new ArrayList<>();
        try (
                Reader reader = Files.newBufferedReader(Paths.get(TESTERS_CSV));
        ) {
// create POJO from csv data
            CsvToBean<Tester> csvToBean = new CsvToBeanBuilder(reader)
                    .withType(Tester.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();

            for (Tester tester : (Iterable<Tester>) csvToBean) {
                if (tester.getTesterId() != null && testerIds.contains(tester.getTesterId())) {
                    testers.add(tester);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return testers;
    }


    public Set<String> getTestersWithDevices(Set<String> devices) {
        Set<String> testersDevicesList = new HashSet<>();
        try (
                Reader reader = Files.newBufferedReader(Paths.get(TESTERS_DEVICES_CSV));
        ) {
// create POJO from csv data
            CsvToBean<TesterDevices> csvToBean = new CsvToBeanBuilder(reader)
                    .withType(TesterDevices.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();


            for (TesterDevices testerDevices : (Iterable<TesterDevices>) csvToBean) {
                if (testerDevices.getTesterId() != null && devices != null && devices.contains(testerDevices.getDeviceId())) {
                    testersDevicesList.add(testerDevices.getTesterId());
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return testersDevicesList;
    }

    public List<TesterInfo> getTestersInfo(MatchTesterCommand matchTesterCommand) {

        Set<String> testerIdSet = this.getTestersIdByCountries(matchTesterCommand.getCountry());
        Set<String> devicesTesterIdSet = this.getTestersWithDevices(matchTesterCommand.getDeviceId());
// leave only overlaping testers ids
        testerIdSet.retainAll(devicesTesterIdSet);
        List<Bug> bugs = this.getBugsByTesterAndDevice(testerIdSet, matchTesterCommand.getDeviceId());
        List<Tester> matchedTesters = this.getTestersById(testerIdSet);

        List<TesterInfo> testerInfo = new ArrayList<TesterInfo>();
        Map<String, Map<String, List<String>>> bugsByTester = new HashMap<>();


        for (Tester tester : matchedTesters) {
            Map<String, List<String>> resultByDeviceId = new HashMap<>();
//list bugs for each tester
            List<Bug> result = bugs.stream()
                    .filter(line -> line.getTesterId().equals(tester.getTesterId()))
                    .collect(Collectors.toList());

// group bugs by device for each tester
            for (Bug bug : result) {
                List<String> bugIds = resultByDeviceId.get(bug.getDeviceId()) != null ? resultByDeviceId.get(bug.getDeviceId()) : new ArrayList<>();
                bugIds.add(bug.getBugId());
                resultByDeviceId.put(bug.getDeviceId(), bugIds);
            }

// count all bugs filled by tester for chosen devices
            int bugCount = 0;
            for (String key : resultByDeviceId.keySet()) {
                bugCount += resultByDeviceId.get(key).size();
            }
// create response object
            testerInfo.add(new TesterInfo(tester.getTesterId(), tester.getFirstName(), tester.getLastName(),
                    tester.getCountry(), tester.getLastLogin(), resultByDeviceId, bugCount));
        }

        return testerInfo;
    }


}
