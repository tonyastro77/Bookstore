package fi.haagahelia.Bookstore;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


import fi.haagahelia.Bookstore.domain.Book;
import fi.haagahelia.Bookstore.domain.BookRepository;

@SpringBootApplication
public class BookstoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookstoreApplication.class, args);
	}
	
	
	@Bean public CommandLineRunner demo(BookRepository repository) {
		return (args) -> {
			Book b1 = new Book("Ernest Hemingway", "1232323-21", "A Farewell to Arms", 1929);
			Book b2 = new Book("George Orwell", "2212343-5", "Animal Farm", 1945);
			
			repository.save(b1);
			repository.save(b2);
		};
	}	

}
