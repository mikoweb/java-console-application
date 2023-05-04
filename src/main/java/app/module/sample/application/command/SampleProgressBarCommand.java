package app.module.sample.application.command;

import app.module.core.application.style.shell.DefaultShellMessageStyle;
import app.module.core.application.style.shell.ShellMessageStyle;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import me.tongfei.progressbar.ProgressBar;
import org.springframework.shell.standard.ShellCommandGroup;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
@ShellCommandGroup("Sample")
final public class SampleProgressBarCommand {
    private static final int DEFAULT_MAX_PROGRESS = 100;
    private static final short DEFAULT_SLEEP_VALUE = 50;
    private final ShellMessageStyle messageStyle;

    public SampleProgressBarCommand() {
        this.messageStyle = new DefaultShellMessageStyle();
    }

    @ShellMethod(key = "sample:progress-bar", value = "Sample command with Progress Bar")
    public void execute(
        @Min(1) @Max(1000 * 1000 * 1000) int maxProgress,
        @Min(1) @Max(10000) short sleep
    ) {
        try (ProgressBar progressBar = new ProgressBar("Sample Progress Bar", maxProgress)) {
            int currentProgress = 0;
            while (currentProgress < maxProgress) {
                sleepCommand(sleep);
                progressBar.step();
                currentProgress++;
            }
        } catch (Exception exception) {
            catchProgressBar();
        }
    }

    @ShellMethod(key = "sample:progress-bar-default", value = "Sample command with Progress Bar - Default arguments")
    public void execute() {
        execute(DEFAULT_MAX_PROGRESS, DEFAULT_SLEEP_VALUE);
    }

    private void sleepCommand(int sleep) {
        try {
            Thread.sleep(sleep);
        } catch (InterruptedException exception) {
            catchSleepInterrupted();
        }
    }

    private void catchProgressBar() {
        messageStyle.error("ProgressBar Crash!");
        System.exit(1);
    }

    private void catchSleepInterrupted() {
        messageStyle.error("Sleep Interrupted!");
        System.exit(1);
    }
}
