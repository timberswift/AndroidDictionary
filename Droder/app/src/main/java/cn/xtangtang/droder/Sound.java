package cn.xtangtang.droder;

import android.media.AudioManager;
import android.media.MediaPlayer;

import java.io.IOException;

/**
 * Created by asus on 2017/3/11.
 */

public class Sound {

    private static String en_sound;
    private  static String am_sound;
    private static  String en_symbol;
    private static String am_symbol;
    private MediaPlayer mediaPlayer;

    public void SetAmSymbol(String a){
        this.am_symbol = a;
    }
    public void SetEnSymbol(String e){
        this.en_symbol = e;
    }

    public void SetAmSound(String am){
        this.am_sound = am;
    }
    public void SetEnSound(String en){
        this.en_sound = en;
    }

    public String getAm_sound() { return am_sound; }
    public String getEn_sound() {
        return en_sound;
    }
    public String getAm_symbol(){ return am_symbol; }
    public String getEn_symbol(){ return en_symbol; }
    public void play(String source){

        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            mediaPlayer.setDataSource(source);
            mediaPlayer.prepare();
            mediaPlayer.start();
            //  play.setEnabled(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
        /*mediaPlayer.setOnCompletionListener(mp -> {
            // play.setEnabled(true);
            mp.release();
            mp = null;
        });*/
    }
}
