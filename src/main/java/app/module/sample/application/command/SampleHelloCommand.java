package app.module.sample.application.command;

import jakarta.validation.constraints.Size;
import org.springframework.shell.standard.ShellCommandGroup;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
@ShellCommandGroup("Sample")
final public class SampleHelloCommand {
    @ShellMethod(value = "Hello message", key = "sample:hello")
    public String execute(
        @ShellOption(defaultValue = "World", help = "Your Name") @Size(min = 1, max = 255) String name
    ) {
        return "Hello " + name;
    }
}
