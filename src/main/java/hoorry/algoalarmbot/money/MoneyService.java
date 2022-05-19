package hoorry.algoalarmbot.money;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MoneyService {

	public Optional<FeeInfo> feeInfoOf(String target) {
		File file = new File(System.getenv("FEE_INFO_PATH"));
		List<FeeInfo> feeInfos = new ArrayList<>();

		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			String line;
			while ((line = br.readLine()) != null) {
				String[] split = line.split(":");
				feeInfos.add(new FeeInfo(split[0].trim(), split[1].trim()));
			}
		} catch (IOException e) {
			log.error(e.getMessage());
		}

		return feeInfos.stream()
			.filter(feeInfo -> feeInfo.isSameName(target))
			.findAny();
	}
}
