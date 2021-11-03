/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package momotest.sodamachine;

/**
 * Class to hold product and change
 * @author User
 */
public class Pair<E1, E2> {

    private E1 first;
    private E2 second;

    public Pair(E1 first, E2 second) {
        this.first = first;
        this.second = second;
    }

    public E1 getFirst() {
        return first;

    }

    public E2 getSecond() {
        return second;
    }
}
