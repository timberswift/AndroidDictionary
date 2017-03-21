package HTTP;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import cn.xtangtang.droder.Sound;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by asus on 2017/3/18.
 */

public class HttpTools{

    private final static String api = "http://dict-co.iciba.com/api/dictionary.php?type=json&key=983F8AB89F8C8CAE6978D507504EFD9A&w=";
    private static String ApiUrl;
    private static String jsonString;

    public void GetApiUrl(String word){
        this.ApiUrl = api + word;
    }

    public void GetJsonPacket(){

        OkHttpClient client = new OkHttpClient();
        String url = ApiUrl;
        Request request = new Request.Builder().url(url).build();
        okhttp3.Response response;

        try{
            response = client.newCall(request).execute();
            String jsonStr = response.body().string();
            //Log.e(TAG, "GetJsonPacket: "+jsonStr );
            this.jsonString = jsonStr;
            //return jsonStr;
        }catch(IOException e1){
            this.jsonString = "error";
        }

    }

    public String GetMeaning()
    {
        if(jsonString.equals("error"))return "Droder提醒您: 未连接到互联网...";
        try {// 英文 t
            JSONObject dataJson = new JSONObject(jsonString);
            JSONArray parts = dataJson.getJSONArray("symbols")
                    .getJSONObject(0).getJSONArray("parts");

            JSONObject pron = dataJson.getJSONArray("symbols")
                    .getJSONObject(0);
            String en = pron.getString("ph_en_mp3");
            String am = pron.getString("ph_am_mp3");
            Sound sound = new Sound();
            sound.SetEnSound(en);
            sound.SetAmSound(am);

            String k = "";
 //           String s;
//            s = "英:["+dataJson.getJSONArray("symbols").getJSONObject(0).getString("ph_en") +"]"+'\n'
//                    + "美:["+dataJson.getJSONArray("symbols").getJSONObject(0).getString("ph_am")+"]"+'\n'+'\n';
            String[] out = new String[parts.length()];
            for (int i = 0; i < parts.length(); i++)
                out[i] = "";

            for (int i = 0; i < parts.length(); i++) {
                JSONObject object = parts.getJSONObject(i);
                out[i] += object.getString("part");

                JSONArray means = object.getJSONArray("means");
                for (int j = 0; j < means.length(); j++) {
                    out[i] += means.getString(j) + ".";
                }
                k = k + out[i] + '\n';
            }
            return k;
        } catch (JSONException t) {

            try {// 中文释义 t2

                JSONObject dataJson1 = new JSONObject(jsonString);
                JSONArray parts1 = dataJson1
                        .getJSONArray("symbols")
                        .getJSONObject(0)
                        .getJSONArray("parts");

                String k1 = "";
                String[] out1 = new String[20];

                for (int i = 0; i < 20; i++) out1[i] = "";

                for (int i = 0; i < parts1.length(); i++) {
                    JSONObject object1 = parts1
                            .getJSONObject(i);
                    JSONArray means1 = object1
                            .getJSONArray("means");
                    for (int j = 0; j < means1.length(); j++) {
                        out1[j] += means1.getJSONObject(j)
                                .getString("word_mean");
                        k1 = k1 + out1[j] + '\n';
                    }
                }
                return k1;

            } catch (JSONException t2) {
                Sound sound = new Sound();
                sound.SetEnSound("");
                sound.SetAmSound("");
                return ("对不起...未查询到相关释义");
            } // catch(t2)
        } // catch(t)



    }

    public void GetPronunciation(){
        if (!jsonString.equals("error"))
        try {// 英文 t
            JSONObject dataJson = new JSONObject(jsonString);
            JSONObject pron = dataJson.getJSONArray("symbols")
                    .getJSONObject(0);
            String en = pron.getString("ph_en_mp3");
            String am = pron.getString("ph_am_mp3");
            Sound sound = new Sound();
            sound.SetEnSound(en);
            sound.SetAmSound(am);

            Sound ch = new Sound();
            ch.SetEnSymbol("英: [" + dataJson.getJSONArray("symbols").getJSONObject(0).getString("ph_en") + "]");
            ch.SetAmSymbol("美: [" + dataJson.getJSONArray("symbols").getJSONObject(0).getString("ph_am") + "]");

        }catch (JSONException e){
            Sound sound = new Sound();
            sound.SetEnSound("");
            sound.SetAmSound("");

        }
    }
}
