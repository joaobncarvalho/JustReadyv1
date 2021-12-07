package pt.iade.JustReady.Controllers;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pt.iade.JustReady.models.RelationShiplist;
import pt.iade.JustReady.models.Exceptions.NotFoundException;
import pt.iade.JustReady.models.Exceptions.Response;
import pt.iade.JustReady.models.Repositories.RelationShiplistRepository;

@RestController
@RequestMapping(path = "/api/rs")
public class RelationShiplistController {

    private Logger logger = LoggerFactory.getLogger(RelationShiplistController.class);
    @Autowired
    private RelationShiplistRepository rsRepository;

    @GetMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<RelationShiplist> getRelationShipList() {
        logger.info("Sending all rs");
        return rsRepository.findAll();
    }


    @GetMapping(path = "/{id:[0-9]+}", produces = MediaType.APPLICATION_JSON_VALUE)
    public RelationShiplist getRelationShiplist(@PathVariable int id) {
        logger.info("Sending rs with id " + id);
        Optional<RelationShiplist> _rs = rsRepository.findById(id);
        if (!_rs.isPresent())
            throw new NotFoundException("" + id, "rs", "id");
        else
            return _rs.get();
    }

    /*@PostMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public RelationShiplist saveRelationShiplist(@RequestBody RelationShiplist relationShiplist) {
        RelationShiplist savedrRelationShiplist = rsRepository.save(relationShiplist);
        logger.info("Saving rs with id " + savedrRelationShiplist.getRl_id());
        return savedrRelationShiplist;

    }*/
    @PostMapping(path = "/newfriend", produces = MediaType.APPLICATION_JSON_VALUE)
    public Response saveFriend(@RequestBody RelationShiplist relationShiplistId){
        logger.info("Registering a new friend with id"+ relationShiplistId.getRl_id()+
                "Registering a new ticket"+relationShiplistId.getRl_users_ticket());
        Integer inserted = rsRepository.registerFriend(relationShiplistId);
        return new Response(inserted+"registation created",relationShiplistId);
    }


    @DeleteMapping(path = "/{id:[0-9]+}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Response deleteRelationShiplist(@PathVariable int id) {
        logger.info("Deleting rs with id " + id);
        // No verification to see if it exists
        rsRepository.deleteById(id);
        return new Response("Deleted rs with id " + id, null);
    }
    
}
