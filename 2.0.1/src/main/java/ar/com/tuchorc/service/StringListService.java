package ar.com.tuchorc.service;

import ar.com.tuchorc.exception.ErrorCode;
import ar.com.tuchorc.exception.ServiceException;
import ar.com.tuchorc.persistence.mapper.ItemMapper;
import ar.com.tuchorc.persistence.model.Item;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 */
@Service
public class StringListService {

    private final Logger logger = Logger.getLogger(StringListService.class.getCanonicalName());

    @Autowired
    ItemMapper itemMapper;

    public StringListService() {
    }

    /**
     * Returns a single item by id
     *
     * @return the requested item
     * @throws ServiceException if unexpected error occurs
     */
    public Item getById(Long id) throws ServiceException {
        try {
            return itemMapper.getById(id);
        } catch (Exception _ex) {
            logger.log(Logger.Level.FATAL, "Unexpected exception retrieving record by id: ", _ex);
            throw new ServiceException(_ex.getMessage(), ErrorCode.OTHER);
        }
    }

    /**
     * Returns all records
     *
     * @return the list of items
     * @throws ServiceException if unexpected error occurs
     */
    public List<Item> getAll() throws ServiceException {
        return this.getAll(true);
    }

    /**
     * Returns all records
     *
     * @return the list of items
     * @throws ServiceException if unexpected error occurs
     */
    public List<Item> getAllReverse() throws ServiceException {
        return this.getAll(false);
    }

    /**
     * Returns all records ordered descending or ascending
     *
     * @return the list of items
     * @throws ServiceException if unexpected error occurs
     */
    private List<Item> getAll(Boolean desc) throws ServiceException {
        try {
            List<Item> list = itemMapper.getAll();
            if (desc) {
                list.sort((o1, o2) -> o1.getDescription().compareTo(o2.getDescription()));
            } else {
                list.sort((o1, o2) -> o2.getDescription().compareTo(o1.getDescription()));
            }

            return list;
        } catch (Exception _ex) {
            logger.log(Logger.Level.FATAL, "Unexpected exception retrieving all records: ", _ex);
            throw new ServiceException(_ex.getMessage(), ErrorCode.OTHER);
        }
    }

    /**
     * Returns all records pagianted
     *
     * @return the list of items
     * @throws ServiceException if unexpected error occurs
     */
    public List<Item> getAll(Integer offset, Integer recordCount) throws ServiceException {
        return null;
    }


    /**
     * Creates a data set record
     *
     * @param item the item to insert
     * @return the new item or the original item if already exists in the list
     * @throws ServiceException if record fails the validation (empty ID and description less than 25 characters),
     *                          or an unexpected error occurs
     */
    public Item insert(Item item) throws ServiceException {
        // Validate record
        // ID
        if ((item.getId() != null) && (!item.getId().equals(0L))) {
            logger.log(Logger.Level.ERROR, "ID is auto generated");
            throw new ServiceException("ID should be empty", ErrorCode.INVALID_ENTITY, this.getClass().getName(), "insert");
        }
        item.setId(null); // in case it is set to 0
        // DESCRIPTION
        if ((item.getDescription() == null) || (item.getDescription().trim().isEmpty())) {
            logger.log(Logger.Level.ERROR, "Missing description");
            throw new ServiceException("Missing description", ErrorCode.INVALID_ENTITY, this.getClass().getName(), "insert");
        }
        normalizeDescription(item);
        if (item.getDescription().length() > 25) {
            logger.log(Logger.Level.ERROR, "Description too large");
            throw new ServiceException("Description too large", ErrorCode.INVALID_ENTITY, this.getClass().getName(), "insert");
        }
        // Check duplicates
        Item aux;
        try {
            aux = itemMapper.getByDescription(item.getDescription());
        } catch (Exception _ex) {
            logger.log(Logger.Level.FATAL, "Unexpected exception searching duplicates: ", _ex);
            throw new ServiceException(_ex.getMessage(), ErrorCode.OTHER);
        }
        if (aux != null) {
            logger.log(Logger.Level.WARN, "Item already exists");
            return aux;
        }
        // Do insert the record
        try {
            itemMapper.insert(item);
            return item;
        } catch (Exception _ex) {
            logger.log(Logger.Level.FATAL, "Unexpected exception inserting record: ", _ex);
            throw new ServiceException(_ex.getMessage(), ErrorCode.OTHER);
        }
    }

