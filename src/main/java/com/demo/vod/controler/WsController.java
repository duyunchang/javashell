package com.demo.vod.controler;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.demo.vod.domain.vo.input.RequestMessage;
import com.demo.vod.domain.vo.output.ResponseMessage;

@Controller
public class WsController {

	@RequestMapping(value = "/")
	public String comm() {

		
		return "socket";
	}
	
	@RequestMapping(value = "/ws")
	public String socket() {

		
		return "ws";
	}

	@MessageMapping("/welcome")
	@SendTo("/topic/getResponse")
	public ResponseMessage say(RequestMessage message) {
		int count = 0;
		try {
			Thread.currentThread().sleep(10*1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // simulated delay
		count++;
		System.out.println(count);

		return new ResponseMessage("welcome," + message.getName() + " ! " + count);

	}
}