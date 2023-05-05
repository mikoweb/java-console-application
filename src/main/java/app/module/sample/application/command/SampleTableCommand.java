package app.module.sample.application.command;

import app.module.core.application.style.shell.DefaultShellMessageStyle;
import app.module.core.domain.exception.FailedQueryException;
import app.module.sample.domain.dto.SampleDTO;
import app.module.sample.infrastructure.query.GetSampleDataFromJsonQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellCommandGroup;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.table.BorderStyle;
import org.springframework.shell.table.TableBuilder;
import org.springframework.shell.table.TableModelBuilder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@ShellComponent
@ShellCommandGroup("Sample")
@Service
final public class SampleTableCommand {
    private static final short TABLE_MAX_WIDTH = 500;
    private final GetSampleDataFromJsonQuery getSampleDataFromJsonQuery;

    @Autowired
    public SampleTableCommand(GetSampleDataFromJsonQuery getSampleDataFromJsonQuery) {
        this.getSampleDataFromJsonQuery = getSampleDataFromJsonQuery;
    }

    @ShellMethod(key = "sample:table", value = "Sample command with Table")
    public void execute() {
        try {
            ArrayList<SampleDTO> sampleData = getSampleDataFromJsonQuery.getData();

            TableModelBuilder<Object> modelBuilder = new TableModelBuilder<>();
            modelBuilder.addRow().addValue("First Name").addValue("Last Name").addValue("Age");

            for (SampleDTO item : sampleData) {
                modelBuilder.addRow().addValue(item.fistName).addValue(item.lastName).addValue(item.age);
            }

            TableBuilder tableBuilder = new TableBuilder(modelBuilder.build());
            tableBuilder.addFullBorder(BorderStyle.oldschool);

            System.out.println(tableBuilder.build().render(TABLE_MAX_WIDTH));
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