    /**
     * Updates an item.
     *
     * @param item the item to update
     * @throws ServiceException if record fails the validation (valid ID and description less than 25 characters),
     *                          or there is another item with the provided description,
     *                          or there is no item for the provided ID,
     *                          or an unexpected error occurs
     */
    public Item update(Item item) throws ServiceException {
        // Validate record
        if ((item.getId() == null) || (item.getId().equals(0L))) {
            logger.log(Logger.Level.ERROR, "Missing item ID");
            throw new ServiceException("Missing item ID", ErrorCode.INVALID_ENTITY, this.getClass().getName(), "update");
        }

        if ((item.getDescription() == null) || (item.getDescription().trim().isEmpty())) {
            logger.log(Logger.Level.ERROR, "Missing description");
            throw new ServiceException("Missing description", ErrorCode.INVALID_ENTITY, this.getClass().getName(), "update");
        }
        normalizeDescription(item);
        if (item.getDescription().length() > 25) {
            logger.log(Logger.Level.ERROR, "Description too large");
            throw new ServiceException("Description too large", ErrorCode.INVALID_ENTITY, this.getClass().getName(), "update");
        }

        // Check duplicates
        Item aux;
        try {
            aux = itemMapper.getByDescription(item.getDescription());
        } catch (Exception _ex) {
            logger.log(Logger.Level.FATAL, "Unexpected exception searching duplicates: ", _ex);
            throw new ServiceException(_ex.getMessage(), ErrorCode.OTHER);
        }
        if (aux != null) {
            logger.log(Logger.Level.ERROR, "Item already exists. Cannot update record.");
            throw new ServiceException("Item already exists. Cannot update record.", ErrorCode.INVALID_PARAMS, this.getClass().getName(), "update");
        }

        // Search the original record
        try {
            aux = itemMapper.getById(item.getId());
        } catch (Exception _ex) {
            logger.log(Logger.Level.FATAL, "Unexpected exception searching the record: ", _ex);
            throw new ServiceException(_ex.getMessage(), ErrorCode.OTHER);
        }
        if (aux == null) {
            logger.log(Logger.Level.ERROR, "No data found for ID");
            throw new ServiceException("No data found for ID.", ErrorCode.INVALID_PARAMS, this.getClass().getName(), "update");
        }

        // Do update the record
        try {
            itemMapper.update(item);
        } catch (Exception _ex) {
            logger.log(Logger.Level.FATAL, "Unexpected exception updating record: ", _ex);
            throw new ServiceException(_ex.getMessage(), ErrorCode.OTHER);
        }

        return item;
    }

    /**
     * Deletes an item
     *
     * @param id the id of the item to remove
     * @return the deleted item or null if there is no item for the specified id.
     * @throws ServiceException if id is not provided or if unexpected error occurs
     */
    public Item delete(Long id) throws ServiceException {
        // Validate record
        if ((id == null) || (id.equals(0L))) {
            logger.log(Logger.Level.ERROR, "Missing item ID");
            throw new ServiceException("Missing item ID", ErrorCode.INVALID_PARAMS, this.getClass().getName(), "delete");
        }

        Item item = itemMapper.getById(id);
        if (item == null) {
            logger.log(Logger.Level.INFO, "Unexistent item. Nothing to delete");
            return null;
        }

        // proceed to delete
        try {
            itemMapper.delete(id);
        } catch (Exception _ex) {
            logger.log(Logger.Level.FATAL, "Unexpected exception deleting record: ", _ex);
            throw new ServiceException(_ex.getMessage(), ErrorCode.OTHER);
        }

        return item;
    }

    /**
     * Normalizes item description according to business rules
     *
     * @param item the item to process
     */
    private void normalizeDescription(Item item) {
        item.setDescription(item.getDescription().trim());
        // item.setDescription(item.getDescription().toUpperCase());
    }

}
