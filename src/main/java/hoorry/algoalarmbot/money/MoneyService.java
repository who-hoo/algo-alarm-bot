package hoorry.algoalarmbot.money;

import hoorry.algoalarmbot.common.MyFileReader;
import java.io.File;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MoneyService {

	public Optional<FeeInfo> feeInfoOf(String target, MyFileReader<FeeInfo> fileReader) {
		File file = new File(System.getenv("FEE_INFO_PATH"));

		List<FeeInfo> feeInfos = fileReader.read(file, ":",
			args -> new FeeInfo(args[0].trim(), args[1].trim()));

		return feeInfos.stream()
			.filter(feeInfo -> feeInfo.isSameName(target))
			.findAny();
	}
}
