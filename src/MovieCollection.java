import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.ArrayList;

public class MovieCollection
{
    private ArrayList<Movie> movies;
    private final Scanner scanner;

    public MovieCollection(String fileName)
    {
        importMovieList(fileName);
        scanner = new Scanner(System.in);
    }

    public ArrayList<Movie> getMovies()
    {
        return movies;
    }

    public void menu()
    {
        String menuOption = "";

        System.out.println("Welcome to the movie collection!");
        System.out.println("Total: " + movies.size() + " movies");

        while (!menuOption.equals("q"))
        {
            System.out.println("------------ Main Menu ----------");
            System.out.println("- search (t)itles");
            System.out.println("- search (k)eywords");
            System.out.println("- search (c)ast");
            System.out.println("- see all movies of a (g)enre");
            System.out.println("- list top 50 (r)ated movies");
            System.out.println("- list top 50 (h)igest revenue movies");
            System.out.println("- (q)uit");
            System.out.print("Enter choice: ");
            menuOption = scanner.nextLine();

            if (!menuOption.equals("q"))
            {
                processOption(menuOption);
            }
        }
    }

    private void processOption(String option)
    {
        switch (option) {
            case "t" -> searchTitles();
            case "c" -> searchCast();
            case "k" -> searchKeywords();
            case "g" -> listGenres();
            case "r" -> listHighestRated();
            case "h" -> listHighestRevenue();
            default -> System.out.println("Invalid choice!");
        }
    }

