package ${package}.support.freemarker;

import freemarker.cache.TemplateLoader;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.util.List;

/**
 * freemarker xss
 */
public class XSSFreeMarkerConfigurer extends FreeMarkerConfigurer {

    @Override
    protected TemplateLoader getAggregateTemplateLoader(List<TemplateLoader> templateLoaders) {
        return new HtmlTemplateLoader(super.getAggregateTemplateLoader(templateLoaders));
    }
}
