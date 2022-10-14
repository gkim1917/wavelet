import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;

class Handler implements URLHandler {
    // The one bit of state on the server: a number that will be manipulated by
    // various requests.
    List<String> strlist = new ArrayList<>();

    public String handleRequest(URI url) {
            if (url.getPath().contains("/add")) {
                String[] parameters = url.getQuery().split("=");
                strlist.add(parameters[1]);
                return String.format(parameters[1]+" string added to the list");
            }
            else if (url.getPath().contains("/search")) {
                String[] parameters = url.getQuery().split("=");
                String result= null;
                String substr = parameters[1];
                for(int i=0;i<strlist.size();i++){
                    if(strlist.get(i).contains(substr)){
                        if(result!=null){
                            result+=" and ";
                        }
                        result+=strlist.get(i);
                    }
                }
                return result;
            }
            return "404 Not Found!";
        }
}

class SearchEngine {
    public static void main(String[] args) throws IOException {
        if(args.length == 0){
            System.out.println("Missing port number! Try any number between 1024 to 49151");
            return;
        }

        int port = Integer.parseInt(args[0]);

        Server.start(port, new Handler());
    }
}