package hoorry.algoalarmbot.recommend;

import java.util.Objects;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Problem {

	private final String platform;
	private final String title;
	private final String URL;
	private final String level;

	public boolean isSameLevel(String level) {
		return this.level.equals(level);
	}

	@Override
	public String toString() {
		return	platform + " " + level + "  " + title + "  " + URL;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Problem problem = (Problem) o;
		return Objects.equals(platform, problem.platform) && Objects.equals(title,
			problem.title) && Objects.equals(URL, problem.URL) && Objects.equals(
			level, problem.level);
	}

	@Override
	public int hashCode() {
		return Objects.hash(platform, title, URL, level);
	}
}
