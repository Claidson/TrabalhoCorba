/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calcApp;

/**
 *
 * @author Claidson
 */
public class CalcPoaImple extends calcPOA {

    @Override
    public float soma(float n1, float n2) {
        System.out.println("Somando: " + n1 + "+ " + n2);
        return n1 + n2;
    }

    @Override
    public float sub(float n1, float n2) {
        System.out.println("Subtraindo: " + n1 + "- " + n2);
        return n1 - n2;
    }

    @Override
    public float multi(float n1, float n2) {
        System.out.println("Multiplicando: " + n1 + "* " + n2);
        return n1 * n2;
    }

    @Override
    public float div(float n1, float n2) {
        System.out.println("Dividindo: " + n1 + "/ " + n2);
        return n1 / n2;
    }

}
