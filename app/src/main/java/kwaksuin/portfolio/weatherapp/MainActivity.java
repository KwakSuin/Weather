package kwaksuin.portfolio.weatherapp;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;

import android.os.Bundle;

import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/*
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

 */

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.lang.String;


public class MainActivity extends AppCompatActivity {
    LinearLayout location_layout;
    ImageView location_img;
    TextView location;

    ImageView icon;

    TextView today_temp;            // 오늘 날씨
    TextView tomorrow_temp;         // 내일 날씨
    TextView next_tomorrow_temp;    // 모레 날씨

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        today_temp = findViewById(R.id.today_temp);
        tomorrow_temp = findViewById(R.id.tomorrow_temp);
        next_tomorrow_temp = findViewById(R.id.next_tomorrow_temp);
        icon = findViewById(R.id.icon);

        // 지역선택 spinner 화면 이동
        location_layout = findViewById(R.id.location_layout);
        location_layout.setOnClickListener(v -> {
            Intent intent = new Intent(getApplication(), Location.class);
            startActivity(intent);
        });

        // 지역선택 spinner 화면 이동
        location_img = findViewById(R.id.location_img);
        location_img.setOnClickListener(v -> {
            Intent intent = new Intent(getApplication(), Location.class);
            startActivity(intent);
        });

        // 지역선택 spinner 화면 이동
        location= findViewById(R.id.location);
        location.setOnClickListener(v -> {
            Intent intent = new Intent(getApplication(), Location.class);
            startActivity(intent);
        });

