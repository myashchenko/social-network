package ua.social.network.filter;

import java.util.List;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;

/**
 * @author Mykola Yashchenko
 */
public class HttpMethodFilter extends ZuulFilter {

    private static final List<String> QUERY_METHODS = List.of("GET");
    private static final List<String> IGNORED_METHODS = List.of("HEAD", "OPTIONS");

    private final DiscoveryClient discoveryClient;
    private final String commandService;
    private final String queryService;
    private final String requestPath;

    public HttpMethodFilter(DiscoveryClient discoveryClient, String commandService,
                            String queryService, String requestPath) {
        this.discoveryClient = discoveryClient;
        this.commandService = commandService;
        this.queryService = queryService;
        this.requestPath = requestPath;
    }

    @Override
    public String filterType() {
        return "route";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        RequestContext context = RequestContext.getCurrentContext();
        String requestURI = context.getRequest().getRequestURI();
        String method = context.getRequest().getMethod();
        return !IGNORED_METHODS.contains(method) && requestURI.startsWith(requestPath);
    }

    @Override
    public Object run() {
        RequestContext context = RequestContext.getCurrentContext();
        String httpMethod = context.getRequest().getMethod();

        List<ServiceInstance> instances;
        if (QUERY_METHODS.contains(httpMethod)) {
            instances = discoveryClient.getInstances(queryService);
        } else {
            instances = discoveryClient.getInstances(commandService);
        }

        try {
            if (CollectionUtils.isNotEmpty(instances)) {
                context.setRouteHost(instances.get(0).getUri().toURL());
            } else {
                throw new IllegalStateException("Target service instance not found!");
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("Couldn't get service URL!", e);
        }

        return null;
    }
}
