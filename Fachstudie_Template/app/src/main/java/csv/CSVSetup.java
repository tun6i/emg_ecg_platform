package csv;

import android.os.Environment;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.Calendar;


/**
 * CSV Setup Class:
 * This class takes care of CSV files of the app and manages everything that has to do with it.
 */
public class CSVSetup{

    private static CSVSetup csvSetup = new CSVSetup();

    // The header of the CSV file.
    private String csvHeader = "Timestamp;ch1;ch2;ch3;ch4;ch5;ch6";

    // Filename.
    private String fileName;

    // Boolean so that the CSV file is not overwritten.
    private boolean csvExists = false;

    private CSVSetup() {
        // File name is created with time and name.
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int min = calendar.get(Calendar.MINUTE);
        int sec = calendar.get(Calendar.SECOND);

        this.fileName = "EMGData_" + day + "_" + month + "_" + year + "_" + hour + "_"
                + min + "_" + sec + ".csv";
    }

    /**
     * A new CSV file with a specific name is created.
     * @param participantName
     */
    public void createNewCSVFile(String participantName) {
        // File name is created with time and name.
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int min = calendar.get(Calendar.MINUTE);
        int sec = calendar.get(Calendar.SECOND);

        this.fileName = participantName + "_" + day + "_" + month + "_" + year + "_" + hour + "_"
                + min + "_" + sec + ".csv";

        writeFile(this.fileName);
    }

    /**
     * Instance is returned.
     * @return
     */
    public static CSVSetup getInstance() {
        return csvSetup;
    }

    /**
     * Filename is returned.
     * @return
     */
    public String getFileName() {
        return this.fileName;
    }

    /**
     * CSV-Header is returned.
     * @return
     */
    public String getCSVHeader() {
      return this.csvHeader;
    }

    /**
     * Value is set when a file exists.
     * @param bool
     */
    public void setCSVBoolean(boolean bool) {
        this.csvExists = bool;
    }

    /**
     * Exists a CSV file.
     * @return
     */
    public boolean getCSVBoolean() {
        return this.csvExists;
    }

    /**
     * A new line is added to the CSV file.
     * @param row
     */
    public void appendRowToCSV(String row) {
        // External Storage
        File extStore = Environment.getExternalStorageDirectory();
        // File-Path
        String path = extStore.getAbsolutePath() + "/" + this.fileName;

        try {
            // File with specific path
            File myFile = new File(path);

            FileOutputStream fOut = new FileOutputStream(myFile, true);
            OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);

            // A new line is added to the CSV file.
            myOutWriter.append(row);
            myOutWriter.append("\n\r");
            myOutWriter.close();
            fOut.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates a new file in external storage with a specific filename.
     * @param fileName
     */
    private void writeFile(String fileName) {
        // External Storage
        File extStore = Environment.getExternalStorageDirectory();
        // File-Path
        String path = extStore.getAbsolutePath() + "/" + fileName;

        try {
            // File with specific path
            File myFile = new File(path);
            boolean success = myFile.createNewFile();
            FileOutputStream fOut = new FileOutputStream(myFile);
            OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);

            // A new line is added to the CSV file (Header).
            myOutWriter.append(this.csvHeader);
            myOutWriter.append("\n\r");
            myOutWriter.close();
            fOut.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates a marker in the CSV file.
     * @param timestmp
     * @param ch1
     * @param ch2
     * @param ch3
     * @param ch4
     * @param ch5
     * @param ch6
     */
    public void createMarkInCSV(String timestmp, String ch1, String ch2, String ch3, String ch4,
                                String ch5, String ch6) {
        // Adds a new line to the CSV file.
        appendRowToCSV(timestmp + ";" + ch1 + ";" + ch2 + ";" + ch3 + ";" + ch4 + ";" + ch5
                + ";" + ch6 + ";");
    }

}
