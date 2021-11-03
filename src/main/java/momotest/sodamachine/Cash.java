/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package momotest.sodamachine;

/**
 *
 * @author User
 */
public enum Cash {
    TENK(10000), TWENTYK(20000), FIFTYK(50000), THOUK(100000), TWO_THOUK(200000);
    private long value;
    private Cash(long value)
    {
        this.value = value;
    }
    
    long getValue(){
        return this.value;
    }
}
