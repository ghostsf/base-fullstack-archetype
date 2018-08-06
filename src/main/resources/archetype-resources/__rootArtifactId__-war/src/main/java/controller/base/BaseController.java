package ${package}.controller.base;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

/**
 * 控制器基类
 */
public class BaseController {
    
    
   private static final Logger LOGGER = LoggerFactory.getLogger(BaseController.class);

    @InitBinder
    public void webInitBinder(WebDataBinder binder){
        binder.registerCustomEditor(String.class, new StringEditor());
    }
}
