package atsys.csv;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CsvReader {

    public List<CsvRow> readAll(String fileName) throws IOException {
        List<CsvRow> rows = new ArrayList<>();

        try(InputStream is = this.getClass().getClassLoader().getResourceAsStream(fileName)){
            if(is == null){
                throw new IOException("Unable to read file");
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            while(true){
                String line = reader.readLine();
                if(line == null){
                    break;
                }
                String[] fields = line.split(",");
                rows.add(new CsvRow(fields));
            }
        }
        return rows;
    }
}
