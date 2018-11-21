/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package a_star_search;

import java.util.Comparator;

/**
 *
 * @author Rayhan
 */
public class MyComparator implements Comparator<Node>{

    @Override
    public int compare(Node a,Node b) {
        
        return (a.f_cost-b.f_cost);
    }
    
}
