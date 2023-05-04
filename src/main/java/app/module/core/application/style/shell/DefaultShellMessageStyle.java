package app.module.core.application.style.shell;

import com.diogonunes.jcolor.AnsiFormat;
import com.diogonunes.jcolor.Attribute;

final public class DefaultShellMessageStyle implements ShellMessageStyle {
    private AnsiFormat successMessage;
    private AnsiFormat warningMessage;
    private AnsiFormat errorMessage;
    private AnsiFormat infoMessage;

    public DefaultShellMessageStyle() {
        initMessages();
    }

    public void success(String message) {
        println(successMessage.format("[OK] " + message));
    }

    public void warning(String message) {
        println(warningMessage.format("[WARNING] " + message));
    }

    public void error(String message) {
        println(errorMessage.format("[ERROR] " + message));
    }

    public void info(String message) {
        println(infoMessage.format("[INFO] " + message));
    }

    private void initMessages() {
        successMessage = new AnsiFormat(Attribute.BLACK_TEXT(), Attribute.GREEN_BACK());
        warningMessage = new AnsiFormat(Attribute.BLACK_TEXT(), Attribute.YELLOW_BACK());
        errorMessage = new AnsiFormat(Attribute.BRIGHT_WHITE_TEXT(), Attribute.RED_BACK());
        infoMessage = new AnsiFormat(Attribute.BLACK_TEXT(), Attribute.BLUE_BACK());
    }

    private void println(String message) {
        System.out.println(message);
    }
}
