package common.lab2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class Lab2 {
  public static void main(String[] args) throws IOException {
    Scanner console = new Scanner(System.in);
    System.out.println("Уведіть шлях до файлу з вхідними даними: ");
    Path inputPath = Paths.get(console.nextLine());
    BufferedReader input = new BufferedReader(new InputStreamReader(Files.newInputStream(inputPath)));
    BufferedWriter output = new BufferedWriter(new OutputStreamWriter(Files.newOutputStream(
        inputPath.getParent().resolve(Paths.get("OUTPUT.txt")))));
    int n = Integer.parseInt(input.readLine());
    byte[][] matrix = new byte[n][n];
    for (int i = 0; i < n; i++) {
      char[] currentLineAsCharArray = input.readLine().toCharArray();
      for (int j = 0; j < n; j++) {
        matrix[i][j] = Byte.parseByte(Character.toString(currentLineAsCharArray[j]));
      }
    }
    String result = process(matrix);
    output.write(result);
    output.flush();
    output.close();
    input.close();
  }

  public static String process(byte[][] matrix){
    int maxSize = 0;
    for (int i = 0; i < matrix.length; i++) {
      for (int j = 0; j < matrix.length; j++) {
        if (matrix[i][j] == 1){
          for (int currentMaxSize = matrix.length; currentMaxSize > 0 ; currentMaxSize--) {
            boolean isSquareFull = true;
            if (currentMaxSize + i > matrix.length || currentMaxSize + j > matrix.length){
              continue;
            }
            currentValidation:
            for (int k = 0; k < currentMaxSize; k++) {
              for (int l = 0; l < currentMaxSize; l++) {
                if (matrix[i + k][j + l] == 0) {
                  isSquareFull = false;
                  break currentValidation;
                }
              }
            }
            if (isSquareFull){
              if (currentMaxSize > maxSize){
                maxSize = currentMaxSize;
              }
              break;
            }
          }
        }
      }
    }
    return Integer.toString(maxSize);
  }
}
