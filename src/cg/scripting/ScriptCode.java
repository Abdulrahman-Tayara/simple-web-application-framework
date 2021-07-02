package cg.scripting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ScriptCode {
    List<String> lines;

    public ScriptCode(List<String> lines) {
        this.lines = lines;
    }

    public ScriptCode() {
        this.lines = new ArrayList<>();
    }

    public void addLine(String... lines) {
        this.lines.addAll(Arrays.asList(lines));
    }

    public List<String> getLines() {
        return lines;
    }
}
