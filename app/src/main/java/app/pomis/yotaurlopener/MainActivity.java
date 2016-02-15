package app.pomis.yotaurlopener;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    URLOpener opener;
    // Статичная ссылка обновляется каждый раз, когда пересоздаётся активность. Это позволит
    // избежать вылета приложения, в случае, когда AsyncTask будет обращаться к активности по ссылке.
    static MainActivity singleton;
    TextView browsingView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setIds();
        setListeners();
    }

    private void setIds() {
        singleton = this;
        browsingView = ((TextView) findViewById(R.id.browsing_text_view));
    }

    // Назначаем на кнопку обработчик
    private void setListeners() {
        ((Button) findViewById(R.id.open_url_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = ((EditText) findViewById(R.id.edit_text)).getText().toString();
                if (url.length()>0) {
                    opener = new URLOpener(url, singleton);
                    opener.execute();
                }
                else{
                    browsingView.setText("Пустой URL.");
                }

            }
        });
    }
}
