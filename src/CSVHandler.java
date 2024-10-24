import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class CSVHandler {
    private static final char DELIMITER = ',';
    private static final char QUOTE_CHAR = '"'; // reserved
    private static final char UNPROCESSED_QUOTE_CHAR =  '\''; // swap " to ' if encountered

    public static List<String> readHeaders(String filepath) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filepath))) {
            String line = reader.readLine();
            if (line != null) {
                return parseLine(line);
            }
        }
        throw new IOException("Error reading CSV! No headers?");
    }

    public static List<List<String>> readRows(String filePath) throws IOException {
        List<List<String>> rows = new ArrayList<>(); // upcast
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            // Skip the header
            reader.readLine();

            while (true) {
                line = reader.readLine();
                if (line != null){
                    rows.add(parseLine(line));
                }
                else break;
            }

        }
        return rows;
    }

    private static List<String> parseLine(String line) {
        List<String> row = new ArrayList<>();
        StringBuilder entry = new StringBuilder();
        boolean isInQuotations = false; // default

        for (char c: line.toCharArray()){
            if (c==QUOTE_CHAR) {
                isInQuotations = !isInQuotations;
            } else if (c==DELIMITER && !isInQuotations){
                row.add(entry.toString().trim());
                entry = new StringBuilder();
            } else entry.append(c);
        }
        row.add(entry.toString().trim());
        return row;
    }

    public static void writeCSV(String filePath, List<String> headers, List<List<String>> rows) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writeLine(writer, headers);
            for (List<String> row : rows) {
                writeLine(writer, row);
            }
        }
    }

    private static void writeLine(BufferedWriter writer, List<String> fields) throws IOException {
        boolean first = true; // avoid having to delete the last comma
        for (String field : fields) {
            if (!first) {
                writer.write(DELIMITER);
            }
            if (field.contains(String.valueOf(DELIMITER)) || field.contains(String.valueOf(QUOTE_CHAR))) {
                writer.write(QUOTE_CHAR);
                writer.write(field.replace(String.valueOf(QUOTE_CHAR), String.valueOf(UNPROCESSED_QUOTE_CHAR)));
                writer.write(QUOTE_CHAR);
            } else {
                writer.write(field);
            }
            first = false;
        }
        writer.newLine();
    }

    public static void main(String[] args){
        final String filename = "appointments.csv";
        final String filepath = java.nio.file.Paths.get(System.getProperty("user.dir"),"test","resources", filename).toString();

        try {
            System.out.println(readHeaders(filepath));
        } catch (IOException e){
            throw new RuntimeException("Error: "+e);
        }
        List<List<String>> rows = new ArrayList<>();
        try {
            rows = readRows(filepath);
            System.out.println(readRows(filepath));
        } catch (IOException e){
            throw new RuntimeException("Error: "+e);
        }
        final String outpath = java.nio.file.Paths.get(System.getProperty("user.dir"),"test","resources", "out.csv").toString();
        try{
            writeCSV(outpath,readHeaders(filepath),rows);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}

