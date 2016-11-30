package csv;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Comment
 */

public class CSVSetup {

    private boolean csvActive;
    private String baseDir;
    private String fileName;
    private String filePath;
    private File file;

    private static CSVSetup csvSetup = new CSVSetup();

    public CSVSetup() {
        this.baseDir = android.os.Environment.getExternalStorageDirectory().getAbsolutePath();

        // Dateiname wird generiert
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int min = calendar.get(Calendar.MINUTE);

        this.fileName = "EmgData" + day + "_" + month + "_" + year + "_" + hour + "_" + min + ".csv";

        this.filePath = baseDir + File.separator + fileName;
        this.file = new File(filePath );
    }

    public static CSVSetup getInstance() {
        return csvSetup;
    }
}
