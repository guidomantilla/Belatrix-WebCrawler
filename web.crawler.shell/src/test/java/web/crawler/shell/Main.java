/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.crawler.shell;

import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author raven
 */
public class Main {

    public static void main(String[] args) throws IOException {

        try {

            ArrayList<Integer> lista = new ArrayList<>();
            lista.add(80);
            lista.add(50);
            lista.add(70);
            lista.add(95);

        } catch (Exception e) {
            System.err.println(e);
        }
    }
}
