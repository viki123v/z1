package date; 

import htmlGen.HtmlGen;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;


public class Main {
    private  final static String errorMsg = "INVALID OPERАTION - the service only functions either without any parameters or with the parameter \"time\", used with or without a value."; 
    
    private static String[] queryPassedValues(){ 
        String query = System.getenv().get("QUERY_STRING"); 

        if(query ==null || query.isEmpty())
            return new String[0]; 
        return Arrays.stream(query.split("&"))
        .map(pair -> pair.split("=")[0])
        .toArray(String[]::new); 
    }

    public static void main(String[] args)  {
        System.out.println(HtmlGen.contentHtmlHeader);
        String[] queryKeys = queryPassedValues(); 
        HtmlGen gen; 
        

        if(queryKeys.length==0 || queryKeys[0].isEmpty()){
            gen = new HtmlGen("Current Date ");
            
            //
            String msg = LocalDateTime.now().atZone(ZoneId.of("UTC")).format(DateTimeFormatter.ofPattern("yyyy-MM-dd zzz")); 

            gen.addMessage(msg);

        }else if(queryKeys.length==1 && queryKeys[0].equals("time")){
            gen=new HtmlGen("Current Date and Тime"); 
            //

            String msg = LocalDateTime.now().atZone(ZoneId.of("UTC")).format(DateTimeFormatter.ofPattern("dd.MM.yy HH24:mm:ss zzz")); 

            gen.addMessage(msg);
            
        }else{ 
            gen = new HtmlGen("Current Date"); 
            gen.addMessage(errorMsg);
        }

        System.out.println(gen.getFinishMsg());
    }    
}