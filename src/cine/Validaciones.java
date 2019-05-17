
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cine;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validaciones {
    public static boolean validaEmail(String correo){
        boolean correcto;
        Pattern p = Pattern.compile("[a-zA-Z0_9][a-zA-Z0-9]*@[a-zA-Z0-9]+([.][a-z]+)+");
        Matcher m = p.matcher(correo);
        correcto = m.find() && m.group().equals(correo);
        return correcto;
    }
    public static boolean validaNombre(String nombre){
        boolean correcto;
        Pattern p = Pattern.compile("[A-Z][a-z]+");
        Matcher m = p.matcher(nombre);
        correcto = m.find() && m.group().equals(nombre);
        return correcto;
    }
    public static boolean validaTelefono(String tel){
        boolean correcto;
        Pattern p = Pattern.compile("[0_9][0_9][0_9][0_9][0_9][0_9][0_9][0_9][0_9][0_9]");
        Matcher m = p.matcher(tel);
        correcto = m.find() && m.group().equals(tel);
        return correcto;
    }
}
