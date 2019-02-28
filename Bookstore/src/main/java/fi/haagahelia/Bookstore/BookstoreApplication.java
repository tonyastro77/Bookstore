package fi.haagahelia.Bookstore;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


import fi.haagahelia.Bookstore.domain.Book;
import fi.haagahelia.Bookstore.domain.BookRepository;
import fi.haagahelia.Bookstore.domain.Category;
import fi.haagahelia.Bookstore.domain.CategoryRepository;

@SpringBootApplication
public class BookstoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookstoreApplication.class, args);
	}
	
	
	@Bean public CommandLineRunner demo(BookRepository brepository, CategoryRepository crepository) {
		return (args) -> {
			Category c1 = new Category("Suspense fiction");
			Category c2 = new Category("Crime fiction");
			Category c3 = new Category("Detective fiction");
			Category c4 = new Category("Biography");
			
			crepository.save(c1);
			crepository.save(c2);
			crepository.save(c3);
			crepository.save(c4);
			
			Book b1 = new Book("Ernest Hemingway", "1232323-21", "A Farewell to Arms", 1929, crepository.findByName("Suspense fiction").get(0));
			Book b2 = new Book("George Orwell", "2212343-5", "Animal Farm", 1945, crepository.findByName("Detective fiction").get(0));
			
			brepository.save(b1);
			brepository.save(b2);
			
			
			
		};
	}	

}
