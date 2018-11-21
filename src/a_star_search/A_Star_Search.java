/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package a_star_search;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 *
 * @author Rayhan
 */
public class A_Star_Search {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException {
        // TODO code application logic here
        int [][] matrix;
        matrix=new int[6][6];
        int n;
        File file = new File("inputs.txt");
        Scanner in;

        in = new Scanner(file);
        n=in.nextInt();
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                matrix[i][j] = in.nextInt();
            }
        }
        for (int i = 2; i <= 2; i++) {
            Search_Runner sr = new Search_Runner(matrix, n);
            sr.heuristics_index=i;
            Node Result = sr.Search();
            if (Result == null) {
                System.out.println("There");
            } else {
                System.out.println("Result: " + Result.f_cost + " " + Result.g_cost + " " + Result.h_cost);
                sr.printpath(Result);

            }
        }
        

    }

}
