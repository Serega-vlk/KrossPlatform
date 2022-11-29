package common.lab3;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Lab3 {
  public static void main(String[] args) throws IOException {
    Scanner console = new Scanner(System.in);
    System.out.println("Варіант 10");
    System.out.println("Уведіть шлях до файлу з вхідними даними: ");
    Path inputPath = Paths.get(console.nextLine());
    BufferedReader input = new BufferedReader(new InputStreamReader(Files.newInputStream(inputPath)));
    BufferedWriter output = new BufferedWriter(new OutputStreamWriter(Files.newOutputStream(
        inputPath.getParent().resolve(Paths.get("OUTPUT.txt")))));
    String str = input.readLine();
    int OVP = Integer.parseInt(str.split(" ")[0]);
    int pairsC = Integer.parseInt(str.split(" ")[1]);
    List<Pair> list = new ArrayList<>();
    for (int i = 0; i < pairsC; i++) {
      String pair = input.readLine();
      list.add(new Pair(Integer.parseInt(pair.split(" ")[0]), Integer.parseInt(pair.split(" ")[1])));
    }
    output.write(process(OVP, list));
    output.flush();
    output.close();
    input.close();
  }

  private static String process(int ovpC, List<Pair> pairs){
    List<Integer> table1 = new ArrayList<>();
    List<Integer> table2 = new ArrayList<>();
    for (int i = 1; i <= ovpC; i++) {
      if (!containsAtLeastOne(table1, getAgainstOvps(i, pairs))){
        table1.add(i);
      } else if (!containsAtLeastOne(table2, getAgainstOvps(i, pairs))) {
        table2.add(i);
      } else return "NO";
    }
    return "YES";
  }

  private static boolean containsAtLeastOne(List<Integer> l1, List<Integer> l2){
    for (int a : l1){
      for (int b : l2){
        if (a == b){
          return true;
        }
      }
    }
    return false;
  }

  private static List<Integer> getAgainstOvps(int ovp, List<Pair> pairs){
    List<Integer> result = new ArrayList<>();
    pairs.forEach(pair -> {
      if (pair.getA() == ovp){
        result.add(pair.getB());
      } else if (pair.getB() == ovp) {
        result.add(pair.getA());
      }
    });
    return result;
  }

  public static class Pair{
    private final int a;
    private final int b;

    public Pair(int a, int b){
      this.a = a;
      this.b = b;
    }

    public int getA() {
      return a;
    }

    public int getB() {
      return b;
    }
  }
}
