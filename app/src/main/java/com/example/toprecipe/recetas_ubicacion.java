package com.example.toprecipe;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.SupportMapFragment;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class recetas_ubicacion extends AppCompatActivity implements adapterRecicler.respuestaAlClick{

    public static Activity activity = null;


    private SectionsPagerAdapter mSectionsPagerAdapter;


    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recetas_ubicacion);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        activity = this;



        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        tabLayout.getTabAt(0).setIcon(R.drawable.ic_free_breakfast_black_24dp);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_my_location_black_24dp);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_account_circle_black_24dp);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

        //FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        /*fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_recetas_ubicacion, menu);
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

    @Override
    public void onrespuesAlClick(receta receta) {

        Intent intent = new Intent(getApplicationContext(), VisorPdf.class);
        intent.putExtra("pdf", receta.getPdf());
        startActivity(intent);
    }


    public static class PlaceholderFragment extends Fragment {

        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }


        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            View rootView = null;

            if(getArguments().getInt(ARG_SECTION_NUMBER) == 1){

                rootView = inflater.inflate(R.layout.fragment_recetas, container, false);

                Spinner spin = (Spinner)rootView.findViewById(R.id.spinnerCategoria);

                ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                        R.array.planets_array, android.R.layout.simple_spinner_item);

                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spin.setAdapter(adapter);

               //spin.setOnItemSelectedListener(this);

                ArrayList<receta> arrayRecetas = new ArrayList();
                ConectorBaseDeDatos databaseAccess;
                databaseAccess = ConectorBaseDeDatos.getInstance(getActivity());
                databaseAccess.AbrirConexion();
                arrayRecetas = databaseAccess.todas_las_recetas();
                databaseAccess.CerrarConexcion();

                RecyclerView recicleRecetas = (RecyclerView)rootView.findViewById(R.id.rclRecetas);
                adapterRecicler miAdaptador;

                recicleRecetas.setLayoutManager(new GridLayoutManager(getContext(), 1));
                miAdaptador = new adapterRecicler(getContext(), arrayRecetas);
                recicleRecetas.setAdapter(miAdaptador);

            }
            if(getArguments().getInt(ARG_SECTION_NUMBER) == 2){

                rootView = inflater.inflate(R.layout.fragment_ubicacion, container, false);

            }
            if(getArguments().getInt(ARG_SECTION_NUMBER) == 3){

                rootView = inflater.inflate(R.layout.fragment_perfil, container, false);

                CircleImageView fotoredonda = (CircleImageView)rootView.findViewById(R.id.fotoCirculo);
                TextView txvNombre = (TextView)rootView.findViewById(R.id.txvNombrePerfil);
                TextView txvNombreCompleto = (TextView)rootView.findViewById(R.id.txvNombreCompleto);
                TextView txvFecha = (TextView)rootView.findViewById(R.id.txvFechaNac);
                TextView txvTelefono = (TextView)rootView.findViewById(R.id.txvNumeroTel);
                TextView txvCorreo = (TextView)rootView.findViewById(R.id.txvCorreoEl);
                TextView txvComentarios = (TextView)rootView.findViewById(R.id.txvComentarios);
                Button btnCerrarSesion = (Button)rootView.findViewById(R.id.btnCerrarSesion);

                btnCerrarSesion.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getContext(), login.class);
                        startActivity(intent);
                        recetas_ubicacion.activity.finish();
                    }
                });
                if(login.UsuarioBuscado.getId() == 0){

                    fotoredonda.setImageResource(R.drawable.ic_account_circle_black_24dp);
                }
                else{

                    Picasso.with(getContext()).load("http://192.168.1.140/topRecipes/imagenes/" + login.UsuarioBuscado.getFoto()).error(R.drawable.ic_account_circle_black_24dp).into(fotoredonda);
                    txvNombre.setText(login.UsuarioBuscado.getLogin());
                    txvNombreCompleto.setText(login.UsuarioBuscado.getNombre() + " " + login.UsuarioBuscado.getApellido());
                    txvFecha.setText(login.UsuarioBuscado.getNacimiento());
                    txvTelefono.setText(login.UsuarioBuscado.getTelefono());
                    txvCorreo.setText(login.UsuarioBuscado.getCorreo());
                    txvComentarios.setText(login.UsuarioBuscado.getComentarios());
                }
            }
            return rootView;
        }
    }
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }
    }
}
