package solid.ocp.abstract_class_with_template;

abstract class DataParser {
    // Template method defines the structure of the algorithm
    public final void parseDataAndGenerateOutput() {
        readData();
        processData();
        writeData();
    }

    abstract void readData();
    abstract void processData();
    abstract void writeData();
}

class CSVDataParser extends DataParser {
    void readData() {
        System.out.println("Reading data from a CSV file");
    }

    void processData() {
        System.out.println("Processing CSV data");
    }

    void writeData() {
        System.out.println("Writing CSV data to output");
    }
}

class JSONDataParser extends DataParser {
    void readData() {
        System.out.println("Reading data from a JSON file");
    }

    void processData() {
        System.out.println("Processing JSON data");
    }

    void writeData() {
        System.out.println("Writing JSON data to output");
    }
}

public class Main {
    public static void main(String[] args) {
        DataParser csvParser = new CSVDataParser();
        csvParser.parseDataAndGenerateOutput();

        DataParser jsonParser = new JSONDataParser();
        jsonParser.parseDataAndGenerateOutput();
    }
}
