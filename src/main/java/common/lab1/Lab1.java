package common.lab1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Lab1 {
  public static void main(String[] args) throws IOException {
    Scanner console = new Scanner(System.in);
    System.out.println("Уведіть шлях до файлу з вхідними даними: ");
    Path inputPath = Paths.get(console.nextLine());
    BufferedReader input = new BufferedReader(new InputStreamReader(Files.newInputStream(inputPath)));
    BufferedWriter output = new BufferedWriter(new OutputStreamWriter(Files.newOutputStream(
        inputPath.getParent().resolve(Paths.get("OUTPUT.txt")))));
    String[] inputs = input.readLine().split(" ");
    int n = Integer.parseInt(inputs[0]);
    int ab = Integer.parseInt(inputs[1]);
    int bc = Integer.parseInt(inputs[2]);
    int ac = Integer.parseInt(inputs[3]);
    output.write(process(n, ab, bc, ac));
    output.flush();
    output.close();
    input.close();
  }

  public static String process(int n, int ab, int bc, int ac){
    int chairsCount = n;
    Map<String, Integer> needForSide = new HashMap<>();
    needForSide.put("AB", ab);
    needForSide.put("BC", bc);
    needForSide.put("AC", ac);

    List<Integer> sortedForSide = new ArrayList<>(needForSide.values());
    Collections.sort(sortedForSide);

    loop:
    for (int forSide : sortedForSide) {
      for (int i = 0; i < forSide; i++) {
        if (needForSide.get("AB") >= forSide && Geom.AB.getCount() < forSide) {
          Geom.AB.increment();
          chairsCount--;
          if (chairsCount == 0) {
            break loop;
          }
        }
        if (needForSide.get("BC") >= forSide && Geom.BC.getCount() < forSide) {
          Geom.BC.increment();
          chairsCount--;
          if (chairsCount == 0) {
            break loop;
          }
        }
        if (needForSide.get("AC") >= forSide && Geom.AC.getCount() < forSide) {
          Geom.AC.increment();
          chairsCount--;
          if (chairsCount == 0) {
            break loop;
          }
        }
      }
    }
    if (chairsCount != 0) {
      return "NO";
    }
    while (needForSide.get("AB") != (Geom.AB.getCount() + Geom.A.getCount() + Geom.B.getCount()) ||
        needForSide.get("BC") != (Geom.BC.getCount() + Geom.B.getCount() + Geom.C.getCount()) ||
        needForSide.get("AC") != (Geom.AC.getCount() + Geom.A.getCount() + Geom.C.getCount())) {
      if (needForSide.get("AB") > (Geom.AB.getCount() + Geom.A.getCount() + Geom.B.getCount())) {
        if (Geom.BC.getCount() > 0) {
          Geom.B.increment();
          Geom.BC.decrement();
        } else if (Geom.AC.getCount() > 0) {
          Geom.A.increment();
          Geom.AC.decrement();
        } else {
          return "NO";
        }
      }
      if (needForSide.get("BC") > (Geom.BC.getCount() + Geom.B.getCount() + Geom.C.getCount())) {
        if (Geom.AC.getCount() > 0) {
          Geom.C.increment();
          Geom.AC.decrement();
        } else if (Geom.AB.getCount() > 0) {
          Geom.B.increment();
          Geom.AB.decrement();
        } else {
          return "NO";
        }
      }
      if (needForSide.get("AC") > (Geom.AC.getCount() + Geom.A.getCount() + Geom.C.getCount())) {
        if (Geom.AB.getCount() > 0) {
          Geom.A.increment();
          Geom.AB.decrement();
        } else if (Geom.BC.getCount() > 0) {
          Geom.C.increment();
          Geom.BC.decrement();
        } else {
          return "NO";
        }
      }
    }
    return "YES\n" +
    String.format("%s %s %s %s %s %s", Geom.A.getCount(),
        Geom.AB.getCount(), Geom.B.getCount(), Geom.BC.getCount(),
        Geom.C.getCount(), Geom.AC.getCount());
  }
}
