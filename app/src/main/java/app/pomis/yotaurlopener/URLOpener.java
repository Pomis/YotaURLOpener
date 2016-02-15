package app.pomis.yotaurlopener;

import android.os.AsyncTask;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by romanismagilov on 15.02.16.
 */
public class URLOpener extends AsyncTask {
    String url;
    String responseString;

    public URLOpener(String url, MainActivity context) {
        this.url = url;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        MainActivity.singleton.browsingView.setText("Подключение...");

    }

    @Override
    protected Object doInBackground(Object[] params) {
        HttpClient httpclient = new DefaultHttpClient();
        publishProgress("Подключено. Загрузка данных...");
        // Загружаем ответ на GET-запрос, что и будет html-страницей.
        HttpResponse response;
        responseString = null;
        try {
            response = httpclient.execute(new HttpGet(url));
            StatusLine statusLine = response.getStatusLine();
            if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                response.getEntity().writeTo(out);
                out.close();
                responseString = out.toString();
            } else {
                //Closes the connection.
                response.getEntity().getContent().close();
                throw new IOException(statusLine.getReasonPhrase());
            }
        } catch (Exception e){
            publishProgress("Невозможно открыть данный URL.");
        } finally {
            publishProgress(responseString);
        }
        return null;
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
    }

    @Override
    protected void onProgressUpdate(Object[] values) {
        super.onProgressUpdate(values);
        MainActivity.singleton.browsingView.setText((String)values[0]);
    }
}
