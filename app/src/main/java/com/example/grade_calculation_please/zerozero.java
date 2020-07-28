package com.example.grade_calculation_please;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.Buffer;
import java.util.Locale;

public class zerozero extends AppCompatActivity {


    double totalKorgrade = 0; // 4.5만점 총평점
    double totalEnggrade = 0; // 4.0만점 총평점
    double totalcredit = 0; // 총 학점
    double totalKorgradesum = 0; // 4.5만점 총평점 분자
    double totalEnggradesum = 0; // 4.0만점 총평점 분자
    double totalcreditShow = 0; // 패논패 제외 실질적인 총 학점

    double totalKorgradeM = 0; // 4.5만점 전공평점
    double totalEnggradeM = 0; // 4.0만점 전공평점
    double totalcreditM = 0; // 전공 학점
    double totalKorgradesumM = 0; //4.5만점 전공평점 분자
    double totalEnggradesumM = 0; //4.0만점 전공평점 분자
    int sheetnum = 0;

    private Spinner oneonecredit1, oneonecredit2, oneonecredit3, oneonecredit4, oneonecredit5, oneonecredit6, oneonecredit7, oneonecredit8, oneonecredit9, oneonecredit10;
    private Spinner oneonegrade1, oneonegrade2, oneonegrade3, oneonegrade4, oneonegrade5, oneonegrade6, oneonegrade7, oneonegrade8, oneonegrade9, oneonegrade10;
    EditText oneonesubject1, oneonesubject2, oneonesubject3, oneonesubject4, oneonesubject5, oneonesubject6, oneonesubject7, oneonesubject8, oneonesubject9, oneonesubject10;
    private Button btn, intentbtn; //버튼 누르면 학점 계산
    CheckBox[] oneonecheck = new CheckBox[10]; // 전공인지 아닌지 체크

