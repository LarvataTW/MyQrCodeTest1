package com.abn.acloud.myqrcodetest1;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.abn.acloud.myqrcodetest1.conf.Conf;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.w3c.dom.Text;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getName();

    private Button scanButton;
    private TextView resultText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        scanButton = (Button) findViewById(R.id.scanButton);
        scanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //啟動 QRCode 掃描程式
                IntentIntegrator integrator = new IntentIntegrator(MainActivity.this);
                integrator.initiateScan();
            }
        });

        resultText = (TextView) findViewById(R.id.resultText);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "onActivityResult");
        super.onActivityResult(requestCode, resultCode, data);

        //QRCode 掃描程式的掃描結果
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        //組合 API (設定UUID)
        String url = String.format(Conf.API_URL, scanningResult.getContents());
        Log.d(TAG, url);
        Log.d(TAG, scanningResult.getContents());

        getResult(url);
    }

    private void getResult(String url){
        AsyncHttpClient client = new AsyncHttpClient();
        client.addHeader("Accept", "application/json");

        //設定email與token
        RequestParams params = new RequestParams();
        params.put("user_email", Conf.USER_EMAIL);
        params.put("user_token", Conf.USER_TOKEN);

        //送出與取得回傳結果
        client.get(url, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.d(TAG, "onFailure");
                resultText.setText("onFailure:" + responseString);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Log.d(TAG, "onSuccess");
                Log.d(TAG, "responseString: " + responseString);
                resultText.setText("onSuccess:" + responseString);
            }
        });
    }
}
