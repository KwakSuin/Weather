package kwaksuin.portfolio.weatherapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.io.IOException;


// 일단 경기도, 서울만 데이터 저장
public class Location extends AppCompatActivity {
    ArrayAdapter<CharSequence> sido_adapter;
    ArrayAdapter<CharSequence> sigungu_adapter;
    Spinner sido;
    Spinner sigungu;

    String check_sido ="";
    String check_sigungu ="";

    String sido_str;
    String sigungu_str;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        sido = findViewById(R.id.sido_spinner);
        sigungu = findViewById(R.id.sigungu_spinner);


        // 스피너 뷰 어댑터 생성
        sido_adapter = ArrayAdapter.createFromResource(this,R.array.spinner_sido, android.R.layout.simple_spinner_dropdown_item);
        sido_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sido.setAdapter(sido_adapter);

        sido.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // custom
                ((TextView) parent.getChildAt(0)).setTextColor(Color.BLUE);
                ((TextView) parent.getChildAt(0)).setTextSize(15);
                parent.getChildAt(0).setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

                if (parent.getItemAtPosition(position).equals("선택해주세요")) {
                    sigungu_adapter = ArrayAdapter.createFromResource(Location.this, R.array.spinner_string, android.R.layout.simple_spinner_item);
                    sigungu_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    sigungu.setAdapter(sigungu_adapter);

                } else if (parent.getItemAtPosition(position).equals("서울특별시")) {
                    check_sido = "서울특별시";
                    sigungu_adapter = ArrayAdapter.createFromResource(Location.this, R.array.spinner_seoul, android.R.layout.simple_spinner_item);
                    sigungu_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    sigungu.setAdapter(sigungu_adapter);

                    sigungu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            ((TextView) parent.getChildAt(0)).setTextColor(Color.BLUE);
                            ((TextView) parent.getChildAt(0)).setTextSize(15);
                            parent.getChildAt(0).setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

                            if (parent.getItemAtPosition(position).equals("선택해주세요")) {
                                check_sigungu = null;
                            } else {
                                // 문자열 변환
                                String item = parent.getItemAtPosition(position).toString();
                                check_sigungu = item;
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                        }

                    });

                } else if (parent.getItemAtPosition(position).equals("경기도")) {
                    check_sido = "경기도";
                    sigungu_adapter = ArrayAdapter.createFromResource(Location.this, R.array.spinner_gyeongi, android.R.layout.simple_spinner_item);
                    sigungu_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    sigungu.setAdapter(sigungu_adapter);

                    sigungu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            ((TextView) parent.getChildAt(0)).setTextColor(Color.BLUE);
                            ((TextView) parent.getChildAt(0)).setTextSize(15);
                            parent.getChildAt(0).setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                            if (parent.getItemAtPosition(position).equals("선택해주세요")){
                                check_sigungu = null;
                            } else {
                                String item = parent.getItemAtPosition(position).toString();
                                check_sigungu = item;
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                } else {
                    String item = parent.getItemAtPosition(position).toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        // 선택한 시/도 toString()
        sido_str = sido.getSelectedItem().toString();


        sigungu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) parent.getChildAt(0)).setTextColor(Color.BLUE);
                ((TextView) parent.getChildAt(0)).setTextSize(15);
                parent.getChildAt(0).setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            }

            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Button check_btn = findViewById(R.id.check_btn);
        check_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(check_sido == "" || check_sigungu == "" || check_sido == null || check_sigungu == null){
                    LayoutInflater inflater = getLayoutInflater();
                    View layout = inflater.inflate(R.layout.toast_layout, findViewById(R.id.toast_layout));

                    TextView check_msg = layout.findViewById(R.id.check_msg);
                    Toast toast = new Toast(getApplication());

                    check_msg.setText("모두 선택해주세요.");
                    check_msg.setTextSize(15);
                    check_msg.setTextColor(Color.BLACK);

                    toast.setGravity(Gravity.BOTTOM,0,120);
                    toast.setDuration(Toast.LENGTH_SHORT);
                    toast.setView(layout);
                    toast.show();


                } else {

                    try{
                        FileOutputStream outputStream = openFileOutput("location", MODE_PRIVATE);
                        String a = "";
                        a = check_sido + check_sigungu;
                        outputStream.write(a.getBytes());
                        outputStream.close();
                        finish();

                    } catch (IOException e){

                    }
                }
            }
        });

        // 선택한 시/군/구 toString()
        sigungu_str = sigungu.getSelectedItem().toString();

    }
}