    private void searchTitles()
    {
        System.out.print("Enter a tital search term: ");
        String searchTerm = scanner.nextLine();

        // prevent case sensitivity
        searchTerm = searchTerm.toLowerCase();

        // arraylist to hold search results
        ArrayList<Movie> results = new ArrayList<Movie>();

        // search through ALL movies in collection
        for (Movie movie : movies) {
            String movieTitle = movie.getTitle();
            movieTitle = movieTitle.toLowerCase();

            if (movieTitle.contains(searchTerm)) {
                //add the Movie objest to the results list
                results.add(movie);
            }
        }

        // sort the results by title
        sortResults(results);

        // now, display them all to the user
        for (int i = 0; i < results.size(); i++)
        {
            String title = results.get(i).getTitle();

            // this will print index 0 as choice 1 in the results list; better for user!
            int choiceNum = i + 1;

            System.out.println("" + choiceNum + ". " + title);
        }

        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        Movie selectedMovie = results.get(choice - 1);

        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void sortResults(ArrayList<Movie> listToSort)
    {
        for (int j = 1; j < listToSort.size(); j++)
        {
            Movie temp = listToSort.get(j);
            String tempTitle = temp.getTitle();

            int possibleIndex = j;
            while (possibleIndex > 0 && tempTitle.compareTo(listToSort.get(possibleIndex - 1).getTitle()) < 0)
            {
                listToSort.set(possibleIndex, listToSort.get(possibleIndex - 1));
                possibleIndex--;
            }
            listToSort.set(possibleIndex, temp);
        }
    }

    private void displayMovieInfo(Movie movie)
    {
        System.out.println();
        System.out.println("Title: " + movie.getTitle());
        System.out.println("Tagline: " + movie.getTagline());
        System.out.println("Runtime: " + movie.getRuntime() + " minutes");
        System.out.println("Year: " + movie.getYear());
        System.out.println("Directed by: " + movie.getDirector());
        System.out.println("Cast: " + movie.getCast());
        System.out.println("Overview: " + movie.getOverview());
        System.out.println("User rating: " + movie.getUserRating());
        System.out.println("Box office revenue: " + movie.getRevenue());
    }

    private void searchCast()
    {
        System.out.println("Enter a cast member: ");
        String searchTerm = scanner.nextLine();

        searchTerm = searchTerm.substring(0,1).toUpperCase() + searchTerm.substring(1).toLowerCase();

        ArrayList<Movie> results = new ArrayList<Movie>();
        ArrayList<String> castMembers = new ArrayList<String>();

        for (int i = 0; i < movies.size(); i++) {
            String movieCast = movies.get(i).getCast();
            String[] cast = movieCast.split("\\|");
            for(int a = 0; a< cast.length;a++){
                if (cast[a].contains(searchTerm)){
                    if(castMembers.size() == 0){
                        castMembers.add(cast[a]);
                    }
                    else{
                        int count = 0;
                        for (int b = 0; b < castMembers.size();b++){
                            if (castMembers.get(b).equals(cast[a])){
                                count++;
                            }
                            if (b == castMembers.size()-1 && count == 0){
                                castMembers.add(cast[a]);
                            }
                        }
                    }

                }
            }
        }
        Collections.sort(castMembers);
        for (int i = 1;i<=castMembers.size();i++){
            System.out.println(i + ". " + castMembers.get(i-1));
        }
        System.out.println("Which cast member would you like to learn more about?");
        System.out.print("Enter number: ");
        int choice = scanner.nextInt();
        scanner.nextLine();
        searchTerm = castMembers.get(choice-1);

        for (Movie movie : movies) {
            String movieCast = movie.getCast();
            String[] cast = movieCast.split("\\|");
            for (String s : cast) {
                if (s.equals(searchTerm)) {
                    results.add(movie);
                }
            }
        }

        // sort the results by title
        sortResults(results);

        // now, display them all to the user
        for (int i = 0; i < results.size(); i++)
        {
            String title = results.get(i).getTitle();

            // this will print index 0 as choice 1 in the results list; better for user!
            int choiceNum = i + 1;

            System.out.println("" + choiceNum + ". " + title);
        }

        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        int ans = scanner.nextInt();
        scanner.nextLine();

        Movie selectedMovie = results.get(ans - 1);

        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();

    }

    private void searchKeywords()
    {
        System.out.print("Enter a keyword search term: ");
        String searchTerm = scanner.nextLine();

        // prevent case sensitivity
        searchTerm = searchTerm.toLowerCase();

        // arraylist to hold search results
        ArrayList<Movie> results = new ArrayList<Movie>();

        // search through ALL movies in collection
        for (Movie movie : movies) {
            String movieTitle = movie.getKeywords();
            movieTitle = movieTitle.toLowerCase();

            if (movieTitle.contains(searchTerm)) {
                //add the Movie objest to the results list
                results.add(movie);
            }
        }

        // sort the results by title
        sortResults(results);

        // now, display them all to the user
        for (int i = 0; i < results.size(); i++)
        {
            String title = results.get(i).getTitle();

            // this will print index 0 as choice 1 in the results list; better for user!
            int choiceNum = i + 1;

            System.out.println("" + choiceNum + ". " + title);
        }

        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        Movie selectedMovie = results.get(choice - 1);

        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }


    private void listGenres() {


        ArrayList<Movie> names = new ArrayList<Movie>();
        ArrayList<String> hello = new ArrayList<String>();
        ArrayList<String> sorted = new ArrayList<String>();

        for (Movie movie : movies) {
            String cast = movie.getGenres();
            String[] castMember = cast.split("\\|");
            Collections.addAll(hello, castMember);
        }
        for (int i = 0; i < hello.size(); i++) {
            for (int s = i + 1; s < hello.size(); s++) {
                if (Objects.equals(hello.get(i), hello.get(s))) {
                    hello.remove(s);
                    s--;
                }
            }
            Collections.sort(hello);

        }
        for (int i = 0; i < hello.size(); i++) {
            System.out.println(i + 1 + ". " + hello.get(i));
        }
        System.out.println("Which genre would you like to learn more about?");
        System.out.print("Enter number: ");
        int genre = scanner.nextInt();
        scanner.nextLine();
        for (Movie movie : movies) {
            if (movie.getGenres().contains(hello.get(genre - 1))) {
                names.add(movie);
            }
        }
        for (Movie name : names) {
            sorted.add(name.getTitle());
        }
        Collections.sort(sorted);
        for(int l=0;l<sorted.size();l++){
            System.out.println(l+1+". "+sorted.get(l));
        }
        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");


        int choice = scanner.nextInt();
        scanner.nextLine();
        String each=sorted.get(choice-1);
        System.out.println(each);
        int index=0;
        for(int y=0;y<movies.size();y++){
           if(movies.get(y).getTitle().equals(each)){
               index=y;
           }
        }

        Movie selectedMovie =movies.get(index);


        displayMovieInfo(selectedMovie);


        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }


    private void listHighestRated()
    {
        int count=1;
        ArrayList<String> hello = new ArrayList<String>();
        ArrayList<String> onlyMovie= new ArrayList<String>();
        for (Movie movie : movies) {
            String cast = Double.toString(movie.getUserRating());
            String[] castMember = cast.split("\\|");
            hello.addAll(Arrays.asList(castMember));
        }
        Collections.sort(hello);
        ArrayList<String> sorted = new ArrayList<String>();
        for(int i=4948;i<hello.size();i++){
            sorted.add(hello.get(i));
        }


        for(int i=sorted.size()-1;i>0;i--){
            count++;
        }
        ArrayList<String> moviePrating = new ArrayList<String>(50);
        for(int i=sorted.size()-1;i>0;i--){
            for (Movie movie : movies) {
                if (movie.getUserRating() == Double.parseDouble(sorted.get(i))) {
                    moviePrating.add(movie.getTitle() + " " + sorted.get(i));
                    onlyMovie.add(movie.getTitle());
                }
            }
        }
        for (int i = 0; i < moviePrating.size(); i++) {
            for (int s = i + 1; s < moviePrating.size(); s++) {
                if (Objects.equals(moviePrating.get(i), moviePrating.get(s))) {
                    moviePrating.remove(s);
                    s--;
                }
            }
        }
        for (int o= 0; o < onlyMovie.size(); o++) {
            for (int s = o + 1; s < onlyMovie.size(); s++) {
                if (Objects.equals(onlyMovie.get(o), onlyMovie.get(s))) {
                    onlyMovie.remove(s);
                    s--;
                }
            }

        }
        ArrayList<String> top50=  new ArrayList<String>();
        for(int n=0;n<50;n++){
            top50.add(moviePrating.get(n));
        }
        for(int r=0;r<top50.size();r++){
            System.out.println(r+1+". "+top50.get(r));
        }
        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        int choice = scanner.nextInt();
        scanner.nextLine();
        String each=onlyMovie.get(choice-1);
        int index=0;
        for(int y=0;y<movies.size();y++){
            if(movies.get(y).getTitle().equals(each)){
                index=y;
            }
        }
        Movie selectedMovie = movies.get(index);


        displayMovieInfo(selectedMovie);


        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }


    private void listHighestRevenue()
    {
        for(int i=0;i<50;i++){
            System.out.println(i+1+". "+movies.get(i).getTitle()+" "+movies.get(i).getRevenue());
        }
        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");


        int choice = scanner.nextInt();
        scanner.nextLine();


        Movie selectedMovie = movies.get(choice - 1);


        displayMovieInfo(selectedMovie);


        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();



    }


    private void importMovieList(String fileName)
    {
        try
        {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = bufferedReader.readLine();


            movies = new ArrayList<Movie>();


            while ((line = bufferedReader.readLine()) != null)
            {
                String[] movieFromCSV = line.split(",");


                String title = movieFromCSV[0];
                String cast = movieFromCSV[1];
                String director = movieFromCSV[2];
                String tagline = movieFromCSV[3];
                String keywords = movieFromCSV[4];
                String overview = movieFromCSV[5];
                int runtime = Integer.parseInt(movieFromCSV[6]);
                String genres = movieFromCSV[7];
                double userRating = Double.parseDouble(movieFromCSV[8]);
                int year = Integer.parseInt(movieFromCSV[9]);
                int revenue = Integer.parseInt(movieFromCSV[10]);


                Movie nextMovie = new Movie(title, cast, director, tagline, keywords, overview, runtime, genres, userRating, year, revenue);
                movies.add(nextMovie);
            }
            bufferedReader.close();
        }
        catch(IOException exception)
        {
            // Print out the exception that occurred
            System.out.println("Unable to access " + exception.getMessage());
        }
    }
}
