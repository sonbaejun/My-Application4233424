package com.example.myapplication4233424;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    String tempmin;
    String tempmax;
    String[] array = new String[43];
    String[] arraysky = new String[43];
    String[] arraytemp = new String[43];
    ImageView[] array2 = new ImageView[9];
    Button gotocmt;
    int i = 0;
    int skyavg;
    int snowavg = 0;
    int rainavg = 0;
    int skycnt =0 ;
    int max = 0;
    int min = 0;
    String nx1 = "91";
    String ny1 = "77";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("WeatherLike");
        StrictMode.enableDefaults();
        TextView tempMinMax = (TextView) findViewById(R.id.tempmin);
        ImageView skyimg = (ImageView) findViewById(R.id.skyavg);
        TextView weather1 = (TextView) findViewById(R.id.weather1);
        TextView weather2 = (TextView) findViewById(R.id.weather2);
        TextView weather3 = (TextView) findViewById(R.id.weather3);
        TextView weather4 = (TextView) findViewById(R.id.weather4);
        TextView weather5 = (TextView) findViewById(R.id.weather5);
        TextView weather6 = (TextView) findViewById(R.id.weather6);
        TextView weather7 = (TextView) findViewById(R.id.weather7);
        TextView weather8 = (TextView) findViewById(R.id.weather8);
        TextView weather9 = (TextView) findViewById(R.id.weather9);
        TextView clothe1 = (TextView) findViewById(R.id.quater1);
        TextView clothe2 = (TextView) findViewById(R.id.quater2);
        TextView clothe3 = (TextView) findViewById(R.id.quater3);
        TextView clothe4 = (TextView) findViewById(R.id.quater4);
        TextView clothe5 = (TextView) findViewById(R.id.quater5);
        TextView comments = (TextView) findViewById(R.id.comments);
        ImageView cltimg1 = (ImageView) findViewById(R.id.quaterimg1);
        ImageView cltimg2 = (ImageView) findViewById(R.id.quaterimg2);
        ImageView cltimg3 = (ImageView) findViewById(R.id.quaterimg3);
        ImageView cltimg4 = (ImageView) findViewById(R.id.quaterimg4);
        ImageView cltimg5 = (ImageView) findViewById(R.id.quaterimg5);

        array2[0] = (ImageView) findViewById(R.id.weatherimg1);
        array2[1] = (ImageView) findViewById(R.id.weatherimg2);
        array2[2] = (ImageView) findViewById(R.id.weatherimg3);
        array2[3] = (ImageView) findViewById(R.id.weatherimg4);
        array2[4] = (ImageView) findViewById(R.id.weatherimg5);
        array2[5] = (ImageView) findViewById(R.id.weatherimg6);
        array2[6] = (ImageView) findViewById(R.id.weatherimg7);
        array2[7] = (ImageView) findViewById(R.id.weatherimg8);
        array2[8] = (ImageView) findViewById(R.id.weatherimg9);

        long now = System.currentTimeMillis();
        Date mDate = new Date(now);
        SimpleDateFormat simpleDate = new SimpleDateFormat("yyyyMMdd");
        String getDate = simpleDate.format(mDate);

        SimpleDateFormat simpleTime = new SimpleDateFormat("k00");
        String getTime = simpleTime.format(mDate);

        int j = 0;

        switch (getTime) {
            case "0000":
            case "0100":
            case "0200":
                j = 0;
                break;
            case "0300":
            case "0400":
            case "0500":
                j = 3;
                break;
            case "0600":
            case "0700":
            case "0800":
                j = 6;
                break;
            case "0900":
            case "1000":
            case "1100":
                j = 3;
                break;
            case "1200":
            case "1300":
            case "1400":
                j = 6;
                break;
            case "1500":
            case "1600":
            case "1700":
                j = 9;
                break;
            case "1800":
            case "1900":
            case "2000":
                j = 12;
                break;
            case "2100":
            case "2200":
            case "2300":
                j = 15;
                break;
        }

        switch (getTime) {
            case "0000":
            case "0100":
            case "0300":
            case "0400":
            case "0600":
            case "0700":
            case "0800":
                getTime = "0200";
                break;
            default:
                getTime = "0800";
                break;
        }

        parsing(getDate, getTime);
        tempMinMax.setText(tempmin + " / " + tempmax);

        int kk=0;

        switch (arraytemp[j]) {
            case "27℃":
            case "28℃":
            case "29℃":
            case "30℃":
            case "31℃":
            case "32℃":
            case "33℃":
            case "34℃":
            case "35℃":
            case "36℃":
            case "37℃":
            case "38℃":
            case "39℃":
            case "40℃":
            case "41℃":
            case "42℃":
            case "43℃":
            clothe1.setText("나시티");
            clothe2.setText("반팔");
            clothe3.setText("민소매");
            clothe4.setText("원피스");
            clothe5.setText("반바지");

            cltimg1.setImageResource(R.drawable.sleeveless50);
            cltimg2.setImageResource(R.drawable.tshirt50);
            cltimg3.setImageResource(R.drawable.sleeveless50);
            cltimg4.setImageResource(R.drawable.dress50);
            cltimg5.setImageResource(R.drawable.shorts50);
            comments.setText("날이많이 덥습니다.\n더위 안먹게 조심하세요!! \n");
            break;
            case "26℃":
            case "25℃":
            case "24℃":
            case "23℃":
                clothe1.setText("반팔");
                clothe2.setText("얇은 셔츠");
                clothe3.setText("얇은 긴팔");
                clothe4.setText("반바지");
                clothe5.setText("면바지");

                cltimg1.setImageResource(R.drawable.tshirt50);
                cltimg2.setImageResource(R.drawable.shirt50);
                cltimg3.setImageResource(R.drawable.mantoman50);
                cltimg4.setImageResource(R.drawable.shorts50);
                cltimg5.setImageResource(R.drawable.trousers50);
                comments.setText("아직은 더운 날씨.\n얇게 입는걸 추천드려요! \n");
                break;
            case "22℃":
            case "21℃":
            case "20℃":
                clothe1.setText("긴팔티");
                clothe2.setText("블라우스");
                clothe3.setText("후드티");
                clothe4.setText("면바지");
                clothe5.setText("슬랙스");

                cltimg1.setImageResource(R.drawable.mantoman50);
                cltimg2.setImageResource(R.drawable.dress50);
                cltimg3.setImageResource(R.drawable.hood50);
                cltimg4.setImageResource(R.drawable.trousers50);
                cltimg5.setImageResource(R.drawable.slax50);
                comments.setText("버틸만한 더위입니다.\n밤이나 새벽엔 얇은 외투를 휴대하세요! \n");
                break;
            case "19℃":
            case "18℃":
            case "17℃":
                clothe1.setText("셔츠");
                clothe2.setText("얇은 니트");
                clothe3.setText("후드티");
                clothe4.setText("맨투맨");
                clothe5.setText("청바지");

                cltimg1.setImageResource(R.drawable.shirt50);
                cltimg2.setImageResource(R.drawable.knit50);
                cltimg3.setImageResource(R.drawable.hood50);
                cltimg4.setImageResource(R.drawable.mantoman50);
                cltimg5.setImageResource(R.drawable.jeans50);
                comments.setText("일교차에 주의하세요.\n밤엔 많이 추울 수 있습니다! \n");
                break;
            case "16℃":
            case "15℃":
            case "14℃":
            case "13℃":
            case "12℃":
                clothe1.setText("자켓");
                clothe2.setText("가디건");
                clothe3.setText("니트");
                clothe4.setText("야상");
                clothe5.setText("청바지");

                cltimg1.setImageResource(R.drawable.jacket50);
                cltimg2.setImageResource(R.drawable.cardigan50);
                cltimg3.setImageResource(R.drawable.knit50);
                cltimg4.setImageResource(R.drawable.hunting50);
                cltimg5.setImageResource(R.drawable.jeans50);
                comments.setText("옷 입기 좋은 날씨\n두꺼운 옷 보단 레이어드를 추천드려요! \n");
                break;
            case "11℃":
            case "10℃":
                clothe1.setText("야상");
                clothe2.setText("얇은 코트");
                clothe3.setText("니트");
                clothe4.setText("내의");
                clothe5.setText("가디건");

                cltimg1.setImageResource(R.drawable.hunting50);
                cltimg2.setImageResource(R.drawable.coat50);
                cltimg3.setImageResource(R.drawable.knit50);
                cltimg4.setImageResource(R.drawable.hunting50);
                cltimg5.setImageResource(R.drawable.cardigan50);
                comments.setText("급습하는 추위 조심하세요!\n니트나 가디건을 이너로 활용하는걸 추천드려요!\n");
                break;
            case "9℃":
            case "8℃":
            case "7℃":
            case "6℃":
                clothe1.setText("코트");
                clothe2.setText("가죽자켓");
                clothe3.setText("이너니트");
                clothe4.setText("내의");
                clothe5.setText("목도리");

                cltimg1.setImageResource(R.drawable.coat50);
                cltimg2.setImageResource(R.drawable.gajukjaket50);
                cltimg3.setImageResource(R.drawable.knit50);
                cltimg4.setImageResource(R.drawable.underwear50);
                cltimg5.setImageResource(R.drawable.scarf50);
                comments.setText("본격적인 추위가 찾아왔어요\n두꺼운 외투와 방한용품을 꺼내셔도 좋습니다! \n");
                break;
            default:
                clothe1.setText("야상");
                clothe2.setText("패딩");
                clothe3.setText("목도리");
                clothe4.setText("장갑");
                clothe5.setText("내의");

                cltimg1.setImageResource(R.drawable.hunting50);
                cltimg2.setImageResource(R.drawable.padding);
                cltimg3.setImageResource(R.drawable.scarf50);
                cltimg4.setImageResource(R.drawable.gloves50);
                cltimg5.setImageResource(R.drawable.underwear50);
                comments.setText("\n\n강추위입니다.\n생존을 위해 꽁꽁 둘러매세요! \n\n");
                break;
        }

        for(int i=j; i<=j+24; i+=3) {
            if(arraysky[i].contains("비")) {
                array2[kk].setImageResource(R.drawable.rain45);
                kk++;
                skyavg+=4;
                rainavg++;
            }
            else if(arraysky[i].contains("눈")) {
                array2[kk].setImageResource(R.drawable.snow45);
                kk++;
                snowavg++;
                skyavg+=4;
            }
            else if(arraysky[i].contains("없음")) {
                if(arraysky[i].contains("맑음")) {
                    if(array[i].contains("1800")) {
                        array2[kk].setImageResource(R.drawable.cmoon45);
                        kk++;
                        skyavg+=1;
                    }
                    else if(array[i].contains("2100")) {
                        array2[kk].setImageResource(R.drawable.cmoon45);
                        kk++;
                        skyavg+=1;
                    }
                    else if(array[i].contains("0000")) {
                        array2[kk].setImageResource(R.drawable.cmoon45);
                        kk++;
                        skyavg+=1;
                    }
                    else if(array[i].contains("0300")) {
                        array2[kk].setImageResource(R.drawable.cmoon45);
                        kk++;
                        skyavg+=1;
                    }
                    else {
                        array2[kk].setImageResource(R.drawable.sun45);
                        kk++;
                        skyavg+=1;
                    }
                }
                else if(arraysky[i].contains("구름")) {
                    array2[kk].setImageResource(R.drawable.suncloud45);
                    kk++;
                    skyavg+=3;
                }
                else if(arraysky[i].contains("흐림")) {
                    array2[kk].setImageResource(R.drawable.cloud45);
                    kk++;
                    skyavg+=4;
                }
            }
            skycnt++;
        }

        for(int i=j; i<=j+24; i+=3) {
            array[i] = array[i].replace("00","시 ");
            array[i] = array[i].replace("시 시 ","00시 ");
        }
        weather1.setText(" " + array[j] + "");
        j+=3;
        weather2.setText(array[j] + " ");
        j+=3;
        weather3.setText(array[j] + " ");
        j+=3;
        weather4.setText(array[j] + " ");
        j+=3;
        weather5.setText(array[j] + " ");
        j+=3;
        weather6.setText(array[j] + " ");
        j+=3;
        weather7.setText(array[j] + " ");
        j+=3;
        weather8.setText(array[j] + " ");
        j+=3;
        weather9.setText(array[j] + " ");
        j+=3;

        double skynine = skyavg / skycnt;
        if(rainavg >= 5) {
            skyimg.setImageResource(R.drawable.rain50);
        }
        else if(rainavg >= 5) {
            skyimg.setImageResource(R.drawable.snow50);
        }
        else {
            if(skynine < 6)
                skyimg.setImageResource(R.drawable.sun50);
            else if(skynine>=6 && skynine<9)
                skyimg.setImageResource(R.drawable.suncloud50);
            else if(skynine>=9 && skynine<=10)
                skyimg.setImageResource(R.drawable.cloud50);
        }

       gotocmt = (Button) findViewById(R.id.gotocmt);
        gotocmt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startLoginActivity();
            }
        });



        }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        SubMenu sMenu = menu.addSubMenu("지역 설정");
        sMenu.add(0,1,0,"서울");
        sMenu.add(0,2,0,"인천");
        sMenu.add(0,3,0,"수원");
        sMenu.add(0,4,0,"춘천");
        sMenu.add(0,5,0,"강릉");
        sMenu.add(0,6,0,"청주");
        sMenu.add(0,7,0,"대전");
        sMenu.add(0,8,0,"전주");
        sMenu.add(0,9,0,"목포");
        sMenu.add(0,10,0,"광주");
        sMenu.add(0,11,0,"대구");
        sMenu.add(0,12,0,"여수");
        sMenu.add(0,13,0,"울산");
        sMenu.add(0,14,0,"창원");
        sMenu.add(0,15,0,"부산");
        sMenu.add(0,16,0,"제주");

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case 1:
                nx1 = "61";
                ny1 = "126";
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    public void parsing(String Date, String Time) {


            Log.d("parsing", " start");
            TextView status1 = (TextView)findViewById(R.id.result); //파싱된 결과확인!
            boolean initem = false, inbaseDate = false, inbaseTime = false, incategory = false, infcstDate = false;
            boolean infcstTime = false, infcstValue = false, innx = false, inny = false;

            String baseDate = null, baseTime = null, category = null, fcstDate = null, fcstTime = null, fcstValue=null;
            String Sky = null, temp = null;
            String Rain = null;
            int Sky2 = 0;
            try{
                StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getVilageFcst"); /*URL*/
                urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=N0jE1smn9kCKhuBBmUzMSeHsAD4RmtbLZTsxuZAwviLb3H4EnGBLMEbjd6WnYv4Z1GN24jfcv56nvVfG39Fpgg=="); /*Service Key*/
                urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
                urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("500", "UTF-8")); /*한 페이지 결과 수*/
                urlBuilder.append("&" + URLEncoder.encode("base_date","UTF-8") + "=" + URLEncoder.encode(Date, "UTF-8")); /*검색할 생성일 범위의 시작*/
                urlBuilder.append("&" + URLEncoder.encode("base_time","UTF-8") + "=" + URLEncoder.encode(Time, "UTF-8")); /*검색할 생성일 범위의 종료*/
                urlBuilder.append("&" + URLEncoder.encode("nx","UTF-8") + "=" + URLEncoder.encode(nx1, "UTF-8")); /*검색할 생성일 범위의 종료*/
                urlBuilder.append("&" + URLEncoder.encode("ny","UTF-8") + "=" + URLEncoder.encode(ny1, "UTF-8")); /*검색할 생성일 범위의 종료*/

                URL url = new URL(urlBuilder.toString());
                XmlPullParserFactory parserCreator = XmlPullParserFactory.newInstance();
                XmlPullParser parser = parserCreator.newPullParser();

                parser.setInput(url.openStream(), null);

                int parserEvent = parser.getEventType();
                System.out.println("파싱시작합니다.");

                while (parserEvent != XmlPullParser.END_DOCUMENT){
                    switch(parserEvent){
                        case XmlPullParser.START_TAG://parser가 시작 태그를 만나면 실행
                            if(parser.getName().equals("category")){ //mapx 만나면 내용을 받을수 있게 하자
                                incategory = true;
                            }
                            if(parser.getName().equals("fcstDate")){ //mapy 만나면 내용을 받을수 있게 하자
                                infcstDate = true;
                            }
                            if(parser.getName().equals("fcstTime")){ //mapy 만나면 내용을 받을수 있게 하자
                                infcstTime = true;
                            }
                            if(parser.getName().equals("fcstValue")){ //mapy 만나면 내용을 받을수 있게 하자
                                infcstValue = true;
                            }
                            if(parser.getName().equals("message")){ //message 태그를 만나면 에러 출력
                                status1.setText(status1.getText()+"에러");
                                //여기에 에러코드에 따라 다른 메세지를 출력하도록 할 수 있다.
                            }
                            break;

                        case XmlPullParser.TEXT://parser가 내용에 접근했을때
                            if(incategory){ //isMapx이 true일 때 태그의 내용을 저장.
                                category = parser.getText();
                                incategory = false;
                            }
                            if(infcstDate){ //isMapy이 true일 때 태그의 내용을 저장.
                                fcstDate = parser.getText();
                                infcstDate = false;
                            }
                            if(infcstTime){ //isMapy이 true일 때 태그의 내용을 저장.
                                fcstTime = parser.getText();
                                infcstTime = false;
                            }
                            if(infcstValue){ //isMapy이 true일 때 태그의 내용을 저장.
                                if(category.equals("SKY")) {
                                    fcstValue = parser.getText();
                                    infcstValue = false;
                                    if(fcstValue.equals("1")) {
                                        Sky = "맑음";
                                        Sky2 = 1;
                                    }
                                    else if(fcstValue.equals("3")) {
                                        Sky = "구름";
                                        Sky2 = 3;
                                    }
                                    else if(fcstValue.equals("4")) {
                                        Sky = "흐림";
                                        Sky2 = 4;
                                    }
                                    else
                                        Sky = "아닌데용";

                                }
                                else if(category.equals("TMP")){
                                    temp = parser.getText()+"℃";
                                    infcstValue = false;
                                }
                                else if(category.equals("TMN")) {
                                        tempmin = parser.getText()+"℃";
                                        infcstValue = false;
                                }
                                else if(category.equals("TMX")) {
                                        tempmax = parser.getText()+"℃";
                                        infcstValue = false;
                                }
                                else if(category.equals("PTY")) {
                                    fcstValue = parser.getText();
                                    infcstValue = false;
                                    if(fcstValue.equals("0"))
                                        Rain = "없음";
                                    else if(fcstValue.equals("1"))
                                        Rain = "비";
                                    else if(fcstValue.equals("2"))
                                        Rain = "눈";
                                    else if(fcstValue.equals("3"))
                                        Rain = "눈";
                                    else if(fcstValue.equals("4"))
                                        Rain = "비";
                                    else
                                        Rain = "5200";
                                }
                                else
                                    break;
                            }
                            break;
                        case XmlPullParser.END_TAG:
                            if(parser.getName().equals("item")) {
                                if (category.equals("SKY")) {
                                    array[i] = status1.getText() + fcstTime + "\n " +
                                              temp + "\n";
                                    arraysky[i] = status1.getText() + Sky + Rain;
                                    arraytemp[i] = status1.getText() + temp;
                                    i++;
                                    initem = false;
                                }
                            }
                            break;
                    }
                    parserEvent = parser.next();
                }
            } catch(Exception e){
                status1.setText("에러가..났습니다...");
            }
        }

    private void startLoginActivity() {
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }
}