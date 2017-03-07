package com.unicornheight.gitrepo;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.IOException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    EditText mEditTextSearchBox;
    TextView mTextViewResult;
    TextView mTextViewDisplay;
    TextView mError;
    ProgressBar mLoadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mEditTextSearchBox = (EditText) findViewById(R.id.editext_id);
        mTextViewDisplay = (TextView) findViewById(R.id.textview_id);
        mTextViewResult = (TextView) findViewById(R.id.textResult);
        mError = (TextView) findViewById(R.id.error_m);
        mLoadingBar = (ProgressBar) findViewById(R.id.loading);
    }

    void searchQuery(){
        String editboxQuery = mEditTextSearchBox.getText().toString();
        URL gitSearchUrl = NetworkClass.buildGitUrl(editboxQuery);
        mTextViewDisplay.setText(gitSearchUrl.toString());
        new GitQuery().execute(gitSearchUrl);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.Search){
            searchQuery();
        }

        return super.onOptionsItemSelected(item);
    }

    private void showData(){
        mError.setVisibility(View.INVISIBLE);
        mTextViewResult.setVisibility(View.VISIBLE);

    }

    private void showError(){
        mTextViewResult.setVisibility(View.INVISIBLE);
        mError.setVisibility(View.VISIBLE);
    }

    public class GitQuery extends AsyncTask<URL, Void, String>{

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            mLoadingBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(URL... params) {

            URL gitSearchUrl = params[0];
            String resultView = null;
            try{
                resultView = NetworkClass.getResponseFromHttpUrl(gitSearchUrl);

            } catch (IOException e) {
                e.printStackTrace();
            }
            return resultView;
        }

        @Override
        protected void onPostExecute(String s) {
            mLoadingBar.setVisibility(View.INVISIBLE);
            if (s !=null && !s.equals("") ){
                showData();
                mTextViewResult.setText(s);
            }else {
                showError();
            }
        }
    }
}
