/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package momotest.sodamachine;

/**
 *
 * @author User
 */
public class NotSelectedProductExpcetion extends RuntimeException {

    private String message;

    public NotSelectedProductExpcetion(String string) {
        this.message = string;
    }

    @Override
    public String getMessage() {
        return message;
    }

}
