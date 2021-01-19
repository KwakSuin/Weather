package kwaksuin.portfolio.weatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.lang.String;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;


public class MainActivity extends AppCompatActivity {
    LinearLayout location_layout;
    ImageView location_img;
    TextView location;

    TextView today_temp;            // 오늘 날씨
    TextView tomorrow_temp;         // 내일 날씨
    TextView next_tomorrow_temp;    // 모레 날씨

    String data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        today_temp = findViewById(R.id.today_temp);

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
        public TempThread(){
        }

        public void run() {
            data = getXmlData();

            runOnUiThread(() -> today_temp.setText(data+ " ℃"));
        }
    }

    public String getXmlData(){
        StringBuffer buffer = new StringBuffer();
        String today = today();
        String time = time();
        String key = "http://apis.data.go.kr/1360000/VilageFcstInfoService/getUltraSrtNcst?serviceKey=YXsM3Qh%2FJr8FVZdMZDqSOlosBCDFmdxqGACs6BCxXIfowyIig7ftX59UngDgR%2FVktpkhOJee84KB%2BbXrvaS1QA%3D%3D"
                + "&base_date="
                + today
                + "&base_time="
                + time
                +"&numOfRows=150&nx=61&ny=130&_type=xml";

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

                        // 4번째 값 출력
                        else if(tag.equals("obsrValue")){
                            parser.next();
                            value.add(parser.getText());
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

    // 현재 날짜 가져오기
    public static String today() {
        Date date = new Date();

        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd", Locale.KOREA);
        String currentDate = formatter.format(date);

        return currentDate;
    }

    // 현재 시각 가져오기
    public static String time() {
        SimpleDateFormat formatter = new SimpleDateFormat("HH00", Locale.KOREA);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR_OF_DAY, -1);

        String currentDate = formatter.format(calendar.getTime());

        return currentDate;
    }

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