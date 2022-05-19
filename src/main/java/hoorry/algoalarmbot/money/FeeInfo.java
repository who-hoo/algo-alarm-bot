package hoorry.algoalarmbot.money;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FeeInfo {

	private final String name;
	private final String fee;

	public boolean isSameName(String name) {
		return this.name.equals(name);
	}

	public String translateToMessage() {
		if (name.equalsIgnoreCase("total")) {
			return "총액은 " + fee + "원입니다.";
		}
		return name + "의 벌금은 " + fee + "원입니다.";
	}
}
