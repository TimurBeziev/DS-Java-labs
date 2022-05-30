package server;


public class Main {

    public static void main(String[] args) {
//        String productsRepoPath = "/Users/timurbeziev/Uni/Java/6th sem/Lab45/src/main/resources/xmlFiles/products.xml";
//        String stocksRepoPath = "/Users/timurbeziev/Uni/Java/6th sem/Lab45/src/main/resources/xmlFiles/stocks.xml";
//        String infosRepoPath = "/Users/timurbeziev/Uni/Java/6th sem/Lab45/src/main/resources/xmlFiles/infos.xml";
        String productsXMLPath = "JaxbProducts.xml";
        String stocksXMLPath = "JaxbStocks.xml";
        String infosXMLPath = "JaxbInfos.xml";
        XmlController server = new XmlController(productsXMLPath, stocksXMLPath, infosXMLPath);
    }
}
