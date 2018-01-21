package pri.smilly.demo.runtime;

import pri.smilly.demo.util.StreamUtil;
import pri.smilly.demo.util.SystemUtil;
import pri.smilly.demo.util.ThreadUtil;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

public final class ProcessExecutor {
    private static final long defTimeout = 1000 * 60 * 5;
    private static final String exeSuffix = SystemUtil.isWindows() ? ".exe" : "";
    private static final String shellSuffix = SystemUtil.isWindows() ? ".bat" : ".sh";

    public static ProcessWrapper executeSync(ProcessCommand command) throws Exception {
        return execute(command, true, 0, -1);
    }

    public static ProcessWrapper executeSync(ProcessCommand command, int expectExitValue) throws Exception {
        return execute(command, true, expectExitValue, -1);
    }

    public static ProcessWrapper executeSync(ProcessCommand command, long timeout) throws Exception {
        return execute(command, true, 0, timeout);
    }

    public static ProcessWrapper executeSync(ProcessCommand command, int expectExitValue, long timeout) throws Exception {
        return execute(command, true, expectExitValue, timeout);
    }

    public static ProcessWrapper executeAsync(ProcessCommand command) throws Exception {
        return execute(command, false, 0, -1);
    }

    private static ProcessWrapper execute(ProcessCommand command, boolean isSync, int expectExitValue, long timeout) throws Exception {
        ProcessBuilder pb = new ProcessBuilder();

        File baseDir = command.getBaseDir();
        String executor = command.getExecutor();
        if (Objects.nonNull(baseDir) && !isExecutorExist(baseDir, executor)) {
            if (!baseDir.exists()) {
                throw new IOException(baseDir + " not exist");
            }

            String exe = getAndCheckExecutor(baseDir, executor, exeSuffix);
            if (exe == null) {
                exe = getAndCheckExecutor(baseDir, executor, shellSuffix);
            }
            if (exe == null) {
                throw new IOException(executor + " not found in " + baseDir);
            }
        }

        if (timeout <= 0) {
            timeout = defTimeout;
        }

        pb.directory(baseDir);
        pb.command(command.toString());
        pb.redirectErrorStream(true);

        Process process = pb.start();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ThreadUtil.invoke(() -> {
            try {
                StreamUtil.copy(process.getInputStream(), out);
            } catch (IOException e) {
                ;
            }
        });

        int exitValue = -99;
        if (isSync) {
            process.wait(timeout);
            exitValue = process.exitValue();
            return ProcessWrapper.builder()
                    .exitValue(exitValue)
                    .expectExitValue(expectExitValue)
                    .outputStream(out)
                    .process(process)
                    .build();
        } else {
            return ProcessWrapper.builder()
                    .exitValue(0)
                    .expectExitValue(expectExitValue)
                    .process(process)
                    .outputStream(out)
                    .build();
        }
    }

    private static String getAndCheckExecutor(File baseDir, String executor, String suffix) {
        executor = executor + suffix;
        if (isExecutorExist(baseDir, executor)) {
            return executor;
        }
        return null;
    }


    private static boolean isExecutorExist(File baseDir, String executor) {
        File exe = new File(baseDir, executor);
        return exe.exists();
    }

}
