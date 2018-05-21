package ar.com.tuchorc.controller;

import ar.com.tuchorc.exception.ErrorCode;
import ar.com.tuchorc.exception.ServiceError;
import ar.com.tuchorc.exception.ServiceException;
import ar.com.tuchorc.model.Item;
import ar.com.tuchorc.service.StringListService;
import io.swagger.annotations.*;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(value = "String-List", description = "String list controller")
@RestController
public class StringListController {

	private final Logger logger = Logger.getLogger(StringListController.class.getCanonicalName());

	private final StringListService stringListService;

	@Autowired
	public StringListController(StringListService stringListService) {
		this.stringListService = stringListService;
	}

	@GetMapping(value = "/all")
	@ApiOperation(value = "Get all items from the list",
			notes = "Returns all the items from the list",
			response = Item.class)
	@ApiResponses({@ApiResponse(code = 200, message = "Ok"),
			@ApiResponse(code = 500, message = "Other generic errors")})
	public ResponseEntity getAll() {
		try {
			return ResponseEntity.ok(stringListService.getAll());
		} catch (ServiceException _ex) {
			logger.log(Logger.Level.ERROR, "Error retrieving the item list: " + _ex.toString());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
					new ServiceError(_ex.getErrorCode().getErrorCode(), _ex.getMessage()));
		}
	}

	@GetMapping(value = "/all/reverse")
	@ApiOperation(value = "Get all items from the list in reverse order",
			notes = "Returns all the items from the list",
			response = Item.class)
	@ApiResponses({@ApiResponse(code = 200, message = "Ok"),
			@ApiResponse(code = 500, message = "Other generic errors")})
	public ResponseEntity getAllReverse() {
		try {
			return ResponseEntity.ok(stringListService.getAllReverse());
		} catch (ServiceException _ex) {
			logger.log(Logger.Level.ERROR, "Error retrieving the item list: " + _ex.toString());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
					new ServiceError(_ex.getErrorCode().getErrorCode(), _ex.getMessage()));
		}
	}

	@GetMapping(value = "/id/{id}")
	@ApiOperation(value = "Get all items from the list",
			notes = "Returns all the items from the list",
			response = Item.class)
	@ApiResponses({@ApiResponse(code = 200, message = "Ok"),
			@ApiResponse(code = 400, message = "Bad request"),
			@ApiResponse(code = 500, message = "Other generic errors")})
	public ResponseEntity getById(@ApiParam(value = "id", name = "id", required = true) @PathVariable Long id) {
		if (id == null || id == 0L) {
			logger.log(Logger.Level.ERROR, "Missing Parameter");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
					new ServiceError(ErrorCode.INVALID_PARAMS.getErrorCode(), "Missing Parameter"));
		}

		try {
			Item item = stringListService.getById(id);

			if (item == null) {
				logger.log(Logger.Level.WARN, "No Data Found");
				return ResponseEntity.ok(new ServiceError(ErrorCode.NO_DATA_FOUND.getErrorCode(), "No data found"));
			}

			return ResponseEntity.ok(item);
		} catch (ServiceException _ex) {
			logger.log(Logger.Level.ERROR, "Error retrieving the item list: " + _ex.toString());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
					new ServiceError(_ex.getErrorCode().getErrorCode(), _ex.getMessage()));
		}
	}

	@PostMapping(value = "/")
	@ApiOperation(value = "Inserts a new record to the list",
			notes = "Creates a new item if it doesn't exist and returns the new item. Otherwise returns the original item.",
			response = Item.class)
	@ApiResponses({@ApiResponse(code = 200, message = "Ok"),
			@ApiResponse(code = 400, message = "Bad request"),
			@ApiResponse(code = 500, message = "Other generic errors")})
	public ResponseEntity insert(@RequestBody Item item) {
		if (item == null) {
			logger.log(Logger.Level.ERROR, "Missing Payload");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
					new ServiceError(ErrorCode.INVALID_PARAMS.getErrorCode(), "Missing payload"));
		}

		try {
			return ResponseEntity.ok(stringListService.insert(item));
		} catch (ServiceException _ex) {
			logger.log(Logger.Level.ERROR, "Error inserting the item: " + _ex.toString());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
					new ServiceError(_ex.getErrorCode().getErrorCode(), _ex.getMessage()));
		}
	}

	@PutMapping(value = "/")
	@ApiOperation(value = "Updates a record from the list",
			notes = "Updates an item if it exist and it doesn't exist  an item having the new description.",
			response = Item.class)
	@ApiResponses({@ApiResponse(code = 200, message = "Ok"),
			@ApiResponse(code = 400, message = "Bad request"),
			@ApiResponse(code = 500, message = "Other generic errors")})
	public ResponseEntity update(@RequestBody Item item) {
		if (item == null) {
			logger.log(Logger.Level.ERROR, "Missing Payload");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
					new ServiceError(ErrorCode.INVALID_PARAMS.getErrorCode(), "Missing payload"));
		}

		try {
			return ResponseEntity.ok(stringListService.update(item));
		} catch (ServiceException _ex) {
			logger.log(Logger.Level.ERROR, "Error updating the item: " + _ex.toString());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
					new ServiceError(_ex.getErrorCode().getErrorCode(), _ex.getMessage()));
		}
	}

	@DeleteMapping(value = "/{id}")
	@ApiOperation(value = "Deletes a record from the list",
			notes = "Deletes an item if it exist.",
			response = Item.class)
	@ApiResponses({@ApiResponse(code = 200, message = "Ok"),
			@ApiResponse(code = 400, message = "Bad request"),
			@ApiResponse(code = 500, message = "Other generic errors")})
	public ResponseEntity delete(@ApiParam(value = "id", name = "id", required = true) @PathVariable Long id) {
		if (id == null || id == 0L) {
			logger.log(Logger.Level.ERROR, "Missing Parameter");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
					new ServiceError(ErrorCode.INVALID_PARAMS.getErrorCode(), "Missing Parameter"));
		}

		try {
			return ResponseEntity.ok(stringListService.delete(id));
		} catch (ServiceException _ex) {
			logger.log(Logger.Level.ERROR, "Error deleting the item: " + _ex.toString());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
					new ServiceError(_ex.getErrorCode().getErrorCode(), _ex.getMessage()));
		}
	}
}
