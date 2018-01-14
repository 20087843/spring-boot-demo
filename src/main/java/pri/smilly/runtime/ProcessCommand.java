package pri.smilly.runtime;

import lombok.Getter;
import lombok.Setter;
import pri.smilly.exception.EmptyParameterException;

import java.io.File;
import java.io.IOException;

@Setter
@Getter
public class ProcessCommand {
    private File baseDir;
    private String executor;
    private String[] args;

    private ProcessCommand(File baseDir, String executor, String[] args) {
        this.baseDir = baseDir;
        this.executor = executor;
        this.args = args;
    }

    public static ProcessCommand parser(File baseDir, String... args) throws Exception {
        if (baseDir != null && !baseDir.exists()) {
            throw new IOException(baseDir + " not exist");
        }
        if (args.length == 0) {
            throw new EmptyParameterException("args can not be empty");
        }
        String executor = args[0];
        String[] params = new String[args.length - 1];
        System.arraycopy(args, 1, params, 0, params.length);
        return new ProcessCommand(baseDir, executor, params);
    }

    public static ProcessCommand parser(File baseDir, String command) throws Exception {
        return parser(baseDir, command.split(" "));
    }

    public static ProcessCommand parser(String... args) throws Exception {
        return parser(null, args);
    }

    public static ProcessCommand parser(String command) throws Exception {
        return parser(null, command.split(" "));
    }

    @Override
    public String toString() {
        StringBuilder msg = new StringBuilder(executor);
        for (String arg : args) {
            msg.append(" ").append(arg);
        }
        return msg.toString();
    }
}