    String Name = null; // Name 안에는 "몇학년 몇학기"인지 담긴다.
    int[] check_first = new int[9]; // 버튼을 처음 누른것인지 체크한다
    Context context = null;
    File file = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zerozero);
        Intent intent = getIntent();
        Name = intent.getExtras().getString("name"); //메인에서 몇학년 몇학기 눌렀는지 가져온다.
        TextView Naming = (TextView) findViewById(R.id.naming); //몇학년 몇학기 써있는 상단의 textview
        Naming.setText(Name); // 그 텍스트뷰에 몇학년 몇학기인지 쓴다.
        context = getApplicationContext();

        oneonecredit1 = (Spinner) findViewById(R.id.oneonecredit1);
        oneonecredit2 = (Spinner) findViewById(R.id.oneonecredit2);
        oneonecredit3 = (Spinner) findViewById(R.id.oneonecredit3);
        oneonecredit4 = (Spinner) findViewById(R.id.oneonecredit4);
        oneonecredit5 = (Spinner) findViewById(R.id.oneonecredit5);
        oneonecredit6 = (Spinner) findViewById(R.id.oneonecredit6);
        oneonecredit7 = (Spinner) findViewById(R.id.oneonecredit7);
        oneonecredit8 = (Spinner) findViewById(R.id.oneonecredit8);
        oneonecredit9 = (Spinner) findViewById(R.id.oneonecredit9);
        oneonecredit10 = (Spinner) findViewById(R.id.oneonecredit10); // 몇학점인지

        oneonegrade1 = (Spinner) findViewById(R.id.oneonegrade1);
        oneonegrade2 = (Spinner) findViewById(R.id.oneonegrade2);
        oneonegrade3 = (Spinner) findViewById(R.id.oneonegrade3);
        oneonegrade4 = (Spinner) findViewById(R.id.oneonegrade4);
        oneonegrade5 = (Spinner) findViewById(R.id.oneonegrade5);
        oneonegrade6 = (Spinner) findViewById(R.id.oneonegrade6);
        oneonegrade7 = (Spinner) findViewById(R.id.oneonegrade7);
        oneonegrade8 = (Spinner) findViewById(R.id.oneonegrade8);
        oneonegrade9 = (Spinner) findViewById(R.id.oneonegrade9);
        oneonegrade10 = (Spinner) findViewById(R.id.oneonegrade10); // 각각 평점이 어떻게 되는지

        oneonesubject1 = (EditText) findViewById(R.id.oneonesubject1);
        oneonesubject2 = (EditText) findViewById(R.id.oneonesubject2);
        oneonesubject3 = (EditText) findViewById(R.id.oneonesubject3);
        oneonesubject4 = (EditText) findViewById(R.id.oneonesubject4);
        oneonesubject5 = (EditText) findViewById(R.id.oneonesubject5);
        oneonesubject6 = (EditText) findViewById(R.id.oneonesubject6);
        oneonesubject7 = (EditText) findViewById(R.id.oneonesubject7);
        oneonesubject8 = (EditText) findViewById(R.id.oneonesubject8);
        oneonesubject9 = (EditText) findViewById(R.id.oneonesubject9);
        oneonesubject10 = (EditText) findViewById(R.id.oneonesubject10); // 과목이름은 어떻게 되는지

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.credit, android.R.layout.simple_spinner_dropdown_item);
        oneonecredit1.setAdapter(adapter);
        oneonecredit2.setAdapter(adapter);
        oneonecredit3.setAdapter(adapter);
        oneonecredit4.setAdapter(adapter);
        oneonecredit5.setAdapter(adapter);
        oneonecredit6.setAdapter(adapter);
        oneonecredit7.setAdapter(adapter);
        oneonecredit8.setAdapter(adapter);
        oneonecredit9.setAdapter(adapter);
        oneonecredit10.setAdapter(adapter); // 스피너 쓰기 위한 어답터(1,2,3학점인지)

        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.grade, android.R.layout.simple_spinner_dropdown_item);
        oneonegrade1.setAdapter(adapter1);
        oneonegrade2.setAdapter(adapter1);
        oneonegrade3.setAdapter(adapter1);
        oneonegrade4.setAdapter(adapter1);
        oneonegrade5.setAdapter(adapter1);
        oneonegrade6.setAdapter(adapter1);
        oneonegrade7.setAdapter(adapter1);
        oneonegrade8.setAdapter(adapter1);
        oneonegrade9.setAdapter(adapter1);
        oneonegrade10.setAdapter(adapter1); // 스피너 쓰기 위한 어답터(A+부터 패논패)

        btn = findViewById(R.id.calc);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String[] oneonesubject = new String[10];
                oneonesubject[0] = oneonesubject1.getText().toString();
                oneonesubject[1] = oneonesubject2.getText().toString();
                oneonesubject[2] = oneonesubject3.getText().toString();
                oneonesubject[3] = oneonesubject4.getText().toString();
                oneonesubject[4] = oneonesubject5.getText().toString();
                oneonesubject[5] = oneonesubject6.getText().toString();
                oneonesubject[6] = oneonesubject7.getText().toString();
                oneonesubject[7] = oneonesubject8.getText().toString();
                oneonesubject[8] = oneonesubject9.getText().toString();
                oneonesubject[9] = oneonesubject10.getText().toString();

                final double[] oneonecredit = new double[10];
                oneonecredit[0] = Double.parseDouble(oneonecredit1.getSelectedItem().toString());
                oneonecredit[1] = Double.parseDouble(oneonecredit2.getSelectedItem().toString());
                oneonecredit[2] = Double.parseDouble(oneonecredit3.getSelectedItem().toString());
                oneonecredit[3] = Double.parseDouble(oneonecredit4.getSelectedItem().toString());
                oneonecredit[4] = Double.parseDouble(oneonecredit5.getSelectedItem().toString());
                oneonecredit[5] = Double.parseDouble(oneonecredit6.getSelectedItem().toString());
                oneonecredit[6] = Double.parseDouble(oneonecredit7.getSelectedItem().toString());
                oneonecredit[7] = Double.parseDouble(oneonecredit8.getSelectedItem().toString());
                oneonecredit[8] = Double.parseDouble(oneonecredit9.getSelectedItem().toString());
                oneonecredit[9] = Double.parseDouble(oneonecredit10.getSelectedItem().toString());

                final String[] oneonegrade = new String[10];
                oneonegrade[0] = String.format(oneonegrade1.getSelectedItem().toString());
                oneonegrade[1] = String.format(oneonegrade2.getSelectedItem().toString());
                oneonegrade[2] = String.format(oneonegrade3.getSelectedItem().toString());
                oneonegrade[3] = String.format(oneonegrade4.getSelectedItem().toString());
                oneonegrade[4] = String.format(oneonegrade5.getSelectedItem().toString());
                oneonegrade[5] = String.format(oneonegrade6.getSelectedItem().toString());
                oneonegrade[6] = String.format(oneonegrade7.getSelectedItem().toString());
                oneonegrade[7] = String.format(oneonegrade8.getSelectedItem().toString());
                oneonegrade[8] = String.format(oneonegrade9.getSelectedItem().toString());
                oneonegrade[9] = String.format(oneonegrade10.getSelectedItem().toString());

                oneonecheck[0] = findViewById(R.id.oneonecheck1);
                oneonecheck[1] = findViewById(R.id.oneonecheck2);
                oneonecheck[2] = findViewById(R.id.oneonecheck3);
                oneonecheck[3] = findViewById(R.id.oneonecheck4);
                oneonecheck[4] = findViewById(R.id.oneonecheck5);
                oneonecheck[5] = findViewById(R.id.oneonecheck6);
                oneonecheck[6] = findViewById(R.id.oneonecheck7);
                oneonecheck[7] = findViewById(R.id.oneonecheck8);
                oneonecheck[8] = findViewById(R.id.oneonecheck9);
                oneonecheck[9] = findViewById(R.id.oneonecheck10);

                double[] oneonegradeKor = new double[10]; //각각 평점 어떻게 되는지
                double[] oneonegradeEng = new double[10]; //각각 평점 어떻게 되는지
                double[] oneonecreditnum = new double[10]; //각각 학점이 어떻게 되는지


                for (int i = 0; i < 10; i++) {
                    if (oneonesubject[i].getBytes().length > 0) //과목에 입력되어 있을 때만 실행
                    {
                        if (oneonecredit[i] == 3) {
                            oneonecreditnum[i] = 3;
                        }
                        if (oneonecredit[i] == 2) {
                            oneonecreditnum[i] = 2;
                        }
                        if (oneonecredit[i] == 1) {
                            oneonecreditnum[i] = 1;
                        }
                        if (oneonecredit[i] == 0) {
                            oneonecreditnum[i] = 0;
                        }
                        //credit을 문자열에서 숫자로 전환


                        if (oneonegrade[i].equals("A+")) {
                            oneonegradeKor[i] = 4.5;
                            oneonegradeEng[i] = 4;
                        }
                        if (oneonegrade[i].equals("A0")) {
                            oneonegradeKor[i] = 4;
                            oneonegradeEng[i] = 4;
                        }
                        if (oneonegrade[i].equals("B+")) {
                            oneonegradeKor[i] = 3.5;
                            oneonegradeEng[i] = 3;
                        }
                        if (oneonegrade[i].equals("B0")) {
                            oneonegradeKor[i] = 3;
                            oneonegradeEng[i] = 3;
                        }
                        if (oneonegrade[i].equals("C+")) {
                            oneonegradeKor[i] = 2.5;
                            oneonegradeEng[i] = 2;
                        }
                        if (oneonegrade[i].equals("C0")) {
                            oneonegradeKor[i] = 2;
                            oneonegradeEng[i] = 2;
                        }
                        if (oneonegrade[i].equals("D+")) {
                            oneonegradeKor[i] = 1.5;
                            oneonegradeEng[i] = 1;
                        }
                        if (oneonegrade[i].equals("D0")) {
                            oneonegradeKor[i] = 1;
                            oneonegradeEng[i] = 1;
                        }
                        if (oneonegrade[i].equals("F")) {
                            oneonegradeKor[i] = 0;
                            oneonegradeEng[i] = 0;
                        }
                        if (oneonegrade[i].equals("P") || oneonegrade[i].equals("NP")) {
                            oneonegradeKor[i] = 0;
                            oneonegradeEng[i] = 0;
                        } //grade를 문자열에서 숫자로 전환 ex)A->4.0


                    }
                }

                totalcredit = 0; // 총학점
                totalcreditShow = 0; // 패논패과목 제외 실제 분모에 들어가는 학점
                totalKorgradesum = 0; //4.5만점 분자계산
                totalEnggradesum = 0; //4.0만점 분자계산
