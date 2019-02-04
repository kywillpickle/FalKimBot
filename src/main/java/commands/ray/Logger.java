package commands.ray;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.ArrayList;
import java.io.LineNumberReader;
import java.io.FileReader;

public class Logger {
    private final String FILE_NAME, FILE_PATH;
    private File file;
    private BufferedWriter bw;
    private FileWriter fw;
    private BufferedReader br;
    private FileReader fr;
    private static String fileSeparator = System.getProperty("file.separator"); // Don't replace with '\'
    private LineNumberReader reader;
    private String absolutePath;

    // CONFIG
    private final boolean UNIQUE_ERRORS_ONLY;
    private final boolean RELEVANT_ERRORS_ONLY;
    private List<Exception> uniqueErrorLog = new ArrayList<Exception>();

    protected Logger(final String FILE_NAME, final String FILE_PATH, final boolean UNIQUE_ERRORS_ONLY,
            final boolean RELEVANT_ERRORS_ONLY) {
        // EXAMPLE: new Logger("log", "src" + fileSeparator + "util");

        this.FILE_NAME = FILE_NAME;
        this.FILE_PATH = FILE_PATH; // Relative
        this.UNIQUE_ERRORS_ONLY = UNIQUE_ERRORS_ONLY;
        this.RELEVANT_ERRORS_ONLY = RELEVANT_ERRORS_ONLY;

        try {
            file = new File(FILE_PATH + fileSeparator + FILE_NAME + ".txt");
            // this.absolutePath = (new File("").getAbsolutePath()).concat(FILE_PATH +
            // fileSeparator + FILE_NAME + ".txt");
            if (!file.exists()) {
                file.createNewFile();
            }
            reader = new LineNumberReader(new FileReader(file));
            fr = new FileReader(file);

            // System.out.println(file.getParentFile().mkdirs());

            bw = new BufferedWriter(fw = new FileWriter(file, true));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean put(String data) {
        try {
            bw.write(data);
            // System.out.println("Putting exception 5");
            bw.flush();
            return true;
        } catch (IOException e) {
            // System.out.println("error 5");
            e.printStackTrace();
            return false;
        }
    }

    private boolean put(String data, boolean newLine) {
        if (newLine) {
            // System.out.println("Putting exception 4");
            return put(data + System.lineSeparator());
        } else {
            return put(data);
        }
    }

    private String convertStackTraceToString(Exception e) { // Source:
                                                            // https://www.programiz.com/java-programming/examples/convert-stack-trace-string
        StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw));
        return sw.toString();
    }

    private boolean filterRelevantErrors(StackTraceElement[] stackTraceElements) {
        // for (StackTraceElement e : stackTraceElements) {

        // }

        return false;
    }

    private synchronized boolean filterUniqueErrors(Exception e) {
        for (Exception uniqueError : uniqueErrorLog) {
            if (convertStackTraceToString(e).equals(convertStackTraceToString(uniqueError))) {
                return false;
            }
        }

        uniqueErrorLog.add(e);
        return true;
    }

    private boolean put(Exception e) {
        String data = null;

        // Filters
        if (UNIQUE_ERRORS_ONLY) {
            if (!filterUniqueErrors(e))
                return false;
        }
        if (RELEVANT_ERRORS_ONLY) {
            if (!filterRelevantErrors(e.getStackTrace()))
                return false;
        }

        // System.out.println("Putting exception 2");
        // if (e.getMessage() == null) {
        return log(convertStackTraceToString(e));
        // }
        // return log(e.getMessage() + ":", convertStackTraceToString(e));
    }

    protected <T extends Exception> boolean log(T exception) {
        try {
            put(exception);
            // System.out.println("Putting exception 1");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            // put(convertStackTraceToString(e), true);
        }
        return false;
    }

    // Can not create array with generics
    protected boolean log(Object... array) {
        try {
            for (Object data : array) {
                put(data.toString(), true);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            // put(convertStackTraceToString(e), true);
        }
        return false;
    }

    protected boolean br() {
        return put("");
    }

    protected String getFileName() {
        return FILE_NAME;
    }

    protected String getFilePath() {
        return FILE_PATH;
    }

    protected File getFile() {
        return file;
    }

    protected int getLines() { // MODIFIED, SOURCE:
                               // https://stackoverflow.com/questions/453018/number-of-lines-in-a-file-in-java
        InputStream is = null;
        try {
            is = new BufferedInputStream(new FileInputStream(file));
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }
        try {
            byte[] c = new byte[1024];
            int count = 0;
            int readChars = 0;
            boolean empty = true;
            while ((readChars = is.read(c)) != -1) {
                empty = false;
                for (int i = 0; i < readChars; ++i) {
                    if (c[i] == '\n') {
                        ++count;
                    }
                }
            }
            return (count == 0 && !empty) ? 1 : count;
        } catch (Exception e) {
            e.printStackTrace();
            // put(convertStackTraceToString(e), true);
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return 0;
    }

    protected String readLine(int lineNum) {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            for (int i = 0; i < lineNum; i++)
                br.readLine();
            line = br.readLine();
            // System.out.println(line);
            return line;
        } catch (Exception e) {
            e.printStackTrace();
            // put(convertStackTraceToString(e), true);
        }
        return null;
    }

    protected String getContent() {
        try {
            String line = "", content = "";
            while ((line = reader.readLine()) != null) {
                content += line + "\r\n";
            }
            return content;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    protected void clearFile() {
        if (file.exists()) {
            file.delete();
            try {
                file.createNewFile();

                reader = new LineNumberReader(new FileReader(file));
                fr = new FileReader(file);

                bw = new BufferedWriter(fw = new FileWriter(file, true));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    protected void replaceLine(int i, String str) {
        try {
            // String line = "";
            // clearFile();
            // while ((line = reader.readLine()) != null) {
            // if (reader.getLineNumber() == i) {
            // line = str;
            // }
            // bw.write(line);; //MODIFIED, SOURCE:
            // https://java.happycodings.com/core-java/code69.html
            // }

            List<String> fileContent = new ArrayList<>(Files
                    .readAllLines(Paths.get(FILE_PATH + fileSeparator + FILE_NAME + ".txt"), StandardCharsets.UTF_8));

            fileContent.set(i, str);

            Files.write(Paths.get(file.getPath()), fileContent, StandardCharsets.UTF_8);
            // for (String content : fileContent) {
            // fw.write(content + "\r\n");
            // }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
