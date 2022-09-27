package com.example.a03_enviarydevolverinformacion;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
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

    //FORMA VIEJA DE RECIBIR INFO
    //CONSTANTE
    private final int DIRECCIONES = 123;
    private final int TRACTORES = 133;

    //LAUNCHERS (FORMA NUEVA DE RECIBIR INFO)
    private ActivityResultLauncher<Intent> launcherDirecciones;
    private ActivityResultLauncher<Intent> launcherTractores;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inicializaVariables();
        //inicializamos los launchers
        inicializaLaunchers();



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
                //FORMA VIEJA DE RECIBIR INFO
                startActivityForResult(intent, DIRECCIONES);
                //FORMA NUEVA DE RECIBIR INFO
                launcherDirecciones.launch(intent);

            }
        });
    }

    private void inicializaLaunchers() {
        //Registrar una actividad de retorno
        //Parte 1 ¿Cómo lanzo la actividad hija?
        //Parte 2 ¿Qué hago cuando mi hija termine?
        launcherDirecciones = registerForActivityResult(
                //1
                new ActivityResultContracts.StartActivityForResult(),
                //2
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if(result.getResultCode() == RESULT_OK){
                            if(result.getData() != null){
                                Bundle bundle = result.getData().getExtras();
                                Direccion dir = (Direccion) bundle.getSerializable("DIR");
                                Toast.makeText(MainActivity.this, dir.toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }
        );
    }

    private void inicializaVariables() {
        txtPassword = findViewById(R.id.txtPasswordMain);
        txtEmail = findViewById(R.id.txtEmailMain);
        btnDesencriptar = findViewById(R.id.btnDesencriptarMain);
        btnCrearDir = findViewById(R.id.btnCrearDirMain);
    }

    //FORMA VIEJA DE RECIBIR INFO
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
                }else{
                    Toast.makeText(this, "No hay DATOS", Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(this, "Ventana Cancelada", Toast.LENGTH_SHORT).show();
            }
        }

    }
}
