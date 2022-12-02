package es.gabrielzafra.tarea22;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.Toast;

public class SchoolLanding extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school_landing);
        //Configuramos la toolbar
        Toolbar toolbar = findViewById(R.id.landingToolbar);
        setSupportActionBar(toolbar);
        //Crear un Intent para volver al menu de registro con en Back button
        Intent goRegisterView = new Intent(this, RegisterView.class);
        //Alteramos el comportamiento del Back button
        OnBackPressedCallback backCallback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                startActivity(goRegisterView);
            }
        };
        //Añadimos el callback al Dispatcher que controla los eventos del Back button
        getOnBackPressedDispatcher().addCallback(this, backCallback);

        //Configuramos el WebView

        WebView miterisWebView = findViewById(R.id.miterisWebView);
        miterisWebView.loadUrl(getString(R.string.miterisUrl));
    }

    //Menú para la toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.school_landing_menu,menu);
        return true;
    }
    //Comportamiento de las opciones del menú
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            //Abrir web con navegador externo
            case R.id.landingMenuOptionNav:
                Intent goSysNav = new Intent();
                goSysNav.setAction(Intent.ACTION_VIEW);
                goSysNav.setData(Uri.parse(getString(R.string.miterisUrl)));
                goSysNav.addCategory(Intent.CATEGORY_BROWSABLE);
                //Comprobar si hay un navegador instaldo
                if(goSysNav.resolveActivity(getPackageManager()) != null){
                    startActivity(goSysNav);
                }else{
                    Toast.makeText(this, R.string.errorNoWebBrowser, Toast.LENGTH_SHORT).show();
                }
                return true;
            //Abrir Google Maps pasandole unas cordenadas
            case R.id.landingMenuOptionMap:
                Uri locationUri = Uri.parse(getString(R.string.schoolCoords));
                Intent goGMaps = new Intent(Intent.ACTION_VIEW, locationUri);
                goGMaps.setPackage(getString(R.string.googleMapsPackageName));
                //Comprobar que el telefono tenga una versión compatible de Google Maps
                if(goGMaps.resolveActivity(getPackageManager()) != null){
                    startActivity(goGMaps);
                }else {
                    Toast.makeText(this, R.string.errorNoGoogleMaps, Toast.LENGTH_SHORT).show();
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}