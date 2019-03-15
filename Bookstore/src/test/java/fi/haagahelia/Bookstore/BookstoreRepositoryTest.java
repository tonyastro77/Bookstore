package fi.haagahelia.Bookstore;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import fi.haagahelia.Bookstore.domain.Book;
import fi.haagahelia.Bookstore.domain.BookRepository;
import fi.haagahelia.Bookstore.domain.Category;

@RunWith(SpringRunner.class)
@DataJpaTest
public class BookstoreRepositoryTest {

	@Autowired
	private BookRepository repository;
	
	@Test
	public void findByTitleReturnBook () {
		List<Book> books = repository.findByTitle("2666");
		
		assertThat(books).hasSize(1);
		assertThat(books.get(0).getAuthor()).isEqualTo("Roberto Bolano");
	}
	
	@Test
	public void createNewBook() {
		Book book = new Book("Naomi Klein", "21232123-8", "This changes everything", 2014, new Category("Non-fiction"));
		repository.save(book);
		assertThat(book.getId()).isNotNull();
	}
}
