package csv;

import android.os.Environment;
import android.util.Log;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.Calendar;


/**
 * CSV Setup Class
 */

public class CSVSetup{

    private static CSVSetup csvSetup = new CSVSetup();
    private String csvHeader = "Timestamp;ch1;ch2;ch3;ch4;ch5;ch6";
    private String fileName;
    // Damit die CSV Datei nicht überschrieben wird.
    private boolean csvExists = false;

    private CSVSetup() {

        // Dateiname wird generiert.
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

    public void createNewCSVFile(String participantName) {
        // Dateiname wird generiert.
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

    public static CSVSetup getInstance() {
        return csvSetup;
    }

    public String getFileName() {
        return this.fileName;
    }

    public String getCSVHeader() {
      return this.csvHeader;
    }

    public void setCSVBoolean(boolean bool) {
        this.csvExists = bool;
    }

    public boolean getCSVBoolean() {
        return this.csvExists;
    }

    public void appendRowToCSV(String row) {
        File extStore = Environment.getExternalStorageDirectory();
        // ==> /storage/emulated/0/note.txt
        String path = extStore.getAbsolutePath() + "/" + this.fileName;
        Log.w("CSV", "Save to: " + path);

        try {
            File myFile = new File(path);
            //myFile.createNewFile();
            FileOutputStream fOut = new FileOutputStream(myFile, true);
            OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
            myOutWriter.append(row);
            myOutWriter.append("\n\r");
            myOutWriter.close();
            fOut.close();

            //Toast.makeText(this.activity, fileName + " saved", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void writeFile(String fileName) {

        File extStore = Environment.getExternalStorageDirectory();
        // ==> /storage/emulated/0/note.txt
        String path = extStore.getAbsolutePath() + "/" + fileName;
        Log.i("CSV", "Save to: " + path);

        try {
            File myFile = new File(path);
            boolean success = myFile.createNewFile();
            FileOutputStream fOut = new FileOutputStream(myFile);
            OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
            myOutWriter.append(this.csvHeader);
            myOutWriter.append("\n\r");
            myOutWriter.close();
            fOut.close();

            //Toast.makeText(getActivity(), "CSV-Datei " + csvFile.getFileName() + " wurde erstellt", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createMarkInCSV(String timestmp, String ch1, String ch2, String ch3, String ch4,
                                String ch5, String ch6) {
        appendRowToCSV(timestmp + ";" + ch1 + ";" + ch2 + ";" + ch3 + ";" + ch4 + ";" + ch5
                + ";" + ch6 + ";");
    }

}
