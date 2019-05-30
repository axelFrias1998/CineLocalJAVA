
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
        Pattern p = Pattern.compile("^[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?$");
        Matcher m = p.matcher(correo);
        correcto = m.find() && m.group().equals(correo);
        return correcto;
    }
    public static boolean validaNombre(String nombre){
        boolean correcto;
        Pattern p = Pattern.compile("([A-ZÁ-ÚÑ][a-zá-úñ]+[ ]?)+");
        Matcher m = p.matcher(nombre);
        correcto = m.find() && m.group().equals(nombre);
        return correcto;
    }
    public static boolean validaTelefono(String tel){
        boolean correcto;
        Pattern p = Pattern.compile("[1_9][1_9][0_9][0_9][0_9][0_9][0_9][0_9][0_9][0_9]");
        Matcher m = p.matcher(tel);
        correcto = m.find() && m.group().equals(tel);
        return correcto;
    }
    public static boolean validaCadena(String Pelicula){
        boolean correcto;
        Pattern p = Pattern.compile("([A-ZÁ-ÚÑ0-9:,][a-zá-úñ0-9:,]+[ ]?)+");
        Matcher m = p.matcher(Pelicula);
        correcto = m.find() && m.group().equals(Pelicula);
        return correcto;
    }
    
    public static boolean validaAnio(String Anio){
        boolean correcto;
        Pattern p = Pattern.compile("[2][0][12][90]");
        Matcher m = p.matcher(Anio);
        correcto = m.find() && m.group().equals(Anio);
        return correcto;
    }
    public static boolean validaDuracion(String Duracion){
        boolean correcto = false;
        if(Integer.parseInt(Duracion) > 20 && Integer.parseInt(Duracion) < 240)
            return correcto = true;
        else
            return correcto;
    }
}
