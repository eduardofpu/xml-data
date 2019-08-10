package xml.com;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class XmlDom {
    private static final String DIR = System.getProperty("user.dir");
    private static final String SEPARATOR = System.getProperty("file.separator");
    private static final String CAMINHO_XML = DIR + SEPARATOR + "src" + SEPARATOR + "main" + SEPARATOR + "resources" + SEPARATOR + "xml" + SEPARATOR + "pessoa.xml";


    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {


        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        Document doc = builder.parse(CAMINHO_XML);

        NodeList listaPessoas = doc.getElementsByTagName("pessoa");
        int tamanhoDaLista = listaPessoas.getLength();

        for(int i = 0 ; i < tamanhoDaLista; i++){

            Node noPessoa = listaPessoas.item(i);
            if(noPessoa.getNodeType() == Node.ELEMENT_NODE){

                Element elementoPessoa = (Element) noPessoa;
                String id = elementoPessoa.getAttribute("codigo");
                System.out.println("CODIGO : " + id);

                NodeList listaDeFilhosDaPessoa = elementoPessoa.getChildNodes();
                int tamanhoListaFilho = listaDeFilhosDaPessoa.getLength();

                for(int j = 0; j < tamanhoListaFilho; j++){

                    Node noFilho = listaDeFilhosDaPessoa.item(j);


                    if(noFilho.getNodeType() == Node.ELEMENT_NODE){
                        Element elementoFilho = (Element) noFilho;
                        switch (elementoFilho.getTagName()){
                            case "nome":
                                System.out.println("NOME : " + elementoFilho.getTextContent());
                                break;
                            case "idade":
                                System.out.println("IDADE : " + elementoFilho.getTextContent());
                                break;
                            case "peso":
                                System.out.println("PESO : " + elementoFilho.getTextContent());
                                break;
                            default:
                                System.out.println("Não encontrado");
                        }
                    }
                }
            }
        }

    }
}
