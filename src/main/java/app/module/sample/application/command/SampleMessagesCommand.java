package app.module.sample.application.command;

import app.module.core.application.style.shell.DefaultShellMessageStyle;
import org.springframework.shell.standard.ShellCommandGroup;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
@ShellCommandGroup("Sample")
final public class SampleMessagesCommand {
    @ShellMethod(value = "Display messages e.g. warning, error, information", key = "sample:messages")
    public void execute() {
        DefaultShellMessageStyle messageStyle = new DefaultShellMessageStyle();
        messageStyle.success("Success");
        messageStyle.warning("Warning!");
        messageStyle.error("Error!");
        messageStyle.info("Info");
    }
}
