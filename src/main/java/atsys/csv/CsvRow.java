package atsys.csv;

public class CsvRow {
    private final String[] tokens;

    CsvRow(String[] tokens){
        this.tokens = tokens;
    }

    public int getLength(){
        return tokens.length;
    }

    public String getToken(int index) {
        return tokens[index];
    }
}
