package com.nasyarobby.droidnewsreader;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class HttpHelper {
    class GetAsyncTask extends AsyncTask<URL, Integer, String>{

        @Override
        protected String doInBackground(URL... urls) {
                    StringBuffer content = new StringBuffer();
                    try {
                        HttpURLConnection connection = (HttpURLConnection) urls[0].openConnection();
                        connection.setRequestMethod("GET");

                        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                        String inputLine;
                        while ((inputLine = in.readLine()) != null) {
                            content.append(inputLine);
                        }
                        in.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return content.toString();
        }
    }

    public String get(String url) throws IOException {
        URL url2 = new URL(url);
        return get(url2);
    }

    public String get(URI uri) throws IOException {
        URL url2 = new URL(uri.toString());
        return get(url2);
    }

    /**
     * @param url2 the url to get
     * @return content in string
     * @throws IOException if error when getting the content
     * @throws ProtocolException if protocol is invalid
     */
    private String get(URL url2) throws IOException, ProtocolException {
        GetAsyncTask task = new GetAsyncTask();
        task.execute(url2);
        String content = null;
        try {
            content = task.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return content;
    }

    public static URI appendUri(String uri, String appendQuery) throws URISyntaxException {
        URI oldUri = new URI(uri);

        return appendUri(oldUri, appendQuery);
    }

    public static URI appendUri(URI uri, String appendQuery) throws URISyntaxException {
        URI oldUri = uri;

        String newQuery = oldUri.getQuery();
        if (newQuery == null) {
            newQuery = appendQuery;
        } else {
            newQuery += "&" + appendQuery;
        }

        URI newUri = new URI(oldUri.getScheme(), oldUri.getAuthority(), oldUri.getPath(), newQuery,
                oldUri.getFragment());

        return newUri;
    }
}
