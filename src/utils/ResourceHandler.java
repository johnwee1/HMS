package utils;

import java.io.IOException;
import java.util.List;

public interface ResourceHandler {
    /**
     * Reads the headers from a resource.
     *
     * @param filepath path to the resource
     * @return list of header strings
     * @throws IOException if there's an error reading the resource
     */
    public List<String> readHeaders(String filepath) throws IOException;

    /**
     * Reads all data rows from a resource.
     *
     * @param filepath path to the resource
     * @return all rows (data) as a list, where each row is a string list
     * @throws IOException if there's an error reading the resource
     */
    public List<List<String>> readRows(String filepath) throws IOException;

    /**
     * Writes headers and data rows to a resource.
     *
     * @param filepath path to the resource
     * @param headers list of header strings
     * @param rows list of data rows, where each row is a List of strings
     * @throws IOException if there's an error writing to the resource
     */
    public void writeToFile(String filepath, List<String> headers, List<List<String>> rows) throws IOException;
}