package commands.ray;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.stream.Stream;

public class Binder extends Master {

    private static String fileSeparator = System.getProperty("file.separator"); // Don't replace with '\'

    private static Dictionary<String, String> bindings = new Hashtable<String, String>();
    private static Logger bindLogger = new Logger("bindings", "src" + fileSeparator + "main" + fileSeparator + "java"
            + fileSeparator + "commands" + fileSeparator + "ray" + fileSeparator + "resources", false, false);
    private static String bindSyntax = "&bind (index) (value)";
    private static String unbindSyntax = "&unbind (index)";
    private static String clearSyntax = "&bind_clear";

    private static String errorBindSyntax = "Error: bind syntax: ``[" + bindSyntax + "]``";
    private static String errorUnbindSyntax = "Error: unbind syntax: ``[" + unbindSyntax + "]``";
    private static String helpMessage = "Bind syntax: ``[" + bindSyntax + "]``\nUnbind syntax: ``[" + unbindSyntax
    + "]``\nClear syntax (recommended to only use when system error): ``[" + clearSyntax + "]``";

    private static boolean systemErrored = false;

    public static String getResponse(String str) {
        try {
            str = str.toLowerCase();
            // CHECK FOR CLEAR
            if (str.equals(clearSyntax)) {
                bindLogger.clearFile();
                systemErrored = false;
                initialize();
                return "CLEARED BINDINGS";
            } else if (str.equals("&bind") || str.equals("&bind help")) {
                return helpMessage;
            }

            // CHECK FOR VALIDITY
            if (systemErrored)
                return null;
            if ((str.length() < 1) || !(str.startsWith("&")))
                return null;

            // BINDINGS
            if (str.startsWith("&bind")) {
                String returnMessage = bindString(str);
                if (returnMessage != null)
                    return returnMessage;
                else
                    return errorBindSyntax;
            } else if (str.startsWith("&unbind")) { // UNBINDINGS
                String returnMessage = unbind(str);
                if (returnMessage != null)
                    return returnMessage;
                else
                    return errorUnbindSyntax;
            }

            // FIND BINDS
            String returnMessage = findBind(str);
            if (returnMessage != null)
                return returnMessage;
            else
                return null;
        } catch (Exception e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
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

        return "Binded ``" + splited[0] + "`` with ``" + splited[1] + "``";
    }

    public static String replaceBinding(String[] splited) {
        boolean found = false;
        for (int i = 0; i < bindLogger.getLines(); i++) {
            if (i % 2 == 1) { // odd num is value
                if (found) {
                    bindLogger.replaceLine(i, splited[1]);
                    restoreBindings();
                    return "Rebinded ``" + splited[0] + "`` with ``" + splited[1] + "``";
                }
            } else { // even is index
                String line = bindLogger.readLine(i);
                if (line != null) {
                    if (line.equals(splited[0]))
                        found = true;
                }
            }
        }
        return null;
    }

    public static String unbind(String str) {
        try {
            str = str.substring(8);

            // System.out.println(str);

            boolean found = false;
            for (int i = 0; i < bindLogger.getLines(); i++) {
                if (i % 2 == 0) { // even is index
                    // System.out.println(bindLogger.readLine(i) + " =? " + splited[0]);
                    String line = bindLogger.readLine(i);
                    if (line != null) {
                        if (line.equals(str)) {
                            bindLogger.deleteLine(i + 1);
                            bindLogger.deleteLine(i);
                            restoreBindings();
                            return "Unbinded ``" + str + "``";
                        }
                    }
                }
            }
            return "Bind not found";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }

    public static String findBinding(String str) {
        // System.out.println("Finding " + str + " in dictioanry");
        return bindings.get(str);
    }

    public static boolean restoreBindings() {
        try {
            String pastBind = null;
            bindings = new Hashtable<String, String>(); 
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
            return true;
        }
        // System.out.println("bindings: " + bindings);
        catch (Exception e) {
            e.printStackTrace();
            systemErrored = true;
            return false;
        }
    }

    public static boolean initialize() {
        return restoreBindings();
    }

}