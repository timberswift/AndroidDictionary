package cn.xtangtang.droder;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import HTTP.HttpTools;

public class MainActivity extends AppCompatActivity {
    private Button button_ok;
    private EditText input;
    private TextView output;
    private TextView outkey;
    private Button En;
    private Button Am;
    private MediaPlayer mediaPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        addListenerOnButton();
        setOnEditorActionListener();
    }
    private void initView(){
        button_ok = (Button) findViewById(R.id.button_ok);
        input = (EditText) findViewById(R.id.inputText);
        output = (TextView) findViewById(R.id.outputText);
        outkey = (TextView) findViewById(R.id.outkey);
        En = (Button) findViewById(R.id.en);
        Am = (Button) findViewById(R.id.am);
    }
    public void setOnEditorActionListener() {
        input.setOnEditorActionListener((input1, actionId, event) -> {

            if(actionId == EditorInfo.IME_ACTION_SEARCH || actionId ==EditorInfo.IME_ACTION_GO){

                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE); //得到InputMethodManager的实例
                if (imm.isActive()) {//如果开启
                    imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT,InputMethodManager.HIDE_NOT_ALWAYS);//关闭软键盘，开启方法相同，这个方法是切换开启与关闭状态的
                }
                final String[] res1 = {""};
                new Thread(() -> {
                    HttpTools act = new HttpTools();
                    act.GetApiUrl(input.getText().toString());
                    act.GetJsonPacket();
                    act.GetPronunciation();
                    Sound act2 = new Sound();

                    res1[0] = act.GetMeaning();
                    String es = act2.getEn_symbol();
                    String as = act2.getAm_symbol();
                    runOnUiThread(() -> {
                        outkey.setText(input1.getText());
                        output.setText(res1[0]);
                        En.setText(es);
                        Am.setText(as);

                    });
                }).start();
                return true;
            }
            else return false;
        });

    }
    public void addListenerOnButton() {

        final String[] res = {""};
        button_ok.setOnClickListener(v -> new Thread(() -> {

            HttpTools act = new HttpTools();
            act.GetApiUrl(input.getText().toString());
            act.GetJsonPacket();
            act.GetPronunciation();
            Sound act2 = new Sound();

            String es = act2.getEn_symbol();
            String as = act2.getAm_symbol();
            res[0] = act.GetMeaning();

            runOnUiThread(() -> {
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE); //得到InputMethodManager的实例
                if (imm.isActive()) {//如果开启
                    imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT,InputMethodManager.HIDE_NOT_ALWAYS);//关闭软键盘，开启方法相同，这个方法是切换开启与关闭状态的
                }

                outkey.setText(input.getText());
                output.setText(res[0]);
                En.setText(es);
                Am.setText(as);
            });
        }).start());

        En.setOnClickListener(v -> {
            Sound url1 = new Sound();
            url1.play(url1.getEn_sound());
            });

        Am.setOnClickListener(v -> {
            Sound url2 = new Sound();
            url2.play(url2.getAm_sound());

        });
    }

}



