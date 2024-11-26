package com.example.mymovies;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BuscaFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static List<Movie> filmes = new ArrayList<>();
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private ListView listView;
    private EditText etFiltro;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public BuscaFragment() {
        // Required empty public constructor
    }

    public static BuscaFragment newInstance(String param1, String param2) {
        BuscaFragment fragment = new BuscaFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_busca, container, false);

        listView = view.findViewById(R.id.listView);
        etFiltro = view.findViewById(R.id.etFiltro);

        listView.setOnItemClickListener((parent, view1, position, id) -> {
            Movie selectedMovie = filmes.get(position);

            // Criar uma nova instância do MovieDetailFragment com os argumentos do filme
            MovieDetailFragment detailFragment = MovieDetailFragment.newInstance(
                    selectedMovie.title,
                    ""+selectedMovie.year,
                    selectedMovie.genres,
                    selectedMovie.thumbnail,
                    selectedMovie.extract
            );

            // Realizar a transação para substituir o fragmento atual
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(android.R.id.content, detailFragment) // Substitui o layout atual pelo fragmento
                    .addToBackStack(null) // Adiciona à pilha para permitir voltar
                    .commit();
        });

        etFiltro.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int before, int after) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int after) {}

            @Override
            public void afterTextChanged(Editable editable) {
                if(editable.toString().length() >= 2){
                    consumirApi();
                }
            }
        });

        consumirApi();
        return view;
    }

    public void carregarMovies(){
        listView.setAdapter(new MovieAdapter(getActivity(), R.layout.item_movie, filmes));
    }

    private void openMovieDetail(Movie movie) {
        MovieDetailFragment detailFragment = MovieDetailFragment.newInstance(
                movie.title,
                ""+movie.year,
                movie.genres,
                movie.thumbnail,
                movie.extract
        );

        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.detailfragment, detailFragment)
                .addToBackStack(null)
                .commit();
    }

    private void consumirApi() {
        String filtro = etFiltro.getText().toString();
        Call<List<Movie>> call = new RetrofitConfig().getMovieService().getMovie(filtro);

        call.enqueue(new Callback<List<Movie>>() {
            @Override
            public void onResponse(Call<List<Movie>> call, Response<List<Movie>> response) {
                if (response.body() != null) {
                    filmes = response.body();
                    carregarMovies();
                }
            }

            @Override
            public void onFailure(Call<List<Movie>> call, Throwable t) {
                Log.e("Erro:", t.getMessage());
            }
        });
    }
}