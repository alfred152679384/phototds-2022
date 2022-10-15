package um.tds.phototds;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Prueba para el segundo commit!" );
        ArrayList<String> lista = new ArrayList<String>();
        lista.addAll(Arrays.asList("prueba","de","photoTDS"));
        lista.forEach(s -> System.out.println(s));
    }
}
