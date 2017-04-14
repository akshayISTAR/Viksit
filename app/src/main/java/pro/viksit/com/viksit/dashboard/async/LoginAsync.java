package pro.viksit.com.viksit.dashboard.async;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import pro.viksit.com.viksit.R;
import pro.viksit.com.viksit.dashboard.activity.DashboardActivity;
import pro.viksit.com.viksit.dashboard.pojo.StudentProfile;
import pro.viksit.com.viksit.home.activity.SplashScreenActivity;

/**
 * Created by Feroz on 06-04-2017.
 */

public class LoginAsync extends AsyncTask<String, Integer, String> {
    private  HashMap<String,String> param;
    private Context context;
    private MaterialDialog dialog;
    private  MaterialDialog progressdialog;
    private SharedPreferences sharedpreferences;
    //private final Gson gson = new Gson();

    public LoginAsync(HashMap<String,String> param,Context context,MaterialDialog dialog, MaterialDialog progressdialog,SharedPreferences sharedpreferences){
        this.param = param;
        this.context = context;
        this.dialog = dialog;
        this.progressdialog = progressdialog;
        this.sharedpreferences = sharedpreferences;
    }

    @Override
    protected  void onPreExecute()
    {
    }

    @Override
    protected String doInBackground(String... params) {
        // TODO Auto-generated method stub
        return postData(param,params[0]);
    }


    @Override
    protected void onPostExecute(String result) {
        if(result.equalsIgnoreCase("null")){
            dialog.show();
        }else {
            Intent i = new Intent(context, SplashScreenActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
        }
        progressdialog.dismiss();

    }
    @Override
    protected void onProgressUpdate(Integer... progress) {
        super.onProgressUpdate(progress);
        progressdialog.show();

    }



    private String postData(HashMap<String,String> param,String url) {
        publishProgress(5);

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        HttpClient httpclient = new DefaultHttpClient();
        System.out.println("calling url is -> "+context.getResources().getString(R.string.serverip)+url);
        HttpPost httppost = new HttpPost(context.getResources().getString(R.string.serverip)+url);
        String jsonresponse= "null";
        try {
            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            for(String key: param.keySet()) {
                nameValuePairs.add(new BasicNameValuePair(key, param.get(key)));
            }
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity httpEntity = response.getEntity();
            jsonresponse = EntityUtils.toString(httpEntity);

            System.out.println("jsonresponse "+jsonresponse);
            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
            StudentProfile studentProfile = gson.fromJson(jsonresponse,StudentProfile.class);
            System.out.println("studentProfile "+studentProfile.getEmail());

            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString(context.getResources().getString(R.string.user_profile), jsonresponse);
            editor.apply();
            editor.commit();
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "null";
        } catch (IOException e) {
            // TODO Auto-generated catch block

            e.printStackTrace();
            return "null";
        }catch (JsonSyntaxException jse) {
            jse.printStackTrace();
            return "null";
        }catch (Exception e){
            e.printStackTrace();
            return "null";
        }
        return jsonresponse;
    }
}