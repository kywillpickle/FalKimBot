package commands.ray;

import net.dv8tion.jda.client.entities.Group;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.*;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.exceptions.PermissionException;
import net.dv8tion.jda.core.exceptions.RateLimitedException;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import javax.security.auth.login.LoginException;
import net.dv8tion.jda.core.entities.MessageChannel;
import javax.script.ScriptEngineManager;
import javax.script.ScriptEngine;
import javax.script.ScriptException;

//@Author Ray //
public class Master {
    private static boolean binderInitialized = false;

    public static void messageReceived(String message, MessageChannel channel, MessageReceivedEvent event) {
        try {
            // Calculator (NEEDS FIXING)
            String response = null; // Calculator.getResponse(message);
            // if (!(response == null || response.isEmpty())) {
            // channel.sendMessage(response).queue();
            // return;
            // }

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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected static String removeWhiteSpace(String str) {
        return str.replaceAll("\\s+", "");
    }

}
