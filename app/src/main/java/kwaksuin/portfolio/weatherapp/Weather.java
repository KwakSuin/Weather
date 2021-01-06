package kwaksuin.portfolio.weatherapp;

public class Weather {

    //초단기 실황 (지금 현재 날씨)
    String Tem = null; //현재 기온

    //일주일 날씨 (해,구름 등) - 중기예보, 동네예보- 1200
    String WEEK_SC1 = null;
    String WEEK_SC2 = null;
    String WEEK_SC3 = null;
    String WEEK_SC4 = null;
    String WEEK_SC5 = null;
    String WEEK_SC6 = null;
    String WEEK_SC7 = null;
    String WEEK_SC8 = null;

    //일주일 최고온도 (중기예보, 동네예보- 0500)
    String WEEK_TMX1 = null;
    String WEEK_TMX2 = null;
    String WEEK_TMX3 = null;
    String WEEK_TMX4 = null;
    String WEEK_TMX5 = null;
    String WEEK_TMX6 = null;
    String WEEK_TMX7 = null;
    String WEEK_TMX8 = null;
    String WEEK_TMX9 = null;
    String WEEK_TMX10 = null;

    //일주일 최저온도 (중기예보, 동네예보-0200은 오늘내일 최저 0500은 모레최저)
    String WEEK_TMN1 = null;
    String WEEK_TMN2 = null;
    String WEEK_TMN3 = null;
    String WEEK_TMN4 = null;
    String WEEK_TMN5 = null;
    String WEEK_TMN6 = null;
    String WEEK_TMN7 = null;
    String WEEK_TMN8 = null;
    String WEEK_TMN9 = null;
    String WEEK_TMN10 = null;

    //오늘 날씨 (새벽, 아침, 점심, 저녁, 밤) - 동네예보
    String TODAY_SKY_1 = null;
    String TODAY_SKY_2 = null;
    String TODAY_SKY_3 = null;
    String TODAY_SKY_4 = null;
    String TODAY_SKY_5 = null;

    //오늘 온도 (새벽, 아침, 점심, 저녁, 밤) - 동네예보
    String TODAY_T3H_1 = null;
    String TODAY_T3H_2 = null;
    String TODAY_T3H_3 = null;
    String TODAY_T3H_4 = null;
    String TODAY_T3H_5 = null;

    //내일 날씨 (새벽, 아침, 점심, 저녁, 밤) - 동네예보
    String TOMO_SKY_1 = null;
    String TOMO_SKY_2 = null;
    String TOMO_SKY_3 = null;
    String TOMO_SKY_4 = null;
    String TOMO_SKY_5 = null;

    //내일 온도 (새벽, 아침, 점심, 저녁, 밤) - 동네예보
    String TOMO_Tem_T3H_1 = null;
    String TOMO_Tem_T3H_2 = null;
    String TOMO_Tem_T3H_3 = null;
    String TOMO_Tem_T3H_4 = null;
    String TOMO_Tem_T3H_5 = null;
    String TOMO_Tem_WSD_5 = null;

    //모레 날씨 (새벽, 아침, 점심, 저녁, 밤) - 동네예보
    String AFTER_SKY_1 = null;
    String AFTER_SKY_2 = null;
    String AFTER_SKY_3 = null;
    String AFTER_SKY_4 = null;
    String AFTER_SKY_5 = null;

    public void setTem(String t1H) {
        Tem = t1H;
    }
}