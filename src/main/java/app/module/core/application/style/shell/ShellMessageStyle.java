package app.module.core.application.style.shell;

public interface ShellMessageStyle {
    void success(String message);
    void warning(String message);
    void error(String message);
    void info(String message);
}
