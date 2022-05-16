package hoorry.algoalarmbot;

import com.slack.api.bolt.App;
import com.slack.api.bolt.jetty.SlackAppServer;
import hoorry.algoalarmbot.money.MoneyService;
import java.util.concurrent.atomic.AtomicReference;

public class AlgoAlarmBotApplication {

    public static void main(String[] args) throws Exception {
        App app = new App();
        MoneyService moneyService = new MoneyService();

        // TEST COMMAND
        app.command("/hello", (req, ctx) -> {
            String arg = req.getPayload().getText();
            return ctx.ack(req.getPayload().getText());
        });

        app.command("/money", (req, ctx) -> {
            String target = req.getPayload().getText();
            AtomicReference<String> message = new AtomicReference<>();
            moneyService.feeInfoOf(target).ifPresentOrElse(
                fee -> message.set(target + "의 벌금은 현재 " + fee + "원입니다."),
                () -> message.set("유효하지 않은 명령어입니다.")
            );
            return ctx.ack(message.toString());
        });

        SlackAppServer server = new SlackAppServer(app);
        server.start();
    }

}
