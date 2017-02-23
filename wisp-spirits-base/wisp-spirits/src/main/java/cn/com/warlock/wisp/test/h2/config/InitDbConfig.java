package cn.com.warlock.wisp.test.h2.config;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import lombok.Data;

@Data
public class InitDbConfig {

    List<String> schemaFileList;

    List<String> dataFileList;

    String totalSchema = "";

    String totalData = "";

    public void setDataFileList(List<String> dataList) {
        this.dataFileList = dataList;

        for (String item : dataList) {
            if (item != null) {
                totalData += getFile(item);
            }
        }
    }

    public void setSchemaFileList(List<String> schemaFileList) {
        this.schemaFileList = schemaFileList;

        for (String item : schemaFileList) {
            if (item != null) {

                totalSchema += getFile(item);
            }
        }
    }

    private String getFile(String fileName) {

        StringBuilder result = new StringBuilder("");

        //Get file from resources folder
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(fileName).getFile());

        try (Scanner scanner = new Scanner(file)) {

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                result.append(line).append("\n");
            }

            scanner.close();

        } catch (IOException e) {

            System.err.println(e.toString());
        }

        return result.toString();

    }

}
