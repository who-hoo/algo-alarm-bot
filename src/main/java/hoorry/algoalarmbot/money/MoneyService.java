package hoorry.algoalarmbot.money;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MoneyService {

    public Optional<String> feeInfoOf(String target) {
        File file = new File(System.getenv("FEE_INFO_PATH"));
        Map<String, String> feeInfo = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] split = line.split(":");
                feeInfo.put(split[0].trim(), split[1].trim());
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        }

        return Optional.ofNullable(feeInfo.get(target));
    }
}
