package no.hvl.dat110.rest.counters;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import static spark.Spark.*;

/**
 * Hello world!
 *
 */
public class App {
	
	static Counters counters = null;
	static List<Todo> todos;
	
	public static void main(String[] args) {

		if (args.length > 0) {
			port(Integer.parseInt(args[0]));
		} else {
			port(8080);
		}

		counters = new Counters();
		List<Todo> todoList = new ArrayList<Todo>();
		
		after((req, res) -> {
  		  res.type("application/json");
  		});
		
		get("/hello", (req, res) -> "Hello World!");
		
        get("/counters", (req, res) -> counters.toJson());
 
        get("/counters/red", (req, res) -> counters.getRed());

        get("/counters/green", (req, res) -> counters.getGreen());

        // TODO: put for green/red and in JSON
        // variant that returns link/references to red and green counter
        put("/counters", (req,res) -> {
        
        	Gson gson = new Gson();
        	
        	counters = gson.fromJson(req.body(), Counters.class);
        
            return counters.toJson();
        	
        });

		get("/todo", (request, response) -> {
			Gson gson = new Gson();

			String jsonString = gson.toJson(todoList);

			return jsonString;
		});

		put("/todo", (request, response) -> {
			Gson gson = new Gson();

			Todo todoUpdate = gson.fromJson(request.body(), Todo.class);
			for (Todo todo:todoList) {
				if (todoUpdate.getSummary().equals(todo.getSummary())) {
					todoList.remove(todo);
					todoList.add(todoUpdate);
					return gson.toJson(todoList);
				}
			}
			return "Could not find the element you were looking for";
		});

		post("/todo", (request, response) -> {
			Gson gson = new Gson();

			Todo todo = gson.fromJson(request.body(), Todo.class);

			todoList.add(todo);

			return gson.toJson(todo);
		});

		delete("/todo/*", (request, response) -> {
			Gson gson = new Gson();

			String[] splat = request.splat();

			Integer in = Integer.parseInt(splat[0]);

			todoList.remove(in-1);

			return gson.toJson(todoList);
		});
    }
    
}
