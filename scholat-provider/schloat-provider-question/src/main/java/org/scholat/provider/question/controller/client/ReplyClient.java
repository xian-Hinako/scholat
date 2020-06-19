package org.scholat.provider.question.controller.client;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.cookie.Cookie;
import org.scholat.common.ResultMsg;
import org.scholat.provider.question.pojo.PageQuestion;
import org.scholat.provider.question.pojo.PageQuestionAndReply;
import org.scholat.provider.question.pojo.QuestionAndReply;
import org.scholat.provider.question.pojo.Reply;
import org.scholat.provider.question.service.ReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/*
 * @author lsx
 * 
 */
@RestController
@CrossOrigin(allowCredentials = "true" ,allowedHeaders = "*")
public class ReplyClient {
	
	@Autowired
	private ReplyService replyService;
	
	@GetMapping("/replys/findReplyList/{questionId}")
	public Object queReply(@PathVariable Integer questionId){
		   List<Reply> ReplyList = replyService.findReply(questionId);
		   return new ResultMsg<Object>(1,ReplyList,"success"); 
	}
	
	@DeleteMapping("/replys/deleteReply/{replyId}")
	public Object deleteReply(@PathVariable Integer replyId){
		replyService.deleteReply(replyId);
		return new ResultMsg<Object>(1,null,"success");
	}
	
	@GetMapping("/replys/replyWithQues/{page}")
	public Object findReplyWithQue(@PathVariable Integer page,HttpServletRequest request){
		PageQuestionAndReply pageQuestionAndReply = new PageQuestionAndReply();
		PageRequest pageReques=PageRequest.of(page,8);  //第page+1页的8条记录		
//		// Cookie cookie=new Cookie("sessionId","CookieTestInfo");
//		Cookie[] cookies = (Cookie[]) request.getCookies();
//		if(cookies != null){
//		for(Cookie cookie : cookies){
//		if(cookie.getName().equals("sessionId")){
//			userId =  cookie.getValue();
//		}
		Integer userId = 2;    //测试数据:仅作测试使用
//		List<QuestionAndReply> ReplyByUser = replyService.findReplyByUser(userId);
		pageQuestionAndReply.setQuestionAndReply(replyService.findReplyByUser(userId,pageReques).getContent());
		pageQuestionAndReply.setPageCount(replyService.findReplyByUser(userId,pageReques).getTotalPages());
		pageQuestionAndReply.setSize(replyService.findReplyByUser(userId,pageReques).getTotalElements());
		return new ResultMsg<Object>(1,pageQuestionAndReply,"success"); 
	}
	
    
    @PutMapping("/replys/addReply")
    public Object addReply(@RequestBody Reply reply){
    	System.out.println(reply);
    	replyService.addReply(reply);
    	return new ResultMsg<Object>(1,null,"success");
    }
}
