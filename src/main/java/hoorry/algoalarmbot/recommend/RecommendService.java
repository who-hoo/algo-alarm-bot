package hoorry.algoalarmbot.recommend;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RecommendService {

	private File baekjun = new File(System.getenv("BAEKJUN_PATH"));
	private File programmers = new File(System.getenv("PROGRAMMERS_PATH"));

	private final List<Problem> problems = new ArrayList<>();

	public RecommendService() {
		loadProblems();
	}

	public List<Problem> recommend() {

		Set<Integer> randomProblemIndex = new HashSet<>();
		while (randomProblemIndex.size() != 5) {
			int random = (int) (Math.random() * problems.size());
			randomProblemIndex.add(random);
		}

		List<Problem> recommendedProblems = new ArrayList<>();

		for (Integer problemIndex : randomProblemIndex) {
			recommendedProblems.add(problems.get(problemIndex));
		}
		return recommendedProblems;
	}

	public List<Problem> recommend(String level) {

		List<Problem> filteredProblems = problems.stream()
			.filter(p -> p.isSameLevel(level))
			.collect(Collectors.toList());

		Set<Integer> randomProblemIndex = new HashSet<>();
		while (randomProblemIndex.size() != 5) {
			int random = (int) (Math.random() * filteredProblems.size());
			randomProblemIndex.add(random);
		}

		List<Problem> recommendedProblems = new ArrayList<>();

		for (Integer problemIndex : randomProblemIndex) {
			recommendedProblems.add(filteredProblems.get(problemIndex));
		}
		return recommendedProblems;
	}

	private void loadProblems() {
		loadBaekjun();
		loadProgrammers();
	}

	private void loadBaekjun() {
		try (BufferedReader br = new BufferedReader(new FileReader(baekjun))) {
			String line;
			while ((line = br.readLine()) != null) {
				String[] split = line.split(",");
				problems.add(new Problem("백준", split[0], split[1], split[2]));
			}
		} catch (IOException e) {
			log.error(e.getMessage());
		}
	}

	private void loadProgrammers() {
		try (BufferedReader br = new BufferedReader(new FileReader(programmers))) {
			String line;
			while ((line = br.readLine()) != null) {
				String[] split = line.split(",");
				problems.add(new Problem("프로그래머스", split[0], split[1], split[2]));
			}
		} catch (IOException e) {
			log.error(e.getMessage());
		}
	}

}
