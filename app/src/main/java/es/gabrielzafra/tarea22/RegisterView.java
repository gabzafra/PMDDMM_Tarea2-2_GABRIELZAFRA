package es.gabrielzafra.tarea22;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

public class RegisterView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //Configuramos nuestro spinner con el array de modos
        Spinner spinner = (Spinner) findViewById(R.id.spinnerMode);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.modes_array, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        findViewById(R.id.registerSubmitButton).setOnClickListener(view -> {
            //Creamos un usuario nuevo con los datos introducidos por pantalla
            User user = fillUserAttr();
            //Creamos el intent apuntado a la siguiente actividad
            Intent dataPkg = new Intent(this, UserDetail.class);
            //Añadimos el objeto usuario
            dataPkg.putExtra("userData",user);
            //Creamos el cuadro de dialogo
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(R.string.askConfirmation);
            builder.setPositiveButton(R.string.continuar, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    //Lanzamos el intent
                    startActivity(dataPkg);
                }
            });
            builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    //No hacemos nada
                }
            });
            builder.show();
        });
    }

    /**
     * Recoge los datos de los campos EditText y el Spinner y los usa para crear un objeto
     * Usuario con ese estado.
     * @return usuario con los datos del "formulario" de registro
     */
    private User fillUserAttr() {
        String name = ((EditText) findViewById(R.id.inputName)).getText().toString();
        String surnames = ((EditText) findViewById(R.id.inputSurnames)).getText().toString();
        String mail = ((EditText) findViewById(R.id.inputEmail)).getText().toString();
        String phone = ((EditText) findViewById(R.id.inputPhone)).getText().toString();
        Spinner spinner = (Spinner) findViewById(R.id.spinnerMode);
        boolean isOnline = spinner.getSelectedItem().toString().equals(getString(R.string.online));
        return new User(name,surnames,mail,phone,isOnline);
    }
}