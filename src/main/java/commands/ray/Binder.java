package commands.ray;

import java.util.Dictionary;
import java.util.Hashtable;

public class Binder extends Master {

    private static String fileSeparator = System.getProperty("file.separator"); // Don't replace with '\'

    private static Dictionary<String, String> bindings = new Hashtable<String, String>();
    private static Logger bindLogger = new Logger("bindings", "src" + fileSeparator + "main" + fileSeparator + "java"
            + fileSeparator + "commands" + fileSeparator + "ray" + fileSeparator + "resources", false, false);
    private static String syntax = "&bind (index) (value)";

    public static String getResponse(String str) {
        try {
            if ((str.length() < 1) || !(str.startsWith("&")))
                return null;

            str = str.toLowerCase();
            // Binding
            if (str.startsWith("&bind")) {
                String returnMessage = bindString(str);
                if (returnMessage != null)
                    return returnMessage;
                else
                    return "Error: Syntax [" + syntax + "]";
            }

            // Find bind
            String returnMessage = findBind(str);
            if (returnMessage != null)
                return returnMessage;
            else
                return null;
        } catch (Exception e) {
            e.printStackTrace();
            return "Error";
        }
    }

    private static String findBind(String str) {
        String bind = findBinding(str.substring(1));
        return bind;
    }

    public static String bindString(String str) {
        try {
            str = str.substring(6);
        } catch (Exception e) {
            return null;
        }

        String[] splited = str.split("\\s+"); // SOURCE:
                                              // https://stackoverflow.com/questions/7899525/how-to-split-a-string-by-space
        // CHECK IF EXISTS AND REPLACE
        String messageFromReplaceBinding = replaceBinding(splited);
        if (messageFromReplaceBinding != null)
            return messageFromReplaceBinding;

        // ADD bind
        if (splited.length <= 1)
            return null;
        bindings.put(splited[0], splited[1]);
        bindLogger.log(splited[0], splited[1]);

        return "Binded " + splited[0] + " with " + splited[1];
    }

    public static String replaceBinding(String[] splited) {
        boolean found = false;
        for (int i = 0; i < bindLogger.getLines(); i++) {
            if (i % 2 == 1) { // odd num is value
                if (found) {
                    // System.out.println("found");
                    bindLogger.replaceLine(i, splited[1]);
                    restoreBindings();
                    return "Rebinded " + splited[0] + " with " + splited[1];
                }
            } else { // even is index
                // System.out.println(bindLogger.readLine(i) + " =? " + splited[0]);
                String line = bindLogger.readLine(i);
                if (line != null) {
                    if (line.equals(splited[0]))
                        found = true;
                }
            }
        }
        return null;
    }

    public static String findBinding(String str) {
        // System.out.println("Finding " + str + " in dictioanry");
        return bindings.get(str);
    }

    public static void restoreBindings() {
        String pastBind = null;
        for (int i = 0; i <= bindLogger.getLines(); i++) {
            String line = bindLogger.readLine(i);
            // System.out.println("Line: " + line);
            // System.out.println("Past bind: " + pastBind);
            if (i % 2 == 0) { // even num is value
                pastBind = line;
            } else { // odd is index
                bindings.put(pastBind, line);
            }
        }

        // System.out.println("bindings: " + bindings);
    }

    public static void initialize() {
        restoreBindings();
    }

}
