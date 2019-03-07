package fi.haagahelia.Bookstore;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


import fi.haagahelia.Bookstore.domain.Book;
import fi.haagahelia.Bookstore.domain.BookRepository;
import fi.haagahelia.Bookstore.domain.Category;
import fi.haagahelia.Bookstore.domain.CategoryRepository;
import fi.haagahelia.Bookstore.domain.User;
import fi.haagahelia.Bookstore.domain.UserRepository;

@SpringBootApplication
public class BookstoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookstoreApplication.class, args);
	}
	
	
	@Bean 
	public CommandLineRunner demo(BookRepository brepository, CategoryRepository crepository, UserRepository urepository) {
		return (args) -> {
			Category c1 = new Category("Suspense fiction");
			Category c2 = new Category("Crime fiction");
			Category c3 = new Category("Detective fiction");
			Category c4 = new Category("Biography");
			Category c5 = new Category("Humour");
			
			crepository.save(c1);
			crepository.save(c2);
			crepository.save(c3);
			crepository.save(c4);
			crepository.save(c5);
			
			Book b1 = new Book("Roberto Bolano", "1232323-21", "2666", 2004, crepository.findByName("Suspense fiction").get(0));
			Book b2 = new Book("Tom Hodgkinson", "2212343-5", "How to be idle", 2004, crepository.findByName("Humour").get(0));
			Book b3 = new Book("Donna Leon", "2212343-5", "Beastly things", 2012, crepository.findByName("Crime fiction").get(0));
			
			brepository.save(b1);
			brepository.save(b2);
			brepository.save(b3);
			
			User user1 = new User("user", "$2a$06$3jYRJrg0ghaaypjZ/.g4SethoeA51ph3UD4kZi9oPkeMTpjKU5uo6", "user1@email.com", "USER");
			User user2 = new User("admin", "$2a$10$0MMwY.IQqpsVc1jC8u7IJ.2rT8b0Cd3b3sfIBGV2zfgnPGtT4r0.C", "user2@email.com", "ADMIN");
			urepository.save(user1);
			urepository.save(user2);
			
			
			
		};
	}	

}