//                계산할때마다 전부 0으로 초기화해서 다시 계산한다.

                for (int j = 0; j < 10; j++) {
                    if (oneonesubject[j].getBytes().length <= 0) {
                    } else if (oneonegrade[j].equals("P") || oneonegrade[j].equals("NP")) {
                    } else {
                        totalcreditShow += oneonecreditnum[j];
                    } // 총이수학 점 계산 (분모용)
                    totalcredit += oneonecreditnum[j]; //총 이수학점 계산
                    totalKorgradesum += oneonegradeKor[j] * oneonecreditnum[j]; // 4.5만점 총평점의 분자
                    totalEnggradesum += Double.valueOf(oneonegradeEng[j]) * Double.valueOf(oneonecreditnum[j]); // 4.0만점 총평점의 분자

                }

                totalcreditM = 0; //전공 총학점
                totalKorgradesumM = 0;//전공 4.5만점 분자계산
                totalEnggradesumM = 0;//전공 4.0만점 분자계산

                for (int j = 0; j < 10; j++) {
                    if (oneonecheck[j].isChecked()) {
                        totalcreditM += oneonecreditnum[j]; //총 이수학점 계산
                        totalKorgradesumM += oneonegradeKor[j] * oneonecreditnum[j]; // 4.5만점 전공평점의 분자
                        totalEnggradesumM += (oneonegradeEng[j]) * (oneonecreditnum[j]); // 4.0만점 전공평점의 분자
                    }
                }
                totalKorgrade = totalKorgradesum / totalcreditShow; //4.5 만점 평점
                totalEnggrade = totalEnggradesum / totalcreditShow; //4.0 만점 평점
                totalKorgradeM = totalKorgradesumM / totalcreditM; //전공평점 4.5 만점
                totalEnggradeM = totalEnggradesumM / totalcreditM; //전공평점 4.0 만점


                TextView creditview1 = findViewById(R.id.creditview1); //취득학점 표시
                TextView Korgradeview1 = findViewById(R.id.Korgradeview1); //4.5 총평점 표시
                TextView Enggradeview1 = findViewById(R.id.Enggradeview1); //4.0 총평점 표시
                TextView Majorgradeview1 = findViewById(R.id.Majorgradeview1Kor); //4.5 전공평점 표시
                TextView Majorgradeview2 = findViewById(R.id.Majorgradeview1Eng); //4.0 전공평점 표시


                creditview1.setText(String.format(Locale.getDefault(), "%.0f", totalcreditShow));
                Korgradeview1.setText(String.format(Locale.getDefault(), "%.3f", totalKorgrade));
                Enggradeview1.setText(String.format(Locale.getDefault(), "%.3f", totalEnggrade));
                Majorgradeview1.setText(String.format(Locale.getDefault(), "%.3f", totalKorgradeM));
                Majorgradeview2.setText(String.format(Locale.getDefault(), "%.3f", totalEnggradeM));

                saveExcel();

            }
        });
        intentbtn = findViewById(R.id.intentbtn);

        intentbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(zerozero.this, MainActivity.class);

                intent.putExtra("oneonetKgs", totalKorgradesum);
                intent.putExtra("oneonetEgs", totalEnggradesum);
                intent.putExtra("oneonetKgsM", totalKorgradesumM);
                intent.putExtra("oneonetEgsM", totalEnggradesumM);
                intent.putExtra("oneonerealcr", totalcreditShow);
                startActivity(intent);


            }
        });

    }

    private void saveExcel() {
        Workbook workbook = new HSSFWorkbook();
        Sheet sheet1 = workbook.createSheet("1학년 1학기");
        Sheet sheet2 = workbook.createSheet("1학년 2학기");
        Sheet sheet3 = workbook.createSheet("2학년 1학기");
        Sheet sheet4 = workbook.createSheet("2학년 2학기");
        Sheet sheet5 = workbook.createSheet("3학년 1학기");
        Sheet sheet6 = workbook.createSheet("3학년 2학기");
        Sheet sheet7 = workbook.createSheet("4학년 1학기");
        Sheet sheet8 = workbook.createSheet("4학년 2학기");
        Sheet sheet9 = workbook.createSheet("기타 학기");

        Row row;
        Cell cell;

        function1();
        switch (sheetnum) {
            case 1:
            row = sheet1.createRow(0);
            // 1번 셀은 빈칸
            cell = row.createCell(0);
            // 2번 셀은 과목이름
            cell = row.createCell(1);
            cell.setCellValue("과목 이름");
            // 3번 셀은 과목 학점
            cell = row.createCell(2);
            cell.setCellValue("학점");
            // 4번 셀은 과목 평점
            cell = row.createCell(3);
            cell.setCellValue("평점");
            // 5번 셀은 전공 여부 체크
            cell = row.createCell(4);
            cell.setCellValue("전공");
            for (int i = 0; i < 10; i++) {
                row = sheet1.createRow(i + 1); // 오류
                cell = row.createCell(1);
                cell.setCellValue("i+1번째 과목이름");
                cell = row.createCell(2);
                cell.setCellValue("i+1번째 학점");
                cell = row.createCell(3);
                cell.setCellValue("i+1번째 평점");
                cell = row.createCell(4);
                cell.setCellValue("i+1번째 전공");
            }
            break;
            case 2:
            row = sheet2.createRow(0); // 오류
            // 1번 셀은 빈칸
            cell = row.createCell(0);
            // 2번 셀은 과목이름
            cell = row.createCell(1);
            cell.setCellValue("과목 이름");
            // 3번 셀은 과목 학점
            cell = row.createCell(2);
            cell.setCellValue("학점");
            // 4번 셀은 과목 평점
            cell = row.createCell(3);
            cell.setCellValue("평점");
            // 5번 셀은 전공 여부 체크
            cell = row.createCell(4);
            cell.setCellValue("전공");
            for (int i = 0; i < 10; i++) {
                row = sheet2.createRow(i + 1); // 오류
                cell = row.createCell(1);
                cell.setCellValue("i+1번째 과목이름");
                cell = row.createCell(2);
                cell.setCellValue("i+1번째 학점");
                cell = row.createCell(3);
                cell.setCellValue("i+1번째 평점");
                cell = row.createCell(4);
                cell.setCellValue("i+1번째 전공");
            }
            break;
            case 3:
            row = sheet3.createRow(0); // 오류
            // 1번 셀은 빈칸
            cell = row.createCell(0);
            // 2번 셀은 과목이름
            cell = row.createCell(1);
            cell.setCellValue("과목 이름");
            // 3번 셀은 과목 학점
            cell = row.createCell(2);
            cell.setCellValue("학점");
            // 4번 셀은 과목 평점
            cell = row.createCell(3);
            cell.setCellValue("평점");
            // 5번 셀은 전공 여부 체크
            cell = row.createCell(4);
            cell.setCellValue("전공");
            for (int i = 0; i < 10; i++) {
                row = sheet3.createRow(i + 1); // 오류
                cell = row.createCell(1);
                cell.setCellValue("i+1번째 과목이름");
                cell = row.createCell(2);
                cell.setCellValue("i+1번째 학점");
                cell = row.createCell(3);
                cell.setCellValue("i+1번째 평점");
                cell = row.createCell(4);
                cell.setCellValue("i+1번째 전공");
            }
            break;
            case 4:
            row = sheet4.createRow(0); // 오류
            // 1번 셀은 빈칸
            cell = row.createCell(0);
            // 2번 셀은 과목이름
            cell = row.createCell(1);
            cell.setCellValue("과목 이름");
            // 3번 셀은 과목 학점
            cell = row.createCell(2);
            cell.setCellValue("학점");
            // 4번 셀은 과목 평점
            cell = row.createCell(3);
            cell.setCellValue("평점");
            // 5번 셀은 전공 여부 체크
            cell = row.createCell(4);
            cell.setCellValue("전공");
            for (int i = 0; i < 10; i++) {
                row = sheet4.createRow(i + 1); // 오류
                cell = row.createCell(1);
                cell.setCellValue("i+1번째 과목이름");
                cell = row.createCell(2);
                cell.setCellValue("i+1번째 학점");
                cell = row.createCell(3);
                cell.setCellValue("i+1번째 평점");
                cell = row.createCell(4);
                cell.setCellValue("i+1번째 전공");
            }
            break;
            case 5:
            row = sheet5.createRow(0); // 오류
            // 1번 셀은 빈칸
            cell = row.createCell(0);
            // 2번 셀은 과목이름
            cell = row.createCell(1);
            cell.setCellValue("과목 이름");
            // 3번 셀은 과목 학점
            cell = row.createCell(2);
            cell.setCellValue("학점");
            // 4번 셀은 과목 평점
            cell = row.createCell(3);
            cell.setCellValue("평점");
            // 5번 셀은 전공 여부 체크
            cell = row.createCell(4);
            cell.setCellValue("전공");
            for (int i = 0; i < 10; i++) {
                row = sheet5.createRow(i + 1); // 오류
                cell = row.createCell(1);
                cell.setCellValue("i+1번째 과목이름");
                cell = row.createCell(2);
                cell.setCellValue("i+1번째 학점");
                cell = row.createCell(3);
                cell.setCellValue("i+1번째 평점");
                cell = row.createCell(4);
                cell.setCellValue("i+1번째 전공");
            }
            break;
            case 6:
            row = sheet6.createRow(0); // 오류
            // 1번 셀은 빈칸
            cell = row.createCell(0);
            // 2번 셀은 과목이름
            cell = row.createCell(1);
            cell.setCellValue("과목 이름");
            // 3번 셀은 과목 학점
            cell = row.createCell(2);
            cell.setCellValue("학점");
            // 4번 셀은 과목 평점
            cell = row.createCell(3);
            cell.setCellValue("평점");
            // 5번 셀은 전공 여부 체크
            cell = row.createCell(4);
            cell.setCellValue("전공");
            for (int i = 0; i < 10; i++) {
                row = sheet6.createRow(i + 1); // 오류
                cell = row.createCell(1);
                cell.setCellValue("i+1번째 과목이름");
                cell = row.createCell(2);
                cell.setCellValue("i+1번째 학점");
                cell = row.createCell(3);
                cell.setCellValue("i+1번째 평점");
                cell = row.createCell(4);
                cell.setCellValue("i+1번째 전공");
            }
            break;
            case 7:
            row = sheet7.createRow(0); // 오류
            // 1번 셀은 빈칸
            cell = row.createCell(0);
            // 2번 셀은 과목이름
            cell = row.createCell(1);
            cell.setCellValue("과목 이름");
            // 3번 셀은 과목 학점
            cell = row.createCell(2);
            cell.setCellValue("학점");
            // 4번 셀은 과목 평점
            cell = row.createCell(3);
            cell.setCellValue("평점");
            // 5번 셀은 전공 여부 체크
            cell = row.createCell(4);
            cell.setCellValue("전공");
            for (int i = 0; i < 10; i++) {
                row = sheet7.createRow(i + 1); // 오류
                cell = row.createCell(1);
                cell.setCellValue("i+1번째 과목이름");
                cell = row.createCell(2);
                cell.setCellValue("i+1번째 학점");
                cell = row.createCell(3);
                cell.setCellValue("i+1번째 평점");
                cell = row.createCell(4);
                cell.setCellValue("i+1번째 전공");
            }
            break;
            case 8:
            row = sheet8.createRow(0); // 오류
            // 1번 셀은 빈칸
            cell = row.createCell(0);
            // 2번 셀은 과목이름
            cell = row.createCell(1);
            cell.setCellValue("과목 이름");
            // 3번 셀은 과목 학점
            cell = row.createCell(2);
            cell.setCellValue("학점");
            // 4번 셀은 과목 평점
            cell = row.createCell(3);
            cell.setCellValue("평점");
            // 5번 셀은 전공 여부 체크
            cell = row.createCell(4);
            cell.setCellValue("전공");
            for (int i = 0; i < 10; i++) {
                row = sheet8.createRow(i + 1); // 오류
                cell = row.createCell(1);
                cell.setCellValue("i+1번째 과목이름");
                cell = row.createCell(2);
                cell.setCellValue("i+1번째 학점");
                cell = row.createCell(3);
                cell.setCellValue("i+1번째 평점");
                cell = row.createCell(4);
                cell.setCellValue("i+1번째 전공");
            }
            break;
            case 9:
            row = sheet9.createRow(0); // 오류
            // 1번 셀은 빈칸
            cell = row.createCell(0);
            // 2번 셀은 과목이름
            cell = row.createCell(1);
            cell.setCellValue("과목 이름");
            // 3번 셀은 과목 학점
            cell = row.createCell(2);
            cell.setCellValue("학점");
            // 4번 셀은 과목 평점
            cell = row.createCell(3);
            cell.setCellValue("평점");
            // 5번 셀은 전공 여부 체크
            cell = row.createCell(4);
            cell.setCellValue("전공");
            for (int i = 0; i < 10; i++) {
                row = sheet9.createRow(i + 1); // 오류
                cell = row.createCell(1);
                cell.setCellValue("i+1번째 과목이름");
                cell = row.createCell(2);
                cell.setCellValue("i+1번째 학점");
                cell = row.createCell(3);
                cell.setCellValue("i+1번째 평점");
                cell = row.createCell(4);
                cell.setCellValue("i+1번째 전공");
            }
            break;
            default:
                throw new IllegalStateException("Unexpected value: " + sheetnum);
        }


        File excelFile = new File(getExternalFilesDir(null), "user.xls");
        try {
            FileOutputStream os = new FileOutputStream(excelFile);
            workbook.write(os);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Toast myToast = Toast.makeText(getApplicationContext(), excelFile.getAbsolutePath() + "에 저장되었습니다", Toast.LENGTH_SHORT);
        myToast.show();
    }

    public void function1() { //어디 입력하는지 확인
        switch (Name){
            case "1학년 1학기" :
                sheetnum = 1;
                break;
            case "1학년 2학기" :
                sheetnum = 2;
                break;
            case "2학년 1학기" :
                sheetnum = 3;
                break;
            case "2학년 2학기" :
                sheetnum = 4;
                break;
            case "3학년 1학기" :
                sheetnum = 5;
                break;
            case "3학년 2학기" :
                sheetnum = 6;
                break;
            case "4학년 1학기" :
                sheetnum = 7;
                break;
            case "4학년 2학기" :
                sheetnum = 8;
                break;
            case "기타학기" :
                sheetnum = 9;
                break;

        }
    }

}