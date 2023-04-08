import java.io.File;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.List;
import java.util.Map;

public class App {
    public static void main(String[] args) throws Exception {

        Url objUrl = new Url();

        // fazer uma conexão HTTP e cuscar os top 10
        // $Env:URL_JSON =
        //setx URL_JSON

        URI endereco = URI.create(objUrl.urlString);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder(endereco).GET().build();
        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
        
        String body = response.body(); 

        // pegar dados nos interessa (titulo, poste, avaliação)
        JsonParser parser = new JsonParser();
        List<Map<String, String>> listaDeConteudos = parser.parse(body); 
                
        // exibir e manipular
        int contador = 1;

        var diretorio = new File("figurinhas/");
            diretorio.mkdir();

        var geradora = new GeradoraDeFigurinhas();

         for (int i = 0; i < listaDeConteudos.size(); i++) {
            Map<String, String> conteudo = listaDeConteudos.get(i);
            String titulo = conteudo.get("title");
            String urlImage = conteudo.get("image");

            InputStream inputStream = new URL(urlImage).openStream();
            String nomeArquivo = "figurinhas/" + titulo + ".png";

            String mensagem = "TOP " + contador++;
            System.out.println(mensagem);
            System.out.println(titulo);
            /* System.out.println("\u001b[1mTítulo: \u001b[m" + filme.get("title"));
            System.out.println("\u001b[1mURL da imagem: \u001b[m" + filme.get("image"));
            System.out.println(filme.get("imDbRating")); */

            double classeficacao = Double.parseDouble(conteudo.get("imDbRating"));
            int numEstrela = (int) classeficacao;
            
            String estrela = "⭐".repeat(numEstrela);
            System.out.print(estrela);

            System.out.println("\n");
            

            geradora.cria(inputStream, nomeArquivo, estrela, mensagem); 


        }

    }
}
