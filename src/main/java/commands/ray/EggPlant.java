package commands.ray;

import java.util.List;
import java.util.concurrent.ExecutionException;

import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageHistory;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.requests.RestAction;

public class EggPlant {
    private static MessageHistory annoucements = null;
    private static String targetedChannelID = "533797858629910558";
    private static String targetedChannelName = "announcements";

    protected static String getResponse(MessageReceivedEvent event) {
        try {
            String str = event.getMessage().getContentDisplay();

            // System.out.println(event.getChannel().getId());
            if (event.getChannel().getId() == targetedChannelID) {
                // System.out.println("yes");
                addEmote(event.getMessage());
            }

            if (!str.toLowerCase().startsWith("&egg"))
                return null;
            if (annoucements == null) {
                annoucements = new MessageHistory(event.getGuild().getTextChannelById(targetedChannelID));
            }

            RestAction<List<Message>> pastMessages;

            int number = Integer.parseInt(str.substring(10));

            if (number > 99)
                number = 99;
            else if (number < 1)
                return null;

            pastMessages = getPastMessages(number);

            loopMessages(pastMessages);

            return "Reacted eggplant emoji to the past " + number + " in " + targetedChannelName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static RestAction<List<Message>> getPastMessages(int n) {
        if (n > 99)
            n = 99;
        return annoucements.retrievePast(n);
    }

    private static void addEmote(Message message) {
        message.addReaction("\ud83c\udf46").queue();
    }

    private static void loopMessages(RestAction<List<Message>> messages) {
        try {
            for (Message message : messages.submit().get()) {
                // System.out.println(message.getContentDisplay());
                addEmote(message);
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

}