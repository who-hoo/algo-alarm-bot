package hoorry.algoalarmbot;

import com.slack.api.app_backend.slash_commands.response.SlashCommandResponse;
import com.slack.api.bolt.App;
import com.slack.api.bolt.jetty.SlackAppServer;
import hoorry.algoalarmbot.money.MoneyService;
import hoorry.algoalarmbot.recommend.Problem;
import hoorry.algoalarmbot.recommend.RecommendService;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class AlgoAlarmBotApplication {

	private static final RecommendService recommendService = new RecommendService();

	public static void main(String[] args) throws Exception {
		App app = new App();
		MoneyService moneyService = new MoneyService();

		app.command("/recommend", (req, ctx) -> {
			StringBuilder sb = new StringBuilder();

			String arg = req.getPayload().getText();
			if (arg == null) {
				List<Problem> recommend = recommendService.recommend();
				for (Problem problem : recommend) {
					sb.append(problem.toString()).append('\n');
				}
				return ctx.ack(sb.toString());
			}

			List<Problem> recommend = recommendService.recommend(arg);

			for (Problem problem : recommend) {
				sb.append(problem.toString()).append('\n');
			}
			return ctx.ack(sb.toString());
		});

		app.command("/money", (req, ctx) -> {
			String target = req.getPayload().getText().toUpperCase();
			AtomicReference<String> message = new AtomicReference<>();
			moneyService.feeInfoOf(target).ifPresentOrElse(
				feeInfo -> message.set(feeInfo.translateToMessage()),
				() -> message.set("유효하지 않은 명령어입니다.")
			);
			return ctx.ack(SlashCommandResponse.builder()
				.text(message.toString())
				.responseType("in_channel")
				.build());
		});

		SlackAppServer server = new SlackAppServer(app);
		server.start();
	}

}
