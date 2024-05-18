package htmlGen;

public class HtmlGen {
    public static final String contentHtmlHeader = "Content-Type: text/html\n\n"; 
    StringBuilder msgBuilder; 

    public HtmlGen(String title){ 
        msgBuilder = new StringBuilder(String.format("<html><head><title>%s</title></head><body>",title));
    }

    public void addMessage(String msg) { 
        msgBuilder.append(String.format("<p>%s</p>",msg)); 
    }

    public String getFinishMsg(){
        
        msgBuilder.append("</body></html>"); 
        String msg = msgBuilder.toString();         
        msgBuilder = null; 

        return msg; 
    }
}
