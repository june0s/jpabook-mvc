package jpabook.jpashop.service;

import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional
    public void saveItem(Item item) {
        itemRepository.save(item);
    }

    // + 변경 감지에 의해서 값을 update 하는 방법. 더 나은 방법
    @Transactional
    public Item updateItem(Long itemId, String name, int price, int stockQuantity) {
        // findItem 영속 상태. save 안 해줘도 된다!
        final Item findItem = itemRepository.findOne(itemId);
        // 값 변경시, setter 를 사용하기보다 change 와 같이 변경하려는 것이 무엇인지 명확히 하는 편이 좋다!
        findItem.setName(name);
        findItem.setPrice(price);
        findItem.setStockQuantity(stockQuantity);

        return findItem;
    }

    public List<Item> findItems() {
        return itemRepository.findAll();
    }

    public Item findOne(Long itemId) {
        return itemRepository.findOne(itemId);
    }
}
