package fipp.unoeste.movies_api.restcontrollers;

import com.google.gson.Gson;
import com.mongodb.DocumentToDBRefTransformer;
import com.mongodb.client.*;
import fipp.unoeste.movies_api.entities.Movie;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Pattern;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.or;

@RestController
@RequestMapping(value = "/api")
public class MoviesRestController{
    @GetMapping(value = "/testar-conexao")
    public ResponseEntity<Object> testarConexao(){
        return ResponseEntity.ok("Conectado");
    }

    @GetMapping(value = "/find-movies")
    public ResponseEntity<Object> findMovies(@RequestParam String filter){

        String connectionString = "mongodb://localhost:27017";
        MongoClient mongoClient = MongoClients.create(connectionString);
        MongoDatabase database = mongoClient.getDatabase("movies_db");

        MongoCollection<Document> collection = database.getCollection("movies");

        Bson filtro = or(Arrays.asList(eq("title", Pattern.compile(filter+"(?i)")),
                eq("cast", Pattern.compile(filter+"(?i)")),
                eq("extract", Pattern.compile(filter+"(?i)"))));

        FindIterable<Document> iterDoc = collection.find(filtro);

        // insere os Documents em um ArrayList
        ArrayList<Movie> movieList = new ArrayList<>();
        for (Document document : iterDoc) {
            movieList.add(new Gson().fromJson(document.toJson(), Movie.class));
        }
        return ResponseEntity.ok(movieList);
    }
}
