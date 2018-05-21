package ar.com.tuchorc.repository;

import ar.com.tuchorc.model.Item;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface ItemRepository extends CrudRepository<Item, Long> {

	Collection<Item> findAll();

	Item save(Item item);

	Item findOneByItemId(Long itemId);

	Item findOneByDescription(String description);

	boolean exists(Long itemId);

	void delete(Item item);

}
