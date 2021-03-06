package sg.edu.nus.learnandroid.activity.static_fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import sg.edu.nus.learnandroid.R;

/**
 * Created by Yongxue
 */

public class StaticFragmentQuizResultsActivity extends AppCompatActivity {

    public static final String MY_SHAREDPREF_NAME = "StaticFragmentQuizAnsSharedPref";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_static_fragment_quiz_results);

        // Set up custom action bar with back button
        getSupportActionBar().setDisplayOptions(getActionBar().DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.action_bar_with_crossbtn);

        // Set up the back button and title on action bar
        View view = getSupportActionBar().getCustomView();

        TextView actionBarTitleTV = (TextView) findViewById(R.id.action_bar_title_with_crossbtn);
        actionBarTitleTV.setText(R.string.course_fragments);

        ImageView backBtnIV = (ImageView) findViewById(R.id.action_bar_cross_with_crossbtn);
        backBtnIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(getApplicationContext(), StaticFragmentInfoActivity.class);
                startActivity(myIntent);
            }
        });

        WebView webView = (WebView) findViewById(R.id.static_fragment_quiz_result_webview);
        StaticFragmentQuizResultsActivity.JavaScriptInterface myJavaScriptInterface =
                new StaticFragmentQuizResultsActivity.JavaScriptInterface(this, webView);

        webView.getSettings().setJavaScriptEnabled(true);
        WebView.setWebContentsDebuggingEnabled(true);
        webView.addJavascriptInterface(myJavaScriptInterface, "StaticFragmentQuizResults");
        webView.setWebChromeClient(new WebChromeClient());
        webView.loadUrl("file:///android_asset/www/static_fragment_quiz_result.html");
    }

    @Override
    public void onBackPressed() {
        Intent myIntent = new Intent(getApplicationContext(), StaticFragmentInfoActivity.class);
        startActivity(myIntent);
    }

    public class JavaScriptInterface {

        private StaticFragmentQuizResultsActivity parentActivity;
        private WebView webView;

        public JavaScriptInterface(StaticFragmentQuizResultsActivity staticFragmentQuizResultsActivity, WebView mWebView) {
            parentActivity = staticFragmentQuizResultsActivity;
            webView = mWebView;
        }

        @JavascriptInterface
        public String returnQuizAnsToWebView() {

            SharedPreferences prefs = getSharedPreferences(MY_SHAREDPREF_NAME,
                    MODE_PRIVATE);

            String q1 = prefs.getString("q1", "default value");
            String q2 = prefs.getString("q2", "default value");

            return q1 + "," + q2;
        }
    }
}
