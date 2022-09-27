package com.example.a03_enviarydevolverinformacion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.a03_enviarydevolverinformacion.modelos.Usuario;

public class DesencriptarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_desencriptar);

        //Para recibir el intent del MainActivity y el bundle
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        //Protegeremos que la mochila no esté vacía
        if(bundle != null){
            /* FORMA FEA
            String password = bundle.getString("PASS");
            String email = bundle.getString("EMAIL");*/

            //Dará error pero le hacemos un CAST de la plantilla que queremos usar
            Usuario user = (Usuario) bundle.getSerializable("USER");
            Toast.makeText(this,user.toString(),Toast.LENGTH_SHORT).show();
        }

    }
}