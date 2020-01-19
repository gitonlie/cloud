package priv.gitonline.consumer.controller;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import priv.gitonline.consumer.config.VerifyCode;

/**
 * 验证码
 * 
 * @author D.K 2019年10月23日
 */
@Controller
public class VerifyCodeController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	/* 获取验证码图片 */
	@RequestMapping("/getVerifyCode")
	public void getVerificationCode(HttpServletResponse response, HttpServletRequest request) {
		try {

			int width = 200;

			int height = 69;

			BufferedImage verifyImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

			//生成对应宽高的初始图片

			String randomText = VerifyCode.drawRandomText(width, height, verifyImg);

			//单独的一个类方法，出于代码复用考虑，进行了封装。

			//功能是生成验证码字符并加上噪点，干扰线，返回值为验证码字符                   

			request.getSession().setAttribute("verifyCode", randomText);

			response.setContentType("image/png");// 必须设置响应内容类型为图片，否则前台不识别

			OutputStream os = response.getOutputStream(); // 获取文件输出流

			ImageIO.write(verifyImg, "png", os);// 输出图片流

			os.flush();

			os.close();// 关闭流

		} catch (IOException e) {

			logger.error(e.getMessage(),e);
		}

	}
}
