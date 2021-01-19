package kwaksuin.portfolio.weatherapp;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class Parsing {
    String weather_url;
    Weather weather;

    XmlPullParserFactory parserCreator = XmlPullParserFactory.newInstance();
    XmlPullParser parser = parserCreator.newPullParser();

    public Parsing() throws XmlPullParserException {

    }

    // 초단기 실황
    public void chodangi(String url){

        // String now = AsyncTask_weather.doTime();
        // String today = AsyncTask_weather.doYearMonthDay();

        //weather_url = "http://apis.data.go.kr/1360000/VilageFcstInfoService/getUltraSrtNcst?serviceKey=YXsM3Qh%2FJr8FVZdMZDqSOlosBCDFmdxqGACs6BCxXIfowyIig7ftX59UngDgR%2FVktpkhOJee84KB%2BbXrvaS1QA%3D%3D&base_date=20210115&base_time=1400&numOfRows=10&nx=61&ny=130&_type=xml";

        URL urls;
        Document doc = null;


        try {

            urls = new URL(url);

            // xml문서를 열기
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            // xml문서 생성
            DocumentBuilder db = dbf.newDocumentBuilder();
            // xml문서 파싱
            doc = db.parse(new InputSource(urls.openStream()));
            //doc = db.parse(weather_url);
            doc.getDocumentElement().normalize();


        } catch (Exception e) {

        }

        String s = "";

        // <item> </item> 있는 정보들을 가져옴
        NodeList nodeList = doc.getElementsByTagName("item");;

        for(int i = 0; i<nodeList.getLength(); i++){

            // 날씨 데이터 추출
            s += "" + i + "날씨 : ";
            Node node = nodeList.item(i);
            Element element = (Element)node;
            NodeList idx = element.getElementsByTagName("category");

            Element nameElement = (Element)idx.item(2);
            idx = nameElement.getChildNodes();
            s += ((Node) idx.item(2)).getNodeValue().equals("T1H");

            // 데이터 읽음
           if (nodeList.item(2).getChildNodes().item(2).getNodeValue().equals("T1H")) {
                NodeList gugun = element.getElementsByTagName("obsrValue");
                s = gugun.item(2).getChildNodes().item(2).getNodeValue() + "";
                weather.setT1H(s);  // MainActivity에서 setText해야 됨
            }
        }
   }

    // 동네예보, 내일, 모레 최저 최고온도
    public void dongnaeparsing() {
        String nowtime = AsyncTask_weather.doTime();
        String today;
        String today_1;
        String today_2;

        int a = Integer.parseInt(nowtime);

        if (a <= 500) {
            today = AsyncTask_weather.doyes();
            today_1 = AsyncTask_weather.doYearMonthDay();
            today_2 = AsyncTask_weather.today_1();
            nowtime = "0500";
        } else if (a > 500 && a < 600) {
            today = AsyncTask_weather.doYearMonthDay();
            today_1 = AsyncTask_weather.today_1();
            today_2 = AsyncTask_weather.today_2();
            nowtime = "0200";
        } else {
            today = AsyncTask_weather.doYearMonthDay();
            today_1 = AsyncTask_weather.today_1();
            today_2 = AsyncTask_weather.today_2();
            nowtime = "0500";
        }

        // 요청
        weather_url = "http://apis.data.go.kr/1360000/VilageFcstInfoService/getVilageFcst?serviceKey=YXsM3Qh%2FJr8FVZdMZDqSOlosBCDFmdxqGACs6BCxXIfowyIig7ftX59UngDgR%2FVktpkhOJee84KB%2BbXrvaS1QA%3D%3D&base_date=20210113&base_time=1400&numOfRows=10&nx=61&ny=130&_type=xml";

        URL url;
        Document doc = null;

        try {
            url = new URL(weather_url);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            doc = db.parse(new InputSource(url.openStream()));
            doc.getDocumentElement().normalize();

        } catch (Exception e) {
        }

        String ss = "";

        NodeList nodeList = doc.getElementsByTagName("item");

        // 응답
        for (int i = 0; i < nodeList.getLength(); i++) {

            Node node = nodeList.item(i);
            Element element = (Element) node;
            NodeList nodelist = element.getElementsByTagName("category");


            // 최저
            if (nodelist.item(0).getChildNodes().item(0).getNodeValue().equals("TMN")) {
                NodeList sigungu= element.getElementsByTagName("fcstDate");
                ss = "" + sigungu.item(0).getChildNodes().item(0).getNodeValue() + "";
                if (ss.equals(today_1)) {
                    // 내일
                    NodeList sigungu2 = element.getElementsByTagName("fcstValue");
                    weather.TMN1 = sigungu2.item(0).getChildNodes().item(0).getNodeValue();
                }
                if (ss.equals(today_2)) {
                    // 내일모레
                    NodeList sigungu3 = element.getElementsByTagName("fcstValue");
                    weather.TMN2 = sigungu3.item(0).getChildNodes().item(0).getNodeValue();
                }
            }

            // 최고
            if (nodelist.item(0).getChildNodes().item(0).getNodeValue().equals("TMX")) {
                NodeList sigungu1 = element.getElementsByTagName("fcstDate");
                ss = "" + sigungu1.item(0).getChildNodes().item(0).getNodeValue() + "";
                if (ss.equals(today_1)) {
                    // 내일
                    NodeList sigungu2 = element.getElementsByTagName("fcstValue");
                    weather.TMX1 = sigungu2.item(0).getChildNodes().item(0).getNodeValue();
                }
                if (ss.equals(today_2)) {
                    // 내일모레
                    NodeList sigungu3 = element.getElementsByTagName("fcstValue");
                    weather.TMX2 = sigungu3.item(0).getChildNodes().item(0).getNodeValue();
                }
            }
        }
    }


}
