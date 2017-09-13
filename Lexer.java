import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;
import java.io.FileReader;
import java.util.StringTokenizer;

public class Lexer{
    public static void main (String args[]){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingrese el nombre del archivo con el código que desea lexear: ");
        String archivo = scanner.nextLine();
        ArrayList<String> lineasAImprimir = new ArrayList();
        try (BufferedReader lectorArchivo = new BufferedReader(new FileReader(archivo))){
            try (BufferedReader lectorTokens = new BufferedReader(new FileReader("tokens.txt"))){
                lectorTokens.mark(1000);
                String lineaArchivo = lectorArchivo.readLine();
                while(lineaArchivo != null){
                    StringTokenizer st = new StringTokenizer(lineaArchivo);
                    while(st.hasMoreTokens()){
                        String componente = st.nextToken();
                        String lineaToken = lectorTokens.readLine();
                        boolean existencia = false;
                        while(lineaToken != null && !lineaToken.equals("")){
                            int indexComa = lineaToken.indexOf(",");
                            String s = lineaToken.substring(0,indexComa);
                            if(s.equals(componente)){
                                lineasAImprimir.add(lineaToken);
                                lectorTokens.reset();
                                lectorTokens.mark(1000);
                                existencia = true;
                                break;
                            }
                            lineaToken = lectorTokens.readLine();
                        }
                        if(!existencia){
                            for(int x = 0; x<componente.length(); x++){
                                lectorTokens.reset();
                                lectorTokens.mark(1000);
                                String particion = componente.substring(x, x+1);
                                lineaToken = lectorTokens.readLine();
                                while(lineaToken != null){
                                    int indexComa = lineaToken.indexOf(",");
                                    String s = lineaToken.substring(0,indexComa);

                                    if(s.equals(particion)){
                                        lineasAImprimir.add(lineaToken);
                                        lectorTokens.reset();
                                        lectorTokens.mark(1000);
                                        break;
                                    }
                                    lineaToken = lectorTokens.readLine();
                                }
                            }
                        }                    }
                    lineaArchivo = lectorArchivo.readLine();
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
  }
}
