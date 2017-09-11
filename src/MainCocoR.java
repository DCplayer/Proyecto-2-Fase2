import com.sun.corba.se.impl.ior.IdentifiableFactoryFinderBase;
import com.sun.corba.se.impl.oa.poa.ActiveObjectMap;
import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.StringTokenizer;

/*Este programa funciona siempre y cuando
* 1. Si se quiere realizar el basicSet, el cual se añade con el termino de Char [.. Char], se debde de utilizar -- en lugar de ..
* 2. Para sumar basicSets dentro de CHARACTERS, se debe de usar el signo ~ o bien Alt+126 para *//**
 * Created by DiegoCastaneda on 01/09/2017.
 */
public class MainCocoR {
    public static void main (String args[]){
        StringTokenizer tokens;
        String linea;
        ArrayList<String> contenido = new ArrayList<>();
        LectordeArchivos lector = new LectordeArchivos();


        //Arraylist que contiene las lineas no vacias del documento con las especificaciones de Cocol/R
        ArrayList<String> lineas = lector.crearLector();

        //Creando el creador de tokens con las lineas del documento de especificaciones de Cocol/R
        CreadorTokens creador = new CreadorTokens(lineas);

        //Verificar si tiene algun error sintactico el documento. Si no tuviera ninguno, se pasa a la parte de creacion
        // del lexer.
        if(!lector.chequearSintaxis(lineas)){
            System.out.println("Malfunction of .txt, Found several Syntax errors");
        }
        else{
            System.out.println("Archivo Sintacticamente Correcto");


            creador.CreateNewJavaFile(lineas);
            creador.CreateNewTokenFile();
        }



    }

    }



