/*
 * This class is used to write the data to a CSV file.
 * The main function is to take in list of string and convert it to CSV format.
 * Then append it to a file on a new line.
 */

import java.io.FileWriter;
import java.io.IOException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class Recorder {

    /**
     * @param data
     * @return String
     *         Convert list of string to CSV format.
     */
    public String convertToCSV(String[] data) {
        return Stream.of(data)
                .collect(Collectors.joining(","));
    }

    /**
     * @param filename
     * @param data
     * @throws IOException
     *                     write a list of string to a CSV file in CSV format.
     */
    public void outputDataToCsv(String filename, String[] data) throws IOException {
        FileWriter pw = new FileWriter(filename, true);
        pw.append(convertToCSV(data) + '\n');
        pw.flush();
        pw.close();
    }

    /**
     * @param filename
     *                 Delete previous content of a file.
     */
    public void clearFileContent(String filename) {
        try {
            new FileWriter(filename, false).close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
