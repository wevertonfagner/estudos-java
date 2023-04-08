import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class GeradoraDeFigurinhas {
    
    public void cria(InputStream inputStream, String nomeArquivo, String estrela, String mensagem) throws Exception {

        // leitura da imagem
        //InputStream inputStream = new URL(<URL da imagem>).openStream("entrada/filme.png");
        BufferedImage original = ImageIO.read(inputStream);

        // cria nova imagem em memória com transparência e com tamanho novo
        int largura = original.getWidth();
        int altura = original.getHeight();
        int novaAltura = altura + 200;

        BufferedImage novaImagem = new BufferedImage(largura, novaAltura, BufferedImage.TRANSLUCENT);

        // copia a imagem original pra novo imagem (em memória)
        Graphics2D graphics = (Graphics2D) novaImagem.getGraphics();

        graphics.drawImage(original, 0, 0, null);

        // Configurar fonte
        var fonte = new Font(Font.SANS_SERIF, Font.BOLD, 75);
        graphics.setColor(Color.YELLOW);
        graphics.setFont(fonte);

        // escrever uma frase na nova imagem
        FontMetrics fontMetrics = graphics.getFontMetrics();

        Rectangle2D retanguloMensagem = fontMetrics.getStringBounds(mensagem, graphics);
        Rectangle2D retanguloEstrela = fontMetrics.getStringBounds(estrela, graphics);

        int larguraMensagem = (int) retanguloMensagem.getWidth();
        int pontoInicialMensagem = (largura - larguraMensagem) / 2;

        int larguraEstrela = (int) retanguloEstrela.getWidth();
        int pontoInicialEstrela = (largura - larguraEstrela) / 2;

        graphics.drawString(mensagem, pontoInicialMensagem, novaAltura - 100);
        graphics.drawString(estrela, pontoInicialEstrela, novaAltura);


        // escrever a nova imagem em um 
        ImageIO.write(novaImagem, "png", new File(nomeArquivo));


    }

}
