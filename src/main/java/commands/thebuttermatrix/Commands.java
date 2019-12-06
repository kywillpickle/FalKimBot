package commands.thebuttermatrix;

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
import java.util.*;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.time.temporal.ChronoUnit;

public class Commands
{
    //custom commands
    public static void ping(String msg, MessageChannel channel)
    {
        if (msg.equals("&ping"))
        {
            //This will send a message, "pong!", by constructing a RestAction and "queueing" the action with the Requester.
            // By calling queue(), we send the Request to the Requester which will send it to discord.

            channel.sendMessage("pong!").queue();
        }
    }
    public static void countdown(String msg, MessageChannel channel)
    {
        if (msg.equals("&countdown"))
        {
            //Sends a message equal to the exact time FRC ends. Don't break down yet, it'll all be over soon-
            //This assumes that you're system's date/time are accurate

            //Create the current and FRC end time
            LocalDateTime date = LocalDateTime.now();
            System.out.println(date);

            //Create the hours, minutes, etc. by converting everything into seconds...
            int secRightNow = date.getDayOfYear()*86400 + date.getHour()*3600 + date.getMinute()*60 + date.getSecond();
            //February 19th, 2019 @ 8:59 and 59 seconds
            int secEndOfSeason = (4)*86400 + (0)*3600 + (0)*60 + (0);
            int difference = secEndOfSeason - secRightNow;
            if(difference<0)
            {
                difference = difference + 31536000;
            }
            //...and then back again
            int days = difference/86400;
            int hours = difference/3600 - days*24;
            int minutes = (difference/60)%60;
            int seconds = difference%60;
            
            //pRoPeR gRaMmAr
            int[] arr = {days, hours, minutes, seconds};
            String[] timeArr = {"day", "hour", "minute", "second"};
            for(int i = 0; i < timeArr.length; i++)
            {
                if(arr[i] != 1)
                {
                    timeArr[i] = timeArr[i] + "s";
                }
            }

            channel.sendMessage("T-minus "+days+" "+timeArr[0]+", "+hours+" "+timeArr[1]+", "+minutes+" "+timeArr[2]+", and "+seconds+" "+timeArr[3]+" until build season starts!").queue();
        }
    }
    public static void pid(String msg, MessageChannel channel) {
        if(msg.equals("&pid?")) {
            Random sequence = new Random();
            if(sequence.nextBoolean()) {
                channel.sendMessage("Yes.").queue();
            }
            else {
                channel.sendMessage("No.").queue();
            }
        }
    }
}