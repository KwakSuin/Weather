package kwaksuin.portfolio.weatherapp;

import android.os.AsyncTask;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


// UI 작업을 위해 별도의 스레드로 만들어져서 백그라운드에서 실행되는 태스크
public class AsyncTask_weather extends AsyncTask<Integer, Integer, Void> {
    static String result = "";
    Parsing parsing;
    String line;
    String nowday;
    String nowtime;

    @Override
    protected Void doInBackground(Integer... integers) {
        //파싱
        parsing.chodangi();


        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }

    //오늘 날짜 가져오기 (api 변수에 사용합니다.)
    public static String doYearMonthDay() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd", Locale.KOREA);
        Date date = new Date();
        String currentDate = formatter.format(date);
        return currentDate;
    }

    //현재 시간 가져오기 (api 변수에 사용합니다.)
    public static String doTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("HH00", Locale.KOREA);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR_OF_DAY,-1);

        String currentDate = formatter.format(calendar.getTime());

        return currentDate;
    }

    public static String doyes(){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd", Locale.KOREA);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE,-1);  // 오늘 날짜에서 하루를 뺌.
        String date = formatter.format(calendar.getTime());
        return date;
    }

    public static String today_1(){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd", Locale.KOREA);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, +1);  // 내일.
        String date = formatter.format(calendar.getTime());
        return date;
    }

    public static String today_2(){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd", Locale.KOREA);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, +2);  //
        String date = formatter.format(calendar.getTime());
        return date;
    }

    public static String today_3(){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd", Locale.KOREA);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, +3);
        String date = formatter.format(calendar.getTime());
        return date;
    }

    public static String today_4(){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd", Locale.KOREA);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, +4);
        String date = formatter.format(calendar.getTime());
        return date;
    }

    public static String today_5(){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd", Locale.KOREA);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, +5);
        String date = formatter.format(calendar.getTime());
        return date;
    }
}