package ua.social.network.communityqueryservice.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ua.social.network.communityqueryservice.dto.CommunityDto;
import ua.social.network.communityqueryservice.query.CommunityMapper;
import ua.social.network.controller.AbstractController;
import ua.social.network.exception.SnException;
import ua.social.network.exception.SnExceptionDetails;

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
            throw new SnException(new SnExceptionDetails() {
                @Override
                public String code() {
                    return null;
                }

                @Override
                public HttpStatus status() {
                    return HttpStatus.NOT_FOUND;
                }
            });
        }
        return entity;
    }

    @GetMapping
    public List<CommunityDto> getCommunities(@RequestParam Map<String, Object> requestParams) {
        return getEntityList(requestParams);
    }
}
