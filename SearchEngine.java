import java.io.IOException;
import java.net.URI;
import java.util.*;

class Handler implements URLHandler {
    // The one bit of state on the server: a number that will be manipulated by
    // various requests.
    int num = 0;
	ArrayList<String> queries = new ArrayList<String>();
	
    public String handleRequest(URI url) {
        if (url.getPath().equals("/")) {
            return "wow the index works (id but api documentation here but im lazy";
        } else if (url.getPath().contains("/add")) {
            String[] parameters = url.getQuery().split("=");
			if (parameters[0].equals("s")) {
				queries.add(parameters[1]);
				return "200";
			}
        } else if (url.getPath().contains("/search")) {
			StringBuilder retvals = new StringBuilder();
            String[] parameters = url.getQuery().split("=");
			if (parameters[0].equals("s")) {
				for (int i = 0; i < queries.size(); i++) {
					if (queries.get(i).contains(parameters[1])) {
						if (!retvals.toString().equals("")) {
							retvals.append(", ");
						}
						retvals.append(queries.get(i));
						System.out.println("uwu");
					}
				}
				return retvals.toString();
			}
			return "500";
		}
		else {
            System.out.println("Path: " + url.getPath());
            return "404 Not Found!";
        }
		return null;
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
