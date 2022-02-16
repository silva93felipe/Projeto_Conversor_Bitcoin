package com.hssoftware.comprascurrency;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button buttonCalcular;
    EditText editTextValor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextValor = findViewById(R.id.editTextValor);

        buttonCalcular = findViewById(R.id.buttonCalcular);
        buttonCalcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                float valorMoeda = 0;
                try{
                    valorMoeda = Float.valueOf(editTextValor.getText().toString());
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
                if(valorMoeda > 0){
                    Intent intentConversor = new Intent(MainActivity.this, ConversorActivity.class);
                    intentConversor.putExtra("valor", valorMoeda);
                    startActivity(intentConversor);
                }else{
                    Toast.makeText(MainActivity.this, "Informe um valor", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}