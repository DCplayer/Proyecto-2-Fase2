import com.sun.corba.se.impl.ior.IdentifiableFactoryFinderBase;
import com.sun.corba.se.impl.oa.poa.ActiveObjectMap;
import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.StringTokenizer;

/**
 * Created by DiegoCastaneda on 01/09/2017.
 */
public class MainCocoR {
    public static void main (String args[]){
        StringTokenizer tokens;
        String linea;
        ArrayList<String> contenido = new ArrayList<>();
        LectordeArchivos lector = new LectordeArchivos();



        /*---------------------------------------------CreacionDirecta--------------------------------------------------------------*/


        /*Se tiene un HashSet que es el automata, entonces para encontrar la transicion con a donde va hay que ver
        * ambos ArrayList (transiciones y arrivals) de cada nodo del HashSet. Finalmente, ver si, del nodo en donde
        * estamos, digamos que se llama r, entonces tiene r.getConjunto.getContenido.equals(#) para ver si es el
        * ultimo*/

        /*Aqui Viene toda la historia de ifs, whiles y condicionales que ayudaran a verificar la aceptacion de COCOR*/

        ArrayList<String> lineas = lector.crearLector();
        if(!lector.chequearSintaxisInicial(lineas)){
            System.out.println("MalFuncionamiento en el programa");
        }
        if(!lector.chequearSintaxis(lineas)){
            System.out.println("Malfunction of .txt, Found several Syntax errors");
        }
        else{
            System.out.println("Archivo Sintacticamente Correcto");
        }
    }

    }



