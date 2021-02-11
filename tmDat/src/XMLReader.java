import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;

public class XMLReader {
    private static Map<Integer,String> paramNames = new TreeMap<Integer,String>();

    public static void XMLread(File xmlFile){
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try{
            builder = factory.newDocumentBuilder();
            Document document = builder.parse(xmlFile);
            document.getDocumentElement().normalize();
            NodeList nodeList = document.getElementsByTagName("Param");

            for(int i = 0; i<nodeList.getLength(); i++) {
                Element node = (Element) nodeList.item(i);
                paramNames.put(new Integer(node.getAttribute("number")), node.getAttribute("name"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Map<Integer, String> getParamNames() {
        return paramNames;
    }

    public static void printParamNames() throws FileNotFoundException {
        PrintWriter f_numberName = new PrintWriter("ParamNumName.txt");
        for(int i : paramNames.keySet()){
            f_numberName.printf("%d\t%s\n",i,paramNames.get(i));
        }
    }
}

