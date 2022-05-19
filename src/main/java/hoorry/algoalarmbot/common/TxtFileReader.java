package hoorry.algoalarmbot.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TxtFileReader<T> implements MyFileReader<T> {

	@Override
	public List<T> read(File file, String delimiter, Generator<T> generator) {
		List<T> store = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			String line;
			while ((line = br.readLine()) != null) {
				String[] split = line.split(delimiter);
				store.add(generator.generate(split));
			}
		} catch (IOException e) {
			log.error(e.getMessage());
		}
		return store;
	}
}
