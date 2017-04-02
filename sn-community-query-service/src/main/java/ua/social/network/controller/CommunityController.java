package ua.social.network.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ua.social.network.dto.CommunityDto;
import ua.social.network.exception.EntityNotFoundException;
import ua.social.network.query.CommunityMapper;

import java.util.List;
import java.util.Map;

/**
 * @author Mykola Yashchenko
 */
@RestController
@RequestMapping("/communities")
public class CommunityController extends AbstractController<CommunityDto, CommunityMapper> {

    @Autowired
    public CommunityController(CommunityMapper mapper) {
        super(mapper);
    }

    @GetMapping("/{id}")
    public CommunityDto getCommunity(@PathVariable("id") String id) {
        CommunityDto entity = getEntity(id);
        if (entity == null) {
            throw new EntityNotFoundException("Community with id %s doesn't exist", id);
        }
        return entity;
    }

    @GetMapping
    public List<CommunityDto> getCommunities(@RequestParam Map<String, Object> requestParams) {
        return getEntityList(requestParams);
    }
}
