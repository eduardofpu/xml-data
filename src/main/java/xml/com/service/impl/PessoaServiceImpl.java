package xml.com.service.impl;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import xml.com.modelo.Pessoa;
import xml.com.repository.PessoaRepository;
import xml.com.service.PessoaService;

import javax.transaction.Transactional;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.IOException;

@Service
public class PessoaServiceImpl implements PessoaService {


    private static final String DIR = System.getProperty("user.dir");
    private static final String USER = System.getProperty("user.home");
    private static final String SEPARATOR = System.getProperty("file.separator");
    private static final String DOWNLOADS = "Downloads" + SEPARATOR;


    private static final String CAMINHO_XML = DIR + SEPARATOR + "src" + SEPARATOR + "main" + SEPARATOR + "resources" + SEPARATOR + "xml" + SEPARATOR;

    private PessoaRepository pessoaRepository;

    @Autowired
    public PessoaServiceImpl(PessoaRepository pessoaRepository) {
        this.pessoaRepository = pessoaRepository;
    }

    @Override
    @SneakyThrows
    @Transactional
    public void createPessoa(MultipartFile caminho){
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = getCaminhoDoArquivo(caminho, builder);
        NodeList listaPessoas = doc.getElementsByTagName("pessoa");
        int tamanhoDaLista = listaPessoas.getLength();
        tamanhoDaLista(listaPessoas, tamanhoDaLista);
    }

    private Document getCaminhoDoArquivo(MultipartFile caminho, DocumentBuilder builder) throws SAXException, IOException {
        String folder = USER + SEPARATOR + DOWNLOADS + caminho.getOriginalFilename();
        return builder.parse(folder);
    }

    private void tamanhoDaLista(NodeList listaPessoas, int tamanhoDaLista) {
        for(int i = 0 ; i < tamanhoDaLista; i++){
            Pessoa p = new Pessoa();
            Node noPessoa = listaPessoas.item(i);
            nomePessoaIsElementNode(p, noPessoa);
            pessoaRepository.save(p);
        }
    }

    private void nomePessoaIsElementNode(Pessoa p, Node noPessoa) {
        if(noPessoa.getNodeType() == Node.ELEMENT_NODE){


            Element elementoPessoa = (Element) noPessoa;
            String id = elementoPessoa.getAttribute("codigo");

            System.out.println("CODIGO : " + id);
            p.setCodigo(id);

            NodeList listaDeFilhosDaPessoa = elementoPessoa.getChildNodes();
            int tamanhoListaFilho = listaDeFilhosDaPessoa.getLength();

            listaFilho(p, listaDeFilhosDaPessoa, tamanhoListaFilho);
        }
    }

    private void listaFilho(Pessoa p, NodeList listaDeFilhosDaPessoa, int tamanhoListaFilho) {
        for(int j = 0; j < tamanhoListaFilho; j++){

            Node noFilho = listaDeFilhosDaPessoa.item(j);

            nomeDoFilhoIsElementNode(p, noFilho);
        }
    }

    private void nomeDoFilhoIsElementNode(Pessoa p, Node noFilho) {
        if(noFilho.getNodeType() == Node.ELEMENT_NODE){


            Element elementoFilho = (Element) noFilho;
            switch (elementoFilho.getTagName()){
                case "nome":

                    System.out.println("NOME : " + elementoFilho.getTextContent());
                    p.setNome(elementoFilho.getTextContent());
                    break;
                case "idade":

                    System.out.println("IDADE : " + elementoFilho.getTextContent());
                    p.setIdade(elementoFilho.getTextContent());
                    break;
                case "peso":

                    System.out.println("PESO : " + elementoFilho.getTextContent());
                    p.setPeso(elementoFilho.getTextContent());
                    break;
                default:
                    System.out.println("Não encontrado");

            }
        }
    }
}
