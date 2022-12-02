package es.gabrielzafra.tarea22;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.regex.Pattern;

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

        //Configuramos el comportamiento del boton de "submit"
        Button submitButton = findViewById(R.id.registerSubmitButton);
        submitButton.setOnClickListener(view -> {
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
        //Inicialmente el boton esta desactivado hasta que tengamos entradas validadas
        submitButton.setEnabled(false);
        //Sacamos los campos del formulario de registro
        EditText nameInput = findViewById(R.id.inputName);
        EditText surnamesInput = findViewById(R.id.inputSurnames);
        EditText mailInput = findViewById(R.id.inputEmail);
        EditText phoneInput = findViewById(R.id.inputPhone);
        //Validador de entradas
        TextWatcher validador = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //Validamos
                boolean nameHasText = nameInput.getText().toString().length() > 2;
                boolean surnamesHasText = surnamesInput.getText().toString().length() > 2;
                boolean phoneHasNineDigits = phoneInput.getText().toString().length() == 9;
                boolean emailIsValid = validateEmail(mailInput.getText().toString());
                //Añadimos errores a los campos que fallen la validación
                if(!nameHasText){
                    nameInput.setError(getString(R.string.errorMin2Chars));
                }
                if(!surnamesHasText){
                    surnamesInput.setError(getString(R.string.errorMin2Chars));
                }
                if(!phoneHasNineDigits){
                    phoneInput.setError(getString(R.string.errorNo9Digits));
                }
                if(!emailIsValid){
                    mailInput.setError(getString(R.string.errorIvalidMail));
                }
                //Si todos son válidos activamos el boton de registro
                submitButton.setEnabled(nameHasText &&
                        surnamesHasText &&
                        phoneHasNineDigits &&
                        emailIsValid);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        };
        //Añadimos los listeners a los campos del formulario
        nameInput.addTextChangedListener(validador);
        surnamesInput.addTextChangedListener(validador);
        mailInput.addTextChangedListener(validador);
        phoneInput.addTextChangedListener(validador);
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

    //Función auxiliar para validar la dirección de correo
    private boolean validateEmail(String mail){
        String regex =
                getString(R.string.mailValidationPattern);
        return Pattern.compile(regex).matcher(mail).matches();
    }
}