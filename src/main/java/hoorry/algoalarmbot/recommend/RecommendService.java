package hoorry.algoalarmbot.recommend;

import hoorry.algoalarmbot.common.MyFileReader;
import hoorry.algoalarmbot.common.TxtFileReader;
import java.io.File;
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
	private Problem bonusProblem = new Problem("백준", "A+B", "https://www.acmicpc.net/problem/1000", "Bronze5");

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
		TxtFileReader<Problem> reader = new TxtFileReader<>();
		loadBaekjun(reader);
		loadProgrammers(reader);
	}

	private void loadBaekjun(MyFileReader<Problem> fileReader) {
		List<Problem> problemsOfBaekjun = fileReader.read(baekjun, ",",
			args -> {
				if (args.length != 3) {
					return bonusProblem;
				}
				return new Problem("백준", args[0], args[1], args[2]);
			});
		this.problems.addAll(problemsOfBaekjun);
	}

	private void loadProgrammers(MyFileReader<Problem> fileReader) {
		List<Problem> problemsOfProgrammers = fileReader.read(programmers, ",",
			args -> {
				if (args.length != 3) {
					return bonusProblem;
				}
				return new Problem("프로그래머스", args[0], args[1], args[2]);
			});
		this.problems.addAll(problemsOfProgrammers);
	}

}
