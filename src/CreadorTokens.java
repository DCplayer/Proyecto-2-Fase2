import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader;

import java.awt.datatransfer.StringSelection;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by DiegoCastaneda on 09/09/2017.
 */
public class CreadorTokens {

    private int numeroCharacters = 0;
    private int numeroKeywords = 0;

    private String imports = "import java.util.ArrayList;\nimport java.util.Scanner;\n";
    private String header = "\npublic class Lexer{\n";
    private String mainIntroduction = "    public static void main (String args[]){\n";
    private String contenido = "";

    private String lastBracket = "  }\n}";

    /*String para los tokens del documento de tokens*/
    private String tokens = "";

    private ArrayList<String> lineas = new ArrayList<>();



        /*Metodo que ayudara a la creacion de los tokens para su utilizacion en otro lenguaje por medio de division en cada
    * del documento, y depeniendo debajo de que palabra reservada de Cocol se encuentre, para su proxima creacion como
    * tokens*/
    public void dividirVencer(){

        for(int s = 0; s<lineas.size(); s++){
            StringTokenizer lineaALeer = new StringTokenizer(lineas.get(s));
            String laPrueba = lineaALeer.nextToken();
            if(laPrueba.equals("CHARACTERS")){
                numeroCharacters = s +1;
            }
            else if(laPrueba.equals("KEYWORDS")){
                numeroKeywords = s +1;
            }
        }


    }

    public void CreateNewTokenFile(){
        if(numeroCharacters != 0){

        }
        if(numeroKeywords != 0){
            ArrayList<String> keywords = new ArrayList<>();
            for(int j = numeroKeywords; j < lineas.size()-1; j++){
                keywords.add(lineas.get(j));
            }

            tokens = tokens + agregarTokensKeywords(keywords);

        }
    }

    public void CreateNewJavaFile(ArrayList<String> lineas){
        contenido = imports + header + mainIntroduction;

        /*Aqui viene el codigo que le da forma a la clase de Java*/
        contenido = contenido + lastBracket;

        try {

            PrintWriter writer = new PrintWriter("Lexer.java");
            writer.println(contenido);
            writer.close();

        } catch (Exception e) {

            e.printStackTrace();

        }


    }

    /*Funcion que devolvera el String con los tokens a agregar*/
    public String agregarTokensKeywords(List<String> lineasEspecificas){

        ArrayList<String> contieneIdent = new ArrayList<>();
        ArrayList<String> contieneValores = new ArrayList<>();

        for(String s: lineasEspecificas){
            String linea = s.replaceAll("\\s+", "");


            String identificador = "";
            String valor = "";
            int x = 0;
            boolean booleano = true;
            while (x < s.length() && booleano){
                if(!Character.toString(linea.charAt(x)).equals("=")){
                    identificador = identificador + Character.toString(linea.charAt(x));
                }
                else if(Character.toString(linea.charAt(x)).equals("=")){
                    booleano = false;
                    break;
                }
                x = x+1;


            }
            contieneIdent.add(identificador);
            booleano = true;
            x = x + 2;
            while (x < s.length() && booleano){
                if(Character.toString(linea.charAt(x)).equals("\"")){
                    booleano = false;
                    break;
                }
                else if(!Character.toString(linea.charAt(x)).equals("=")){
                    valor = valor + Character.toString(linea.charAt(x));
                }
                x = x+1;

            }
            contieneValores.add(valor);

        }
        String tokensKeywords = "\n";
        for(int s = 0; s < contieneIdent.size(); s++){
            tokensKeywords = tokensKeywords + contieneIdent.get(s) + "," + contieneValores.get(s) + "\n";
        }
        return tokensKeywords;
    }

    public int getNumeroCharacters() {
        return numeroCharacters;
    }

    public int getNumeroKeywords() {
        return numeroKeywords;
    }

    public ArrayList<String> getLineas() {
        return lineas;
    }

    public void setLineas(ArrayList<String> lineas) {
        this.lineas = lineas;
    }
}
