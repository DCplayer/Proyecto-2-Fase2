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

    private ArrayList<ArrayList<String>> palabrasConocidas = new ArrayList<>();

    private int numeroCharacters = 0;
    private int numeroKeywords = 0;

    private String imports = "import java.util.ArrayList;\nimport java.util.Scanner;\nimport java.io.*;\n";
    private String header = "\npublic class Lexer{\n";
    private String mainIntroduction = "    public static void main (String args[]){\n";
    private String contenido = "";

    private String lastBracket = "  }\n}";

    /*String para los tokens del documento de tokens*/
    private String tokens = "";

    private ArrayList<String> lineas = new ArrayList<>();

    /*Al crear el CreadorTokens, se define el numeroCharacters y el numeroKeywords, con los cuales se puede definir
    * de donde a donde va el contenido de cada uno */
    public CreadorTokens(ArrayList<String> lineas) {
        this.lineas = lineas;
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


    /*--------------Metodo para crear el documento con los Tokens que sera leido por el programa lexeado-------------*/
    public void CreateNewTokenFile(){
        if(numeroCharacters != 0){
            ArrayList<String> characters = new ArrayList<>();
            for (int j = numeroCharacters; j < numeroKeywords -1; j++){
                characters.add(lineas.get(j));
            }

            tokens = tokens + agregarTokensCharacters(characters);
            
        }
        /*Creador de Tokens debajo de KeyWord*/
        if(numeroKeywords != 0){
            ArrayList<String> keywords = new ArrayList<>();
            for(int j = numeroKeywords; j < lineas.size()-1; j++){
                keywords.add(lineas.get(j));
            }

            tokens = tokens + agregarTokensKeywords(keywords);

        }
    }
    /*----------Metodo para crear el documento de Java capaz de identificar los tokens que se le han dado-------------*/
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

    /*----------------------Funcion que devolvera el String con los tokens a agregar---------------------------------*/
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

    //--------------------Metodo para agregar los tokens producidos en la parte de CHARACTERS------------------------
    public String agregarTokensCharacters(ArrayList<String> lineasEspecificas){
        String resultado = "";
        for(String linea: lineasEspecificas){
            ArrayList<String> palabraAprendida = new ArrayList<>();
            boolean Simple = chequearSiCharacterSimple(linea);

            if(Simple){
                String lineaSinEspacios = linea.replaceAll("\\s+", "");
                int indexIgual = lineaSinEspacios.indexOf("=");

                String identificador = lineaSinEspacios.substring(0,indexIgual);
                String valor = lineaSinEspacios.substring(indexIgual+2, lineaSinEspacios.length()-2);

                palabraAprendida.add(identificador);
                for(int x =0; x < valor.length(); x++){
                    String temporal = valor.substring(x, x+1);
                    resultado = resultado + identificador + "," + temporal + "\n";
                    palabraAprendida.add(temporal);
                }
                palabrasConocidas.add(palabraAprendida);



            }
            if(!Simple){

            }

        }
        return resultado;
    }

    //-----------------------Metodo para chequear si la linea de Characteres es simple--------------------------------
    public boolean chequearSiCharacterSimple(String lineaConEspacios){
        boolean resultado = false;
        if(lineaConEspacios.indexOf("=") > 0){
            String lineaSinEspacios = lineaConEspacios.replaceAll("\\s+", "");
            int elIndex = lineaSinEspacios.indexOf("=")+1;

            String estructuraFinal = lineaSinEspacios.substring(lineaSinEspacios.length()-2);
            String estructuraInicial = lineaSinEspacios.substring(elIndex, elIndex +1 );
            if(estructuraInicial.equals("\"") && estructuraFinal.equals("\".")){
                resultado = true;
            }

        }
        return  resultado;
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
