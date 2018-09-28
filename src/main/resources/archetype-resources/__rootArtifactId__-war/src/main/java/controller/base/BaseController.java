package ${package}.controller.base;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

/**
 * 控制器基类
 */
public class BaseController {

    @InitBinder
    public void webInitBinder(WebDataBinder binder){
        binder.registerCustomEditor(String.class, new StringEditor());
    }
}
