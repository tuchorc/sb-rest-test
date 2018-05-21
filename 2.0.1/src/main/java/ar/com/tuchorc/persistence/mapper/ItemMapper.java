package ar.com.tuchorc.persistence.mapper;

import ar.com.tuchorc.persistence.model.Item;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ItemMapper {

	String SELECT = " select id, description from sb_test_item ";

	String INSERT = " insert into sb_test_item (id, description) VALUES (#{id}, #{description}) ";

	String UPDATE = " update sb_test_item set description = #{description} where id = #{id} ";

	String DELETE = " delete from sb_test_item where id = #{id} ";

	/**
	 * Returns all items
	 */
	@Select(SELECT)
	@ResultType(Item.class)
	List<Item> getAll();

	/**
	 * Returns an item by id
	 *
	 * @param id
	 * 		the item id
	 * @return the specified item, otherwise null
	 */
	@Select(SELECT + " where id = #{id} ")
	@ResultType(Item.class)
	Item getById(@Param("id") Long id);

	/**
	 * @param description
	 * @return
	 */
	@Select(SELECT + " where description = #{desc} ")
	@ResultType(Item.class)
	Item getByDescription(@Param("desc") String description);

	/**
	 * Creates an item record
	 *
	 * @param item
	 * 		the item to insert
	 */
	@Insert(INSERT)
	@Options(useGeneratedKeys = true)
	void insert(Item item);

	/**
	 * Updates an item
	 *
	 * @param item
	 * 		the item to update
	 */
	@Update(UPDATE)
	void update(Item item);

	/**
	 * Delete an item by id
	 *
	 * @param id
	 * 		the item id
	 */
	@Delete(DELETE)
	void delete(@Param("id") Long id);

}
