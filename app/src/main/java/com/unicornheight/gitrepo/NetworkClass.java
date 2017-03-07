package com.unicornheight.gitrepo;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by deboajagbe on 3/4/17.
 */

public class NetworkClass {


    final static String GITHUB_URL =
            "https://api.github.com/search/users";

    final static String PARAM_QUERY = "q";

    final static String PARAM_SORT = "sort";

    final static String sortBy = "stars";




    public static URL buildGitUrl(String githubSearchQuery) {
        Uri builtUri = Uri.parse(GITHUB_URL).buildUpon()
                .appendQueryParameter(PARAM_QUERY, githubSearchQuery)
                .appendQueryParameter(PARAM_SORT, sortBy)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }
    public static String getResponseFromHttpUrl(URL githubUrl) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) githubUrl.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }


}
