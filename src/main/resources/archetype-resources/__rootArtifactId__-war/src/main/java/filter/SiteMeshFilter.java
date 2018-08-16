package ${package}.filter;

import com.opensymphony.module.sitemesh.Config;
import com.opensymphony.module.sitemesh.DecoratorMapper;
import com.opensymphony.module.sitemesh.Factory;
import com.opensymphony.sitemesh.Content;
import com.opensymphony.sitemesh.ContentProcessor;
import com.opensymphony.sitemesh.Decorator;
import com.opensymphony.sitemesh.DecoratorSelector;
import com.opensymphony.sitemesh.compatability.DecoratorMapper2DecoratorSelector;
import com.opensymphony.sitemesh.compatability.OldDecorator2NewDecorator;
import com.opensymphony.sitemesh.webapp.ContainerTweaks;
import com.opensymphony.sitemesh.webapp.ContentBufferingResponse;
import com.opensymphony.sitemesh.webapp.SiteMeshWebAppContext;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SiteMeshFilter extends com.opensymphony.sitemesh.webapp.SiteMeshFilter {
    private FilterConfig filterConfig;
    private ContainerTweaks containerTweaks;


    @Override
    public void init(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
        this.containerTweaks = new ContainerTweaks();
        super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest rq, ServletResponse rs, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) rq;
        HttpServletResponse response = (HttpServletResponse) rs;
        ServletContext servletContext = this.filterConfig.getServletContext();
        SiteMeshWebAppContext webAppContext = new SiteMeshWebAppContext(request, response, servletContext);
        ContentProcessor contentProcessor = this.initContentProcessor(webAppContext);

        Factory factory = Factory.getInstance(new Config(this.filterConfig));
        factory.refresh();
        DecoratorMapper decoratorMapper = factory.getDecoratorMapper();
        DecoratorSelector decoratorSelector = new DecoratorMapper2DecoratorSelector(decoratorMapper);

        if (this.filterAlreadyAppliedForRequest(request)) {
            chain.doFilter(request, response);
        } else if (!contentProcessor.handles(webAppContext)) {
            chain.doFilter(request, response);
        } else {
            if (this.containerTweaks.shouldAutoCreateSession()) {
                request.getSession(true);
            }

            try {
                Content content = this.obtainContent(contentProcessor, webAppContext, request, response, chain);
                if (content == null) {
                    return;
                }


                if (Integer.valueOf(500).equals(request.getAttribute("_responseStatus"))) {
                    Decorator decorator = new OldDecorator2NewDecorator(decoratorMapper.getNamedDecorator(request, "default"));
                    decorator.render(content, webAppContext);
                } else {
                    Decorator decorator = decoratorSelector.selectDecorator(content, webAppContext);
                    decorator.render(content, webAppContext);
                }
            } catch (IllegalStateException var12) {
                if (!this.containerTweaks.shouldIgnoreIllegalStateExceptionOnErrorPage()) {
                    throw var12;
                }
            } catch (RuntimeException var13) {
                if (this.containerTweaks.shouldLogUnhandledExceptions()) {
                    servletContext.log("Unhandled exception occurred whilst decorating page", var13);
                }

                throw var13;
            } catch (ServletException var14) {
                request.setAttribute("com.opensymphony.sitemesh.APPLIED_ONCE", (Object) null);
                throw var14;
            }

        }
    }

    private boolean filterAlreadyAppliedForRequest(HttpServletRequest request) {
        if (request.getAttribute("com.opensymphony.sitemesh.APPLIED_ONCE") == Boolean.TRUE) {
            return true;
        } else {
            request.setAttribute("com.opensymphony.sitemesh.APPLIED_ONCE", Boolean.TRUE);
            return false;
        }
    }

    private Content obtainContent(ContentProcessor contentProcessor, SiteMeshWebAppContext webAppContext, HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        ContentBufferingResponse contentBufferingResponse = new ContentBufferingResponse(response, contentProcessor, webAppContext);
        chain.doFilter(request, contentBufferingResponse);
        webAppContext.setUsingStream(contentBufferingResponse.isUsingStream());
        return contentBufferingResponse.getContent();
    }

}
