package app.module.sample.application.command;

import app.module.core.application.style.shell.DefaultShellMessageStyle;
import app.module.core.domain.exception.FailedQueryException;
import app.module.sample.domain.dto.SampleDTO;
import app.module.sample.infrastructure.query.GetSampleDataFromJsonQuery;
import de.vandermeer.asciitable.AsciiTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellCommandGroup;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@ShellComponent
@ShellCommandGroup("Sample")
@Service
final public class SampleTableCommand {
    private final GetSampleDataFromJsonQuery getSampleDataFromJsonQuery;

    @Autowired
    public SampleTableCommand(GetSampleDataFromJsonQuery getSampleDataFromJsonQuery) {
        this.getSampleDataFromJsonQuery = getSampleDataFromJsonQuery;
    }

    @ShellMethod(key = "sample:table", value = "Sample command with Table")
    public void execute() {
        try {
            ArrayList<SampleDTO> sampleData = getSampleDataFromJsonQuery.getData();
            AsciiTable table = new AsciiTable();

            table.addRule();
            table.addRow("First Name", "Last Name", "Age");
            table.addRule();

            for (SampleDTO item : sampleData) {
                table.addRow(item.fistName, item.lastName, item.age);
                table.addRule();
            }

            System.out.println(table.render());
        } catch (FailedQueryException exception) {
            catchCannotLoadSampleData();
        }
    }

    private void catchCannotLoadSampleData() {
        DefaultShellMessageStyle messageStyle = new DefaultShellMessageStyle();
        messageStyle.error("Cannot load sample data!");
        System.exit(1);
    }
}