        TempThread thread = new TempThread();
        thread.start();


    }

    public class TempThread extends Thread{
        private static final String TAG = "";
        String data;
        String sky;
        String rain;

        String TM_min;
        String TM_max;

        String NTM_min;
        String NTM_max;

        public TempThread(){
        }

        public void run() {
            data = getXmlData();        // 현재 날씨
            sky = getXmlSky();          // 현재 하늘
            rain = getXmlRain();        // 현재 비


            TM_min = getXmlTMN();       // 내일 최저온도
            TM_max = getXmlTMX();       // 내일 최고온도

            NTM_min = getXmlTMN_2();    // 모레 최저온도
            NTM_max = getXmlTMX_2();    // 모레 최고온도


            Log.i(TAG,"현재 SKY : " + sky);
            Log.i(TAG,"현재 RAIN : " + rain);

            // rain : 0 비안옴
            if(rain.equals("0")){
                if(sky.equals("0") || sky.equals("1") || sky.equals("2") || sky.equals("3") || sky.equals("4") || sky.equals("5")){
                    // 맑음
                    runOnUiThread(() -> icon.setImageResource(R.drawable.sun_color));
                }
                if(sky.equals("6") || sky.equals("7") || sky.equals("8")){
                    // 구름많음
                    runOnUiThread(() -> icon.setImageResource(R.drawable.cloud_color));
                }
                if(sky.equals("9") || sky.equals("10")) {
                    // 흐림
                    runOnUiThread(() -> icon.setImageResource(R.drawable.cloud_color));
                }
            }else{
                if(rain.equals("1")){
                    // 비
                    runOnUiThread(() -> icon.setImageResource(R.drawable.rain_color));
                }
                if(rain.equals("2")){
                    // 진눈개비
                    runOnUiThread(() -> icon.setImageResource(R.drawable.rain_color));
                }
                if(rain.equals("3")){
                    // 눈
                    runOnUiThread(() -> icon.setImageResource(R.drawable.snow_color));
                }
            }


            Log.i(TAG,"현재 날씨 : " + data + " ℃");
            runOnUiThread(() -> today_temp.setText(data+ " ℃"));

            Log.i(TAG,"내일 최저/최고 : " + TM_min + " ℃ / " + TM_max + " ℃");
            runOnUiThread(() -> tomorrow_temp.setText(TM_min + " ℃ / " + TM_max + " ℃"));

            Log.i(TAG,"모레 최저/최고 : " + NTM_min + " ℃ / "+ NTM_max + " ℃");
            runOnUiThread(() -> next_tomorrow_temp.setText(NTM_min + " ℃ / "+ NTM_max + " ℃"));



        }
    }

    // 현재 날씨
    public String getXmlData() {
        StringBuffer buffer = new StringBuffer();
        String today = today();
        String time = time();
        String key = "http://apis.data.go.kr/1360000/VilageFcstInfoService/getUltraSrtNcst?serviceKey=YXsM3Qh%2FJr8FVZdMZDqSOlosBCDFmdxqGACs6BCxXIfowyIig7ftX59UngDgR%2FVktpkhOJee84KB%2BbXrvaS1QA%3D%3D"
                + "&base_date="
                + today
                + "&base_time="
                + time
                + "&numOfRows=150&nx=61&ny=130&_type=xml";

        List<String> value = new ArrayList<>();

        try{
            URL url = new URL(key);
            InputStream inputStream = url.openStream();

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = factory.newPullParser();
            parser.setInput(new InputStreamReader(inputStream, "UTF-8"));

            String tag;
            parser.next();

            int eventType = parser.getEventType();

            while(eventType != XmlPullParser.END_DOCUMENT){
                switch (eventType){

                    case XmlPullParser.START_TAG:
                        tag = parser.getName();

                        if(tag.equals("item"));

                        // obsrValue값 출력
                        else if(tag.equals("obsrValue")){
                            parser.next();
                            value.add(parser.getText());    // obsrValue값 배열로 넣기
                        }
                        break;

                    case XmlPullParser.TEXT:
                        break;

                    case  XmlPullParser.END_TAG:
                        tag = parser.getName();
                        if (tag.equals("item")){
                            buffer.append("");
                        }
                        break;
                }
                eventType = parser.next();
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return value.get(3);
    }

    // 동네예보 : 현재 하늘
    // SKY : 0~5(맑음), 6~8(구름많음), 9~10(흐림)
    public String getXmlSky(){
        StringBuffer buffer = new StringBuffer();
        String today = today();
        String time = time();
        String key = "http://apis.data.go.kr/1360000/VilageFcstInfoService/getVilageFcst?serviceKey=YXsM3Qh%2FJr8FVZdMZDqSOlosBCDFmdxqGACs6BCxXIfowyIig7ftX59UngDgR%2FVktpkhOJee84KB%2BbXrvaS1QA%3D%3D"
                + "&base_date="
                + today
                + "&base_time=1400"
                //+ time
                +"&numOfRows=150&nx=61&ny=130&_type=xml";

        List<String> num = new ArrayList<>();

        try{
            URL url = new URL(key);
            InputStream inputStream = url.openStream();

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = factory.newPullParser();
            parser.setInput(new InputStreamReader(inputStream, "UTF-8"));

            String tag;
            parser.next();

            int eventType = parser.getEventType();

            while(eventType != XmlPullParser.END_DOCUMENT){
                switch (eventType){

                    case XmlPullParser.START_TAG:
                        tag = parser.getName();

                        if(tag.equals("item"));

                        else if(tag.equals("fcstValue")){
                            parser.next();
                            num.add(parser.getText());
                        }
                        break;

                    case XmlPullParser.TEXT:
                        break;

                    case  XmlPullParser.END_TAG:
                        tag = parser.getName();
                        if (tag.equals("item")){
                            buffer.append("");
                        }
                        break;
                }
                eventType = parser.next();
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return num.get(5);
    }

    // 초단기 실황, 비
    // 비없음 : 0, 비 : 1, 진눈개비 : 2, 눈 : 3
    public String getXmlRain(){
        StringBuffer buffer = new StringBuffer();
        String today = today();
        String time = time();
        String key = "http://apis.data.go.kr/1360000/VilageFcstInfoService/getUltraSrtNcst?serviceKey=YXsM3Qh%2FJr8FVZdMZDqSOlosBCDFmdxqGACs6BCxXIfowyIig7ftX59UngDgR%2FVktpkhOJee84KB%2BbXrvaS1QA%3D%3D"
                + "&base_date="
                + today
                + "&base_time="
                + time
                + "&numOfRows=150&nx=61&ny=130&_type=xml";

        List<String> num = new ArrayList<>();

        try{
            URL url = new URL(key);
            InputStream inputStream = url.openStream();

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = factory.newPullParser();
            parser.setInput(new InputStreamReader(inputStream, "UTF-8"));

            String tag;
            parser.next();

            int eventType = parser.getEventType();

            while(eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType){

                    case XmlPullParser.START_TAG:
                        tag = parser.getName();

                        if(tag.equals("item"));

                        else if(tag.equals("obsrValue")){
                            parser.next();
                            num.add(parser.getText());
                        }
                        break;

                    case XmlPullParser.TEXT:
                        break;

                    case  XmlPullParser.END_TAG:
                        tag = parser.getName();
                        if (tag.equals("item")){
                            buffer.append("");
                        }
                        break;
                }
                eventType = parser.next();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return num.get(0);
    }

    // 동네예보, 내일 최저기온
    public String getXmlTMN(){
        StringBuffer buffer = new StringBuffer();
        String today = today();
        String time = time();
        String key = "http://apis.data.go.kr/1360000/VilageFcstInfoService/getVilageFcst?serviceKey=YXsM3Qh%2FJr8FVZdMZDqSOlosBCDFmdxqGACs6BCxXIfowyIig7ftX59UngDgR%2FVktpkhOJee84KB%2BbXrvaS1QA%3D%3D"
                + "&base_date="
                + today
                + "&base_time=1400"
                //+ time
                +"&numOfRows=150&nx=61&ny=130&_type=xml";

        List<String> num = new ArrayList<>();

        try{
            URL url = new URL(key);
            InputStream inputStream = url.openStream();

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = factory.newPullParser();
            parser.setInput(new InputStreamReader(inputStream, "UTF-8"));

            String tag;
            parser.next();

            int eventType = parser.getEventType();

            while(eventType != XmlPullParser.END_DOCUMENT){
                switch (eventType){

                    case XmlPullParser.START_TAG:
                        tag = parser.getName();

                        if(tag.equals("item"));

                        else if(tag.equals("fcstValue")){
                            parser.next();
                            num.add(parser.getText());
                        }
                        break;

                    case XmlPullParser.TEXT:
                        break;

                    case  XmlPullParser.END_TAG:
                        tag = parser.getName();
                        if (tag.equals("item")){
                            buffer.append("");
                        }
                        break;
                }
                eventType = parser.next();
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return num.get(47);
    }

    // 동네예보, 내일 최고기온
    public String getXmlTMX(){
        StringBuffer buffer = new StringBuffer();
        String today = today();
        String time = time();
        String key = "http://apis.data.go.kr/1360000/VilageFcstInfoService/getVilageFcst?serviceKey=YXsM3Qh%2FJr8FVZdMZDqSOlosBCDFmdxqGACs6BCxXIfowyIig7ftX59UngDgR%2FVktpkhOJee84KB%2BbXrvaS1QA%3D%3D"
                + "&base_date="
                + today
                + "&base_time=1400"
                //+ time
                +"&numOfRows=150&nx=61&ny=130&_type=xml";

        List<String> num = new ArrayList<>();

        try{
            URL url = new URL(key);
            InputStream inputStream = url.openStream();

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = factory.newPullParser();
            parser.setInput(new InputStreamReader(inputStream, "UTF-8"));

            String tag;
            parser.next();

            int eventType = parser.getEventType();

            while(eventType != XmlPullParser.END_DOCUMENT){
                switch (eventType){

                    case XmlPullParser.START_TAG:
                        tag = parser.getName();

                        if(tag.equals("item"));

                        else if(tag.equals("fcstValue")){
                            parser.next();
                            num.add(parser.getText());
                        }
                        break;

                    case XmlPullParser.TEXT:
                        break;

                    case  XmlPullParser.END_TAG:
                        tag = parser.getName();
                        if (tag.equals("item")){
                            buffer.append("");
                        }
                        break;
                }
                eventType = parser.next();
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return num.get(77);
    }

    // 동네예보, 모레 최저기온
    public String getXmlTMN_2(){
        StringBuffer buffer = new StringBuffer();
        String today = today();
        String time = time();
        String key = "http://apis.data.go.kr/1360000/VilageFcstInfoService/getVilageFcst?serviceKey=YXsM3Qh%2FJr8FVZdMZDqSOlosBCDFmdxqGACs6BCxXIfowyIig7ftX59UngDgR%2FVktpkhOJee84KB%2BbXrvaS1QA%3D%3D"
                + "&base_date="
                + today
                + "&base_time=1400"
                //+ time
                +"&numOfRows=200&nx=61&ny=130&_type=xml";

        List<String> num = new ArrayList<>();

        try{
            URL url = new URL(key);
            InputStream inputStream = url.openStream();

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = factory.newPullParser();
            parser.setInput(new InputStreamReader(inputStream, "UTF-8"));

            String tag;
            parser.next();

            int eventType = parser.getEventType();

            while(eventType != XmlPullParser.END_DOCUMENT){
                switch (eventType){

                    case XmlPullParser.START_TAG:
                        tag = parser.getName();

                        if(tag.equals("item"));

                        else if(tag.equals("fcstValue")){
                            parser.next();
                            num.add(parser.getText());
                        }
                        break;

                    case XmlPullParser.TEXT:
                        break;

                    case  XmlPullParser.END_TAG:
                        tag = parser.getName();
                        if (tag.equals("item")){
                            buffer.append("");
                        }
                        break;
                }
                eventType = parser.next();
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return num.get(129);

    }

    // 동네예보, 모레 최고기온
    public String getXmlTMX_2(){
        StringBuffer buffer = new StringBuffer();
        String today = today();
        String time = time();
        String key = "http://apis.data.go.kr/1360000/VilageFcstInfoService/getVilageFcst?serviceKey=YXsM3Qh%2FJr8FVZdMZDqSOlosBCDFmdxqGACs6BCxXIfowyIig7ftX59UngDgR%2FVktpkhOJee84KB%2BbXrvaS1QA%3D%3D"
                + "&base_date="
                + today
                + "&base_time=1400"
                //+ time
                +"&numOfRows=200&nx=61&ny=130&_type=xml";

        List<String> num = new ArrayList<>();

        try{
            URL url = new URL(key);
            InputStream inputStream = url.openStream();

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = factory.newPullParser();
            parser.setInput(new InputStreamReader(inputStream, "UTF-8"));

            String tag;
            parser.next();

            int eventType = parser.getEventType();

            while(eventType != XmlPullParser.END_DOCUMENT){
                switch (eventType){

                    case XmlPullParser.START_TAG:
                        tag = parser.getName();

                        if(tag.equals("item"));

                        else if(tag.equals("fcstValue")){
                            parser.next();
                            num.add(parser.getText());
                        }
                        break;

                    case XmlPullParser.TEXT:
                        break;

                    case  XmlPullParser.END_TAG:
                        tag = parser.getName();
                        if (tag.equals("item")){
                            buffer.append("");
                        }
                        break;
                }
                eventType = parser.next();
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return num.get(159);

    }

    // 현재 날짜 가져오기
    public static String today() {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd", Locale.KOREA);
        String currentDate = format.format(date);

        return currentDate;
    }

    // 현재 시각 가져오기
    public static String time() {
        SimpleDateFormat format = new SimpleDateFormat("HH00", Locale.KOREA);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR_OF_DAY, -1);

        String currentDate = format.format(calendar.getTime());

        return currentDate;
    }

    // Excel 파일 불러오기
    /*
    public void OpenExcel() throws IOException {
       FileInputStream file = new FileInputStream("C:\\Users\\Public\\Portfolio\\WeatherApp\\app\\src\\main\\assets\\weather_xy.xlsx");
        XSSFWorkbook workbook = new XSSFWorkbook(file);
        int rowIndex = 0;
        int columnIndex = 0;

        XSSFSheet sheet = workbook.getSheetAt(0);

        int rows = sheet.getPhysicalNumberOfRows();
        for(rowIndex = 1; rowIndex<rows; rowIndex++){
            XSSFRow row = sheet.getRow(rowIndex);
            if(row != null){
                int cells = row.getPhysicalNumberOfCells();
                for(columnIndex=0; columnIndex<=cells; columnIndex++){
                    XSSFCell cell = row.getCell(columnIndex);

                    String value = "";
                    if(cell == null){
                        continue;
                    }else{
                        switch (cell.getCellType()){
                            case FORMULA:
                                value = cell.getCellFormula();
                                break;

                            case NUMERIC:
                                value = cell.getNumericCellValue()+"";
                                break;

                            case STRING:
                                value = cell.getStringCellValue()+"";
                                break;

                            case BLANK:
                                value = cell.getBooleanCellValue()+"";
                                break;

                            case ERROR:
                                value = cell.getErrorCellValue()+"";
                                break;

                        }
                    }
                }
            }
        }
    }

     */

    // spinner 지역 선택 후, 현재 지역으로 setText
    @Override
    public void onPause() {
        super.onPause();

        try {
            FileInputStream inputStream = openFileInput("location");
            byte[] txt = new byte[100];
            inputStream.read(txt);
            String str = new String(txt);
            location.setText(str);
            inputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}