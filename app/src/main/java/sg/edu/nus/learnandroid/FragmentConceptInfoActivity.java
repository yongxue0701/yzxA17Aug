package sg.edu.nus.learnandroid;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yongxue
 */

public class FragmentConceptInfoActivity extends AppCompatActivity {

    private FragmentConceptInfoRVAdapter adapter;
    private RecyclerView fragmentInfoRV;
    private LinearLayoutManager layoutManager;
    private ImageView loadingAnim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_concept_info);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ScrollView fragmentInfoSV = (ScrollView) findViewById(R.id.fragment_info_SV);
                fragmentInfoSV.setVisibility(View.VISIBLE);
            }
        }, 2000);

        loadingAnim = (ImageView) findViewById(R.id.fragment_concept_loading_anim);
        loadingAnim.setBackgroundResource(R.drawable.spinning_globe_grey);
        AnimationDrawable frameAnimation = (AnimationDrawable) loadingAnim.getBackground();
        frameAnimation.start();

        // Set up custom action bar with back button
        getSupportActionBar().setDisplayOptions(getActionBar().DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.action_bar_without_donebtn);

        // Set up the back button and title on action bar
        View view = getSupportActionBar().getCustomView();

        TextView actionBarTitleTV = (TextView) findViewById(R.id.action_bar_title_without_donebtn);
        actionBarTitleTV.setText(R.string.course_fragments);

        ImageView backBtnIV = (ImageView) findViewById(R.id.action_bar_back_without_donebtn);
        backBtnIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(getApplicationContext(), CourseFragmentActivity.class);
                startActivity(myIntent);
            }
        });

        WebView webView = (WebView) findViewById(R.id.fragment_info_background_info_webview);
        webView.getSettings().setJavaScriptEnabled(true);
        WebView.setWebContentsDebuggingEnabled(true);
        webView.setWebChromeClient(new WebChromeClient());
        webView.loadUrl("file:///android_asset/www/fragment_concept_info.html");

        // Set up recycler view with account buttons
        List<String> fragmentConceptButtonNames = new ArrayList<>();
        fragmentConceptButtonNames.add("Introduction of Fragments");
        fragmentConceptButtonNames.add("Fragment Life Cycle");
        fragmentConceptButtonNames.add("Types of Fragments");
        fragmentConceptButtonNames.add("Quiz");

        adapter = new FragmentConceptInfoRVAdapter(fragmentConceptButtonNames, this, this);
        fragmentInfoRV = (RecyclerView) findViewById(R.id.fragment_info_RV);
        fragmentInfoRV.addItemDecoration(new SimpleDividerItemDecoration(getApplicationContext()));
        layoutManager = new LinearLayoutManager(this, OrientationHelper.VERTICAL, false);

        fragmentInfoRV.setLayoutManager(layoutManager);
        fragmentInfoRV.setAdapter(adapter);
        fragmentInfoRV.setHasFixedSize(true);
    }
}