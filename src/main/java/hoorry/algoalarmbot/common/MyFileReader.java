package hoorry.algoalarmbot.common;

import java.io.File;
import java.util.List;

public interface MyFileReader<T> {

	List<T> read(File file, String delimiter, Generator<T> generator);
}
