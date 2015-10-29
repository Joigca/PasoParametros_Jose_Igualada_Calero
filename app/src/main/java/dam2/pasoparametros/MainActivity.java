package dam2.pasoparametros;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    Button btnEnviarDatos;
    EditText nombre, ETSb;
    RadioGroup rGSexo;
    TextView datos;
    SeekBar SBPuntua;
    RatingBar RBNota;
    Switch SWConducir;
    int nota = 0;


    final int SUBACTIVITY_1=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnEnviarDatos = (Button) findViewById(R.id.btnEnviarDatos);
        nombre = (EditText) findViewById(R.id.etNombre);
        rGSexo = (RadioGroup) findViewById(R.id.rgSexo);
        datos = (TextView) findViewById(R.id.tvDatosRecibidos);
        ETSb = (EditText) findViewById(R.id.etSB);
        ETSb.setEnabled(false);
        SBPuntua = (SeekBar) findViewById(R.id.sBPuntua);
        RBNota = (RatingBar) findViewById(R.id.rBNota);
        SWConducir = (Switch) findViewById(R.id.swConducir);

        modSeekbar();

        //Afegim un Listener al botó
        btnEnviarDatos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(datosCorrectos()){
                    //Cridaré al meu Subactivity
                    Intent i = new Intent(getApplicationContext(),subActivity.class);

                    //Cree un objecte Bundle per a enviar els paràmetres
                    Bundle b = new Bundle();
                    b.putString("Nombre",nombre.getText().toString());  // Afegim el paràmetre NOM

                    // Afegim el paràmetre SEXE
                    switch(rGSexo.getCheckedRadioButtonId()){
                        case R.id.rbHombre:
                            b.putString("Sexo","Hombre");
                            break;
                        case R.id.rbMujer:
                            b.putString("Sexo","Mujer");
                            break;
                        default:
                            b.putString("Sexo", "Indefinido");
                    }

                    if(SWConducir.isChecked()){
                        b.putString("Carnet","1");
                    }else{
                        b.putString("Carnet", "0");
                    }

                    b.putString("Estrellas", "" + RBNota.getRating());
                    b.putString("Nota", "" + SBPuntua.getProgress());


                    i.putExtras(b);  //Afegisc l'objecte Bundle a l'Intent
                    startActivityForResult(i, SUBACTIVITY_1); // Cride al subactivity, amb l'Intent (que conté el Bundle)
                }else{
                    Toast.makeText(getApplicationContext(),"Debes rellenar todos los datos",Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    public void modSeekbar(){

        SBPuntua.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                nota = SBPuntua.getProgress();
                ETSb.setText(""+nota);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

    }

    public boolean datosCorrectos(){
        if(nombre.getText().length()<=0){
            return false;
        }else{
            if(rGSexo.getCheckedRadioButtonId()==-1){
                return false;
            }else{
                return true;
            }
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case SUBACTIVITY_1:
                gestionaSubActivity1(resultCode,data);
                break;
        }
    }

    public void gestionaSubActivity1(int resultCode, Intent data){

        if(resultCode==RESULT_OK){
            String missatge="";
            Bundle b = data.getExtras();
            int edat = b.getInt("Edad");
            if (edat<18) missatge=new String("Estas hecho un chaval!");
            if ((edat>=18)&&(edat<25))  missatge=new String("Crack!");
            if (edat>=25)  missatge=new String("ai,ai,ai..");
            //Posem
            datos.setText(missatge.toString());
            desactivaComponentes();
        }else{
            Toast.makeText(getApplicationContext(),"Error en el SubActivity 1",Toast.LENGTH_LONG).show();
        }
    }

    public void desactivaComponentes(){
        nombre.setEnabled(false);  // desactivem el camp nom
        rGSexo.setEnabled(false);
        //REcorreguem TOTS els radiobutton del radioGroup, per a anar desactivant-los un a un
        for (int i=0;i<rGSexo.getChildCount();i++){
            rGSexo.getChildAt(i).setEnabled(false);
        }
        btnEnviarDatos.setEnabled(false); //desactivem el botó
        SBPuntua.setEnabled(false);
        RBNota.setEnabled(false);;
        ETSb.setEnabled(false);;
        SWConducir.setEnabled(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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