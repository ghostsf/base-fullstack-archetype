package ${package}.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ${package}.controller.base.BaseController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 控制器DEMO
 *
 * @author ghostsf
 * @create 2018-09-27 14:01
 * @version
 * @see
 * @since
 */
@RestController
@RequestMapping("/index")
public class IndexController extends BaseController {
	private static final Logger LOGGER = LoggerFactory.getLogger(IndexController.class);


}