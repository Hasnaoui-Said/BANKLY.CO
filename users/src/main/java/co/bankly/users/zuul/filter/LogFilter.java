package co.bankly.users.zuul.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Component
public class LogFilter extends ZuulFilter {
    Logger log = LoggerFactory.getLogger(this.getClass());

    @Override

    public String filterType() {
        return "pre";
    }

    @Override

    public int filterOrder() {
        return 1;
    }

    @Override

    public boolean shouldFilter() {
        return true;
    }

    @Override

    public Object run() throws ZuulException {
//        HttpServletResponse req = RequestContext.getCurrentContext().getResponse();
//        log.info("**** Requête interceptée ! L'URL est : {} ", req.getStatus());

        HttpServletRequest req = RequestContext.getCurrentContext().getRequest();

        log.info("**** Requête interceptée ! L'URL est : {} " , req.getRequestURL());

        return null;
    }
}
