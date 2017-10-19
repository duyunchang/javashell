package com.tysx.vod.controler;
/**
 * 
 */
/**
 * @author dyc
 *
 */
import org.apache.log4j.Logger;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tysx.vod.domain.vo.output.ResultProxy;

@RestController
@EnableAutoConfiguration
@RequestMapping("/shell")
public class Controller {
	private Logger logger = Logger.getLogger(Controller.class);
	
	@RequestMapping(value = "/connect")
	public ResultProxy connect(String user,String passwd ,String serverIp) {
		ResultProxy ss=new ResultProxy();
		ss.setMsg("ss");
		return ss;
	}
	
	@RequestMapping(value = "/disconnect")
	public ResultProxy Disconnect(String user,String passwd ,String serverIp) {
		
		
		return null;
	}
	
}