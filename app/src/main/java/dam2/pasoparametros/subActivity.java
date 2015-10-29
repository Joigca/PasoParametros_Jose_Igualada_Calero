package dam2.pasoparametros;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class subActivity extends Activity {
    String nombre;  //per guardar les dades rebudes de l'Activity principal
    String sexo;  //per guardar les dades rebudes de l'Activity principal
    String carnCond;
    String notaPunt;
    String estrPunt;

    String mensaje;
    String extras;

    TextView tVBienvenida, tVExtras;
    Button fin;
    EditText edad, nota, estrellas;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);
        //Enllacem el TextView de l'XML (activity_sub.xml) amb un Objecte TextView de Java
        tVBienvenida = (TextView) findViewById(R.id.tvBienvenida);
        // Enllacem el botó acabar de l'XML amb un objecte Botó de Java
        fin = (Button) findViewById(R.id.btnFin);
        //Enllacem el EditText de l'xml amb un objecte EditText de Java
        edad = (EditText) findViewById(R.id.edad);

        nota = (EditText) findViewById(R.id.txtPunt);
        estrellas =(EditText) findViewById(R.id.txtEstrellas);

        //tVExtras = (TextView) findViewById(R.id.tvExtras);

        // Recollim els paràmatres que venen de l'Activity principal (si en ve algun)
        Bundle b = getIntent().getExtras();
        if (b!=null){
            nombre = b.getString("Nombre");
            sexo = b.getString("Sexo");
            estrPunt = b.getString("Estrellas");
            notaPunt = b.getString("Nota");
            carnCond = b.getString("Carnet");


            if(sexo.compareTo("Hombre")==0){
                mensaje="Encantado Sr. "+nombre;
            }else{
                mensaje="Encantado Srta. "+nombre;
            }

            if(carnCond.equals("1")){
                mensaje = mensaje + ", enhorabuena por el carnet";
            }else{
                mensaje = mensaje + ", tendra que probar suerte otra vez";
            }

            nota.setText(notaPunt);
            estrellas.setText(estrPunt);

            tVBienvenida.setText(mensaje.toString());
        }else{
            tVBienvenida.setText("No hemos recibido los datos.");
        }

        // Li afegim un Listener al botó acabar
        fin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edad.getText().length()<=0){
                    Toast.makeText(getApplicationContext(),"Debe rellenar el campo de edad",Toast.LENGTH_LONG).show();
                }else{  //tot esta correcte
                    Intent i = getIntent();
                    i.putExtra("Edad",Integer.parseInt(edad.getText().toString()));  // Afegim un paràmetre més al bundle
                    setResult(RESULT_OK, i);  //Establim El resultat del subActivity, coma a que ha anat tot be
                    finish();   // Indiquem que es deu tancar el subActivity
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sub, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
