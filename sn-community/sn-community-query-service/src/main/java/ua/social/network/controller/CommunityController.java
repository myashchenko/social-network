package ua.social.network.controller;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ua.social.network.dto.CommunityDto;
import ua.social.network.exception.EntityNotFoundException;
import ua.social.network.query.CommunityMapper;

/**
 * @author Mykola Yashchenko
 */
@RestController
@RequestMapping("/communities")
public class CommunityController extends AbstractController<CommunityDto, CommunityMapper> {

    public CommunityController(CommunityMapper mapper) {
        super(mapper);
    }

    @GetMapping("/{id}")
    public CommunityDto getCommunity(@PathVariable("id") String id, @RequestParam Map<String, Object> requestParams) {
        var entity = getEntity(id, requestParams);
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
