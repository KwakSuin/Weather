package kwaksuin.portfolio.weatherapp;

import android.widget.Toast;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class Parsing {
    String weather_url;

    // 초단기 실황
    public void chodangi(){
        String now = AsyncTask_weather.doTime();
        String today = AsyncTask_weather.doYearMonthDay();

        weather_url = "http://apis.data.go.kr/1360000/VilageFcstInfoService/getUltraSrtNcst"
                +"ServiceKey = YXsM3Qh%2FJr8FVZdMZDqSOlosBCDFmdxqGACs6BCxXIfowyIig7ftX59UngDgR%2FVktpkhOJee84KB%2BbXrvaS1QA%3D%3D"
                +"base_date=" +         // 발표 일자
                today +
                "&" +
                "base_time=" +          // 발표 시간
                now +
                "&" +
                "nx=61&" +              // 위치 (좌표)
                "ny=130&" +
                "numOfRows=10&" +       // 한 페이지 결과 수
                "_type=xml";            // 데이터 타입

        URL url;
        Document doc = null;

        try {
            url = new URL(weather_url);

            // xml문서를 열기
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            doc = db.parse(new InputSource(url.openStream()));
            doc.getDocumentElement().normalize();

        } catch (Exception e) {

        }

        String s = "";
        NodeList nodeList = doc.getElementsByTagName("data");

        for(int i = 0; i<nodeList.getLength(); i++){

            // 날씨 데이터 추출
            s += "" + i + "날씨 : ";
            Node node = nodeList.item(i);
            Element element = (Element)node;
            NodeList nameList = element.getElementsByTagName("category");
            Element nameElement = (Element)nameList.item(0);
            nameList = nameElement.getChildNodes();
            s += "온도 = " +((Node) nameList.item(0)).getNodeValue().equals("Tem");
        }

    }
}
