package commands.ray;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.entities.MessageChannel;

public class Master {
    private static boolean binderInitialized = false;

    public static void messageReceived(MessageReceivedEvent event) {
        try {
            String response = null;
            final String message = event.getMessage().getContentRaw();
            final MessageChannel channel = event.getChannel();
            // Binder
            if (!message.startsWith("&"))
                return;
            if (!binderInitialized) {
                binderInitialized = true;
                if (!Binder.initialize())
                    channel.sendMessage("Binder initalization errored").queue();
            }
            response = Binder.getResponse(message);
            // System.out.println(response);
            if (!(response == null || response.isEmpty())) {
                channel.sendMessage(response).queue();
                return;
            }

            // EggPlant
            response = EggPlant.getResponse(event);
            if (!(response == null || response.isEmpty())) {
                channel.sendMessage(response).queue();
                return;
            }

            // Calculator (REMEMBER TO UPDATE NEW CODE FROM REPO https://github.com/RayBipse/calculator)
            response = CalculatorManager.getResponse(message);
            if (!(response == null || response.isEmpty())) {
                channel.sendMessage(response).queue();
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected static String removeWhiteSpace(String str) {
        return str.replaceAll("\\s+", "");
    }
}
