package com.example.a03_enviarydevolverinformacion;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.a03_enviarydevolverinformacion.modelos.Direccion;
import com.example.a03_enviarydevolverinformacion.modelos.Usuario;

public class MainActivity extends AppCompatActivity {

    private EditText txtPassword;
    private EditText txtEmail;
    private Button btnDesencriptar;
    private Button btnCrearDir;

    //PARA DEVOLVER
    //CONSTANTE
    private final int DIRECCIONES = 123;
    private final int TRACTORES = 133;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inicializaVariables();


        btnDesencriptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String password = txtPassword.getText().toString();
                String email = txtEmail.getText().toString();
                //Creamos la actividad dandole al paquete principal, new -> activity -> empty activity
                //Creamos el intent
                Intent intent = new Intent(MainActivity.this, DesencriptarActivity.class);
                //Creamos la mochila de dora la exploradora:
                Bundle bundle = new Bundle();
                //Le tenemos que dar una "key" y el valor
                /* ESTA ES LA FORMA FEA
                bundle.putString("PASS", password );
                bundle.putString("EMAIL", email);
                */
                //Así mandamos el objeto usuario al completo, poniendo en la clase que se implementa la serialización
                bundle.putSerializable("USER", new Usuario(email, password));

                //cargamos la mochila, extraS
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        btnCrearDir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,CreateDirActivity.class);
                startActivityForResult(intent, DIRECCIONES);


            }
        });
    }
    private void inicializaVariables() {
        txtPassword = findViewById(R.id.txtPasswordMain);
        txtEmail = findViewById(R.id.txtEmailMain);
        btnDesencriptar = findViewById(R.id.btnDesencriptarMain);
        btnCrearDir = findViewById(R.id.btnCrearDirMain);
    }

    //Esto lo aplicamos para devolver, con el startActivityForResult lo necesitaremos.
    /**
     * Se activa al retornar de un startActivityForResult y dispara las acciones necesarias
     * @param requestCode -> identificador de la ventana que se ha cerrado (El tipo de dato que retorna)
     * @param resultCode -> el modo en que se ha cerrado la ventana
     * @param data -> datos retornados (Intent con un Bundle dentro)
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //qué ventana se ha cerrado
        if(requestCode == DIRECCIONES){
            //cómo se ha cerrado la ventana
            if(resultCode == RESULT_OK){
                //data es el INTENT que vuelve
                if(data != null){
                    Bundle bundle = data.getExtras();
                    Direccion dir = (Direccion) bundle.getSerializable("DIR");
                    Toast.makeText(this, dir.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        }

    }
}
