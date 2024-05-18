package hangman;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import htmlGen.HtmlGen;
import java.util.Set;

public class Main {
    private static final String city = "RESEN"; 
    private static String winningMsg = "You guessed correctly - RESEN"; 
    private static final String errorMsg = "INVALID OPERАTION - the hangman service expects a GET or POST parameter operand1 with a single alphabetical character as a value and will output if you guessed the char correctly (if an imagined string contains the guessed character and at which positions)"; 

    public static boolean isInputOperandGood(String[] operand){         
        return operand[0].equals("operand1") && operand[1].length() == 1
         && Character.isAlphabetic(operand[1].charAt(0)); 
    }

    public static char handleGETRequest() throws Exception
    { 
        String[] query = System.getenv().get("QUERY_STRING").split("&");
        
        if(query.length != 1) 
            throw new Exception("The query string isn't formatted correctly "); 

        String[] operand1 = query[0].split("="); 
        
        if(!isInputOperandGood(operand1))
            throw new Exception("The input doesn't have the correct format "); 
        
        return operand1[1].toUpperCase().charAt(0); 
    }

    public static char handlePOSTRequest() throws Exception
    { 
        Scanner in = new Scanner(System.in); 
        if(!in.hasNextLine()){
            in.close();
            throw new Exception("No input in the POST body");
        }

        String[] input = in.nextLine().split("=");
        
        if(!isInputOperandGood(input)){
            in.close();
            throw new Exception("The input doesn't have the correct format "); 
        }
        
        char letter = input[1].toUpperCase().charAt(0);

        in.close();
        return letter; 
    }

    public static Set<Character> getCookie(){
        String guessedString = System.getenv().get("HTTP_COOKIE");

        if(guessedString==null || guessedString.isEmpty()){
            return new HashSet<>(); 
        }
        
        Set<Character> guessed=new HashSet<>(); 
        String value = guessedString.split("=")[1]; 

        for(char letter : value.toCharArray()){
            guessed.add(letter);
        }

        return guessed; 
    }
    public static void setCookie(Set<Character> guessed){
        String guessedString = guessed.stream().map(ch -> String.valueOf(ch)).reduce("",String::concat); 
        System.out.println("SET-COOKIE: guessed=" + guessedString);
    }

    public static void main(String[] args) {
        String requestType = System.getenv().get("REQUEST_METHOD"); 
        char msg  ; 

        try{
            
            if(requestType.equals("GET")){ 
                msg = handleGETRequest(); 
            }else {
                msg = handlePOSTRequest(); 
            }
            
            Set<Character> guessedLetters = getCookie(); 
            HtmlGen gen = new HtmlGen("Hangman"); 
            
            boolean hasLeft = false;
            StringBuilder displayedFormat = new StringBuilder();
            
            for(char letter : city.toCharArray()){
                if(guessedLetters.contains(letter)){
                    displayedFormat.append(letter);
                }else if(letter==msg){
                    displayedFormat.append(msg); 
                    guessedLetters.add(msg); 
                }
                else{
                    displayedFormat.append("_");
                    hasLeft=true; 
                }
            }

            if(!hasLeft){
                gen.addMessage(winningMsg);
            }else{
                gen.addMessage(displayedFormat.toString());
                setCookie(guessedLetters);
            }
             
            System.out.println(HtmlGen.contentHtmlHeader);
            System.out.println(gen.getFinishMsg());

        }catch(Exception ignore){
            HtmlGen gen = new HtmlGen("HELP"); 
            gen.addMessage(errorMsg);
            
            System.out.println(HtmlGen.contentHtmlHeader);
            System.out.println(gen.getFinishMsg());
        }
    }
}