package kwaksuin.portfolio.weatherapp;

public class Weather {

    //초단기 실황 (지금 현재 날씨)
    String T1H = ""; //현재 기온
    String PTY = null; //현재 강수형태

    //일주일 최고온도 (중기예보, 동네예보- 0500)
    String TMX1 = null;
    String TMX2 = null;

    //일주일 최저온도 (중기예보, 동네예보-0200은 오늘내일 최저 0500은 모레최저)
    String TMN1 = null;
    String TMN2 = null;

    //내일 강수 (새벽, 아침, 점심, 저녁, 밤) - 동네예보
    String T_PTY_1 = null;
    String T_PTY_2 = null;
    String T_PTY_3 = null;
    String T_PTY_4 = null;
    String T_PTY_5 = null;


    //모레 강수 (새벽, 아침, 점심, 저녁, 밤) - 동네예보
    String F_PTY_1 = null;
    String F_PTY_2 = null;
    String F_PTY_3 = null;
    String F_PTY_4 = null;
    String F_PTY_5 = null;



    public void setT1H(String T1H) {
        this.T1H = T1H;
    }
    public void setPTY(String PTY) {
        this.PTY = PTY;
    }

}