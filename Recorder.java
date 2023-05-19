import java.io.FileWriter;
import java.io.IOException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class Recorder {
    public String convertToCSV(String[] data) {
        return Stream.of(data)
                .collect(Collectors.joining(","));
    }

    public void outputDataToCsv(String filename, String[] data) throws IOException {
        FileWriter pw = new FileWriter(filename, true);
        pw.append(convertToCSV(data) + '\n');
        pw.flush();
        pw.close();
    }

    public void clearFileContent(String filename) {
        try {
            new FileWriter(filename, false).close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
