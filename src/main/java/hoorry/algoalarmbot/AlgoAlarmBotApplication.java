package hoorry.algoalarmbot;

import com.slack.api.app_backend.slash_commands.response.SlashCommandResponse;
import com.slack.api.bolt.App;
import com.slack.api.bolt.jetty.SlackAppServer;
import hoorry.algoalarmbot.money.MoneyService;
import java.util.concurrent.atomic.AtomicReference;

public class AlgoAlarmBotApplication {

    public static void main(String[] args) throws Exception {
        App app = new App();
        MoneyService moneyService = new MoneyService();

        app.command("/recommend", (req, ctx) -> {
	        return ctx.ack();
        });

        app.command("/money", (req, ctx) -> {
            String target = req.getPayload().getText().toUpperCase();
            AtomicReference<String> message = new AtomicReference<>();
            moneyService.feeInfoOf(target).ifPresentOrElse(
                fee -> {
                    if (target.equals("TOTAL")) {
                        message.set("총액은 " + fee + "원입니다.");
                    } else {
                        message.set(target + "의 벌금은 " + fee + "원입니다.");
                    }
                },
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
