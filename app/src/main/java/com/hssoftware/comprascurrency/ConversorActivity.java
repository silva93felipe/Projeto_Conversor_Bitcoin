package com.hssoftware.comprascurrency;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class ConversorActivity extends AppCompatActivity {

    TextView textViewCM;
    TextView textViewCotacao;
    TextView textViewConversao;
    LinearLayout linearLayout;
    ProgressBar progressBar;

    float valorMoeda = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversor);

        linearLayout = findViewById(R.id.linearLayoutDados);
        textViewCM = findViewById(R.id.textViewCM);
        textViewCotacao = findViewById(R.id.textViewCotacao);
        textViewConversao = findViewById(R.id.textViewConversao);

        progressBar = findViewById(R.id.progressBar);

        Intent intentValor = getIntent();
        valorMoeda = intentValor.getFloatExtra("valor", 0);

        if(valorMoeda > 0){
            mostrarDados(false);
            new ConsultaAPI().execute("https://blockchain.info/ticker");
        }
    }

    public void mostrarDados(boolean mostrar){
        if(mostrar){
            linearLayout.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.INVISIBLE);
        }else{
            linearLayout.setVisibility(View.INVISIBLE);
            progressBar.setVisibility(View.VISIBLE);
        }
    }

    private class ConsultaAPI extends AsyncTask<String, Integer, JSONObject> {

        @Override
        protected JSONObject doInBackground(String... urls) {
            JSONObject retorno = null;
            try {
                retorno = API.requestAPI(urls[0]);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return retorno;
        }

        @Override
        protected void onPostExecute(JSONObject result) {
            try {
                JSONObject objetoBR = result.getJSONObject("BRL");
                double cotacao = objetoBR.getDouble("sell");
                textViewCM.setText("Valor: BTC " + String.format("%.2f", valorMoeda));
                textViewCotacao.setText("Cotação: R$ " + String.format("%.2f", cotacao));
                textViewConversao.setText("Conversão: R$ " + String.format("%.2f", valorMoeda * cotacao));
                mostrarDados(true);
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(ConversorActivity.this, "Falha durante a requisição, verifiquei sua conexão com a Internet", Toast.LENGTH_LONG).show();
            }
        }
    }

}