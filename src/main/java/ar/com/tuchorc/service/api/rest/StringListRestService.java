package ar.com.tuchorc.service.api.rest;

import ar.com.tuchorc.exception.ErrorCode;
import ar.com.tuchorc.exception.ServiceError;
import ar.com.tuchorc.exception.ServiceException;
import ar.com.tuchorc.persistence.model.Item;
import ar.com.tuchorc.service.StringListService;
import io.swagger.annotations.*;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.core.Response;

@Api(value = "String-List", description = "String list controller")
@RestController
public class StringListRestService {
    private final Logger logger = Logger.getLogger(StringListRestService.class.getCanonicalName());

    private final StringListService stringListService;

    @Autowired
    public StringListRestService(StringListService stringListService) {
        this.stringListService = stringListService;
    }

    @GetMapping(value = "/all")
    @ApiOperation(value = "Get all items from the list",
            notes = "Returns all the items from the list",
            response = Item.class)
    @ApiResponses({@ApiResponse(code = 200, message = "Ok"),
            @ApiResponse(code = 500, message = "Other generic errors")})
    public Response getAll() {
        try {
            return Response.ok(stringListService.getAll()).build();
        } catch (ServiceException _ex) {
            logger.log(Logger.Level.ERROR, "Error retrieving the item list: " + _ex.toString());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ServiceError(_ex.getErrorCode().getErrorCode(), _ex.getMessage()))
                    .build();
        }
    }

    @GetMapping(value = "/all/reverse")
    @ApiOperation(value = "Get all items from the list in reverse order",
            notes = "Returns all the items from the list",
            response = Item.class)
    @ApiResponses({@ApiResponse(code = 200, message = "Ok"),
            @ApiResponse(code = 500, message = "Other generic errors")})
    public Response getAllREverse() {
        try {
            return Response.ok(stringListService.getAllReverse()).build();
        } catch (ServiceException _ex) {
            logger.log(Logger.Level.ERROR, "Error retrieving the item list: " + _ex.toString());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ServiceError(_ex.getErrorCode().getErrorCode(), _ex.getMessage()))
                    .build();
        }
    }

    @GetMapping(value = "/id/{id}")
    @ApiOperation(value = "Get all items from the list",
            notes = "Returns all the items from the list",
            response = Item.class)
    @ApiResponses({@ApiResponse(code = 200, message = "Ok"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 500, message = "Other generic errors")})
    public Response getById(@ApiParam(value = "id", name = "id", required = true) @PathVariable Long id) {
        if (id == null || id == 0L) {
            logger.log(Logger.Level.ERROR, "Missing Parameter");
            return Response.status(Response.Status.BAD_REQUEST).entity(new ServiceError(ErrorCode.INVALID_PARAMS.getErrorCode(), "Missing Parameter"))
                    .build();
        }

        try {
            Item item = stringListService.getById(id);

            if (item == null) {
                logger.log(Logger.Level.WARN, "No Data Found");
                return Response.ok(new ServiceError(ErrorCode.NO_DATA_FOUND.getErrorCode(), "No data found"))
                        .build();
            }

            return Response.ok(item).build();
        } catch (ServiceException _ex) {
            logger.log(Logger.Level.ERROR, "Error retrieving the item list: " + _ex.toString());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ServiceError(_ex.getErrorCode().getErrorCode(), _ex.getMessage()))
                    .build();
        }
    }

    @PostMapping(value = "/")
    @ApiOperation(value = "Inserts a new record to the list",
            notes = "Creates a new item if it doesn't exist and returns the new item. Otherwise returns the original item.",
            response = Item.class)
    @ApiResponses({@ApiResponse(code = 200, message = "Ok"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 500, message = "Other generic errors")})
    public Response insert(@RequestBody Item item) {
        if (item == null) {
            logger.log(Logger.Level.ERROR, "Missing Payload");
            return Response.status(Response.Status.BAD_REQUEST).entity(new ServiceError(ErrorCode.INVALID_PARAMS.getErrorCode(), "Missing payload"))
                    .build();
        }

        try {
            return Response.ok(stringListService.insert(item)).build();
        } catch (ServiceException _ex) {
            logger.log(Logger.Level.ERROR, "Error inserting the item: " + _ex.toString());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ServiceError(_ex.getErrorCode().getErrorCode(), _ex.getMessage()))
                    .build();
        }
    }

    @PutMapping(value = "/")
    @ApiOperation(value = "Updates a record from the list",
            notes = "Updates an item if it exist and it doesn't exist  an item having the new description.",
            response = Item.class)
    @ApiResponses({@ApiResponse(code = 200, message = "Ok"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 500, message = "Other generic errors")})
    public Response update(@RequestBody Item item) {
        if (item == null) {
            logger.log(Logger.Level.ERROR, "Missing Payload");
            return Response.status(Response.Status.BAD_REQUEST).entity(new ServiceError(ErrorCode.INVALID_PARAMS.getErrorCode(), "Missing payload"))
                    .build();
        }

        try {
            return Response.ok(stringListService.update(item)).build();
        } catch (ServiceException _ex) {
            logger.log(Logger.Level.ERROR, "Error updating the item: " + _ex.toString());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ServiceError(_ex.getErrorCode().getErrorCode(), _ex.getMessage()))
                    .build();
        }
    }

    @DeleteMapping(value = "/id/{id}")
    @ApiOperation(value = "Deletes a record from the list",
            notes = "Deletes an item if it exist.",
            response = Item.class)
    @ApiResponses({@ApiResponse(code = 200, message = "Ok"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 500, message = "Other generic errors")})
    public Response delete(@ApiParam(value = "id", name = "id", required = true) @PathVariable Long id) {
        if (id == null || id == 0L) {
            logger.log(Logger.Level.ERROR, "Missing Parameter");
            return Response.status(Response.Status.BAD_REQUEST).entity(new ServiceError(ErrorCode.INVALID_PARAMS.getErrorCode(), "Missing Parameter"))
                    .build();
        }

        try {
            return Response.ok(stringListService.delete(id)).build();
        } catch (ServiceException _ex) {
            logger.log(Logger.Level.ERROR, "Error deleting the item: " + _ex.toString());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ServiceError(_ex.getErrorCode().getErrorCode(), _ex.getMessage()))
                    .build();
        }
    }
}
