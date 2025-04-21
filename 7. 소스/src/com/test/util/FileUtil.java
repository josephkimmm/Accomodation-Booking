package com.test.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {
	
	 // 파일에서 데이터를 읽어오는 메서드
    public static List<String> readFromFile(String filePath) {
    	
        List<String> lines = new ArrayList<>();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            System.err.println("파일 읽기 중 오류 발생: " + e.getMessage());
        }
        
        return lines;
    }

    // 데이터를 파일에 저장하는 메서드
    public static void writeToFile(String filePath, List<String> lines) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("파일 쓰기 중 오류 발생: " + e.getMessage());
        }
    }

    // 특정 파일에서 데이터를 구분자로 분리해 List<List<String>>로 반환하는 메서드
    public static List<List<String>> readAndSplitFile(String filePath, String delimiter) {
    	
        List<List<String>> parsedData = new ArrayList<>();
        List<String> lines = readFromFile(filePath);
        
        for (String line : lines) {
            String[] parts = line.split(delimiter);
            List<String> parsedLine = new ArrayList<>();
            for (String part : parts) {
                parsedLine.add(part.trim()); // 트림으로 공백 제거
            }
            parsedData.add(parsedLine);
        }
        
        return parsedData;
    }

    // 데이터를 구분자로 조합해 파일에 저장하는 메서드
    public static void writeDataWithDelimiter(String filePath, List<List<String>> data, String delimiter) {
    	
        List<String> lines = new ArrayList<>();
        
        for (List<String> entry : data) {
            lines.add(String.join(delimiter, entry));
        }
        writeToFile(filePath, lines);
    }
}
