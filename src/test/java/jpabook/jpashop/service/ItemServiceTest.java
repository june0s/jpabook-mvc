package jpabook.jpashop.service;

import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class ItemServiceTest {

    @Autowired ItemService itemService;
    @Autowired ItemRepository itemRepository;
    @Autowired EntityManager em;

    @Test
    public void 저장() throws Exception {
        //given
        final Book book = new Book();
        book.setName("JPA");
        book.setPrice(10000);
        book.setStockQuantity(10);
        book.setAuthor("june0");
        book.setIsbn("1234");

        //when
        itemService.saveItem(book);

        //then
        final Item getItem = itemRepository.findOne(book.getId());
        assertEquals("아이템 가격은 같아야 한다.", 10000, getItem.getPrice());
        assertEquals("아이템 저자는 같아야 한다.", "june0", ((Book)getItem).getAuthor());
    }
